package com.heima.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.RegionDao;
import com.heima.domain.Region;
import com.heima.service.RegionService;
import com.heima.utils.FastJSONUtils;
import com.heima.utils.MyUtils;
import com.heima.utils.PinYin4jUtils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:22:52
 */
@Service
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RegionServiceImpl implements RegionService {

	private RegionDao regionDao;
	private RedisTemplate<String, String> redis;
	private final String REGION_LIST_KEY = "region_list";

	public RedisTemplate<String, String> getRedis() {
		return redis;
	}

	@Autowired
	public void setRedis(RedisTemplate<String, String> redis) {
		this.redis = redis;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	@Autowired
	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	@Override
	public String getPage(PageRequest pageRequest, Map<String, String> params) {
		String key = "region-" + pageRequest.getPageNumber() + "-" + pageRequest.getPageSize();
		Specification<Region> spec = null;
		// 如果有查询条件
		if (params.size() > 0) {
			String shortcode = params.get("shortcode");
			if (MyUtils.isNotBlank(shortcode)) {
				params.put("shortcode", shortcode.toUpperCase());
			}
			// 拼接key的参数
			for (String name : params.keySet()) {
				key = key + " for " + name + ":" + params.get(name);
			}
			// 拼接specification(无论redis是否有值都拼接了参数，有待优化)
			spec = new Specification<Region>() {
				@Override
				public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					for (Entry<String, String> entry : params.entrySet()) {
						return cb.equal(root.get(entry.getKey()).as(String.class), entry.getValue());
					}
					return null;
				}
			};
		}

		String result = redis.opsForValue().get(key);
		if (MyUtils.isNotBlank(result)) {
			return result;
		}
		Page<Region> pageData = regionDao.findAll(spec, pageRequest);
		String pageJson = MyUtils.getPageJson(pageData);
		redis.opsForValue().set(key, pageJson, 30, TimeUnit.MINUTES);
		return pageJson;
	}

	@Override
	public boolean checkId(String newId, String id) {
		if (MyUtils.isNotBlank(id)) {
			if (id.equals(newId)) {
				return true;
			}
		}
		Region region = regionDao.findOne(newId);
		if (region == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkPostcode(String postcode, String id) {
		if (MyUtils.isNotBlank(id)) {
			Region region = regionDao.findOne(id);
			if (region != null) {
				if (region.getPostcode().equals(postcode)) {
					return true;
				}
			}
		}
		Region region = regionDao.findByPostcode(postcode);
		if (region == null) {
			return true;
		}
		return false;
	}

	@Override
	public void updateRegion(Region model) {
		redis.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
		regionDao.save(model);
	}

	@Override
	public void upload(File upload) throws FileNotFoundException, IOException {
		HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(upload));
		HSSFSheet sheet = workBook.getSheetAt(0);
		List<Region> list = new ArrayList<>();
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			Region region = new Region();
			region.setId(row.getCell(0).getStringCellValue());
			String province = row.getCell(1).getStringCellValue();
			region.setProvince(province);
			String city = row.getCell(2).getStringCellValue();
			region.setCity(city);
			String district = row.getCell(3).getStringCellValue();
			region.setDistrict(district);
			region.setPostcode(row.getCell(4).getStringCellValue());

			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);

			String citycode = PinYin4jUtils.stringArrayToString((PinYin4jUtils.stringToPinyin(city)));
			region.setCitycode(citycode);

			if (province.equals(city)) {
				String shortcode = PinYin4jUtils
						.stringArrayToString(PinYin4jUtils.getHeadByString(province + district));
				region.setShortcode(shortcode);
			} else {
				String shortcode = PinYin4jUtils
						.stringArrayToString(PinYin4jUtils.getHeadByString(province + city + district));
				region.setShortcode(shortcode);
			}
			list.add(region);
		}

		regionDao.save(list);

	}

	@Override
	public String getRegionList(String param) {
		String jsonStr = null;
		if (!MyUtils.isNotBlank(param)) {
			String regionListJSON = redis.opsForValue().get(REGION_LIST_KEY);
			if (MyUtils.isNotBlank(regionListJSON)) {
				return regionListJSON;
			}
			List<Region> list = regionDao.findAll();
			jsonStr = FastJSONUtils.toJSONWithProperties(list, "name", "id");
			redis.opsForValue().set(REGION_LIST_KEY, jsonStr);
		} else {
			List<Region> list = regionDao.findByParam("%" + param + "%");
			jsonStr = FastJSONUtils.toJSONWithProperties(list, "name", "id");
		}
		return jsonStr;
	}
}
