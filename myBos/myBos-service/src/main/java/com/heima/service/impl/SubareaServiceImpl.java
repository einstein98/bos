package com.heima.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.heima.dao.SubareaDao;
import com.heima.domain.DecidedZone;
import com.heima.domain.Region;
import com.heima.domain.Subarea;
import com.heima.service.SubareaService;
import com.heima.utils.FastJSONUtils;
import com.heima.utils.MyUtils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:25:57
 */

@Service
@Transactional
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SubareaServiceImpl implements SubareaService {

	private SubareaDao subareaDao;
	private RedisTemplate<String, String> template;

	public RedisTemplate<String, String> getTemplate() {
		return template;
	}

	@Autowired
	public void setTemplate(RedisTemplate<String, String> template) {
		this.template = template;
	}

	public SubareaDao getSubareaDao() {
		return subareaDao;
	}

	@Autowired
	public void setSubareaDao(SubareaDao subareaDao) {
		this.subareaDao = subareaDao;
	}

	@Override
	public boolean checkId(String oldId, String id) {
		if (MyUtils.isNotBlank(oldId)) {
			if (oldId.equals(id)) {
				return true;
			}
		}
		Subarea subarea = subareaDao.findOne(id);
		if (subarea == null) {
			return true;
		}
		return false;
	}

	@Override
	public void updateSubarea(Subarea model) {
		template.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});

		subareaDao.save(model);
	}

	@Override
	public String getPage(Subarea subarea, PageRequest pageRequest) {
		// 封装查询条件
		Specification<Subarea> spec = setSpecification(subarea);

		// 封装多条件key
		String key = getKey(subarea, pageRequest);

		String result = template.opsForValue().get(key);
		if (MyUtils.isNotBlank(result)) {
			return result;
		}
		Page<Subarea> page = subareaDao.findAll(spec, pageRequest);
		String pageJson = MyUtils.getPageJson(page);
		template.opsForValue().set(key, pageJson, 30, TimeUnit.MINUTES);
		return pageJson;
	}

	private String getKey(Subarea subarea, PageRequest pageRequest) {
		String key = "subarea-" + pageRequest.getPageNumber() + "-" + pageRequest.getPageSize();
		String addresskey = subarea.getAddresskey();
		if (MyUtils.isNotBlank(addresskey)) {
			key = key + "_addresskey:" + addresskey;
		}
		Region region = subarea.getRegion();

		if (region != null) {
			String province = region.getProvince();
			if (MyUtils.isNotBlank(province)) {
				key = key + "_province:" + province;
			}
			String city = region.getCity();
			if (MyUtils.isNotBlank(city)) {
				key = key + "_city:" + city;
			}
			String district = region.getDistrict();
			if (MyUtils.isNotBlank(district)) {
				key = key + "_district:" + district;
			}
		}
		DecidedZone decidedZone = subarea.getDecidedZone();
		if (decidedZone != null) {
			String decidedZoneId = decidedZone.getId();
			if (MyUtils.isNotBlank(decidedZoneId)) {
				key = key + "_decidedZone:" + decidedZoneId;
			}
		}
		return key;
	}

	private Specification<Subarea> setSpecification(Subarea subarea) {
		Specification<Subarea> spec = new Specification<Subarea>() {
			@Override
			public Predicate toPredicate(Root<Subarea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new LinkedList<>();
				String addresskey = subarea.getAddresskey();
				if (MyUtils.isNotBlank(addresskey)) {
					list.add(cb.equal(root.get("addresskey").as(String.class), addresskey));
				}
				Region region = subarea.getRegion();
				if (region != null) {
					Join<Subarea, Region> join = root.join(root.getModel().getSingularAttribute("region", Region.class),
							JoinType.INNER);
					String province = region.getProvince();
					if (MyUtils.isNotBlank(province)) {
						list.add(cb.like(join.get("province").as(String.class), "%" + province + "%"));
					}
					String city = region.getCity();
					if (MyUtils.isNotBlank(city)) {
						list.add(cb.like(join.get("city").as(String.class), "%" + city + "%"));
					}
					String district = region.getDistrict();
					if (MyUtils.isNotBlank(district)) {
						list.add(cb.like(join.get("district").as(String.class), "%" + district + "%"));
					}

				}
				DecidedZone decidedZone = subarea.getDecidedZone();
				if (decidedZone != null) {
					String decidedZoneId = decidedZone.getId();
					if (MyUtils.isNotBlank(decidedZoneId)) {
						Join<Subarea, DecidedZone> join = root.join(
								root.getModel().getSingularAttribute("decidedZone", DecidedZone.class), JoinType.INNER);
						list.add(cb.equal(join.get("id").as(String.class), decidedZoneId));
					}
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};
		return spec;
	}

	@Override
	public List<Subarea> getSubareaList() {
		return subareaDao.getSubareaList();
	}

	@Override
	public HSSFWorkbook export(Subarea condition) {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("分区表");
		int rowNum = 0;
		HSSFRow header = sheet.createRow(rowNum);
		header.createCell(0).setCellValue("分区编号");
		header.createCell(1).setCellValue("定区编码");
		header.createCell(2).setCellValue("区域编号");
		header.createCell(3).setCellValue("地址关键字");
		header.createCell(4).setCellValue("起始编号");
		header.createCell(5).setCellValue("结束编号");
		header.createCell(6).setCellValue("单双号");
		header.createCell(7).setCellValue("区域位置");
		Specification<Subarea> specification = setSpecification(condition);
		List<Subarea> subareas = subareaDao.findAll(specification);
		for (Subarea subarea : subareas) {
			rowNum++;
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(subarea.getId());
			DecidedZone decidedZone = subarea.getDecidedZone();
			if (decidedZone != null) {
				row.createCell(1).setCellValue(decidedZone.getId());
			} else {
				row.createCell(1).setCellValue("");
			}
			Region region = subarea.getRegion();
			if (region != null) {
				row.createCell(1).setCellValue(region.getId());
			} else {
				row.createCell(1).setCellValue("");
			}
			row.createCell(3).setCellValue(subarea.getAddresskey());
			row.createCell(4).setCellValue(subarea.getStartnum());
			row.createCell(5).setCellValue(subarea.getEndnum());
			Character single = subarea.getSingle();
			String oddsEven = "";
			switch (single) {
			case '0':
				oddsEven = "单双号";
				break;
			case '1':
				oddsEven = "单号";
				break;
			case '2':
				oddsEven = "双号";
				break;
			default:
				break;
			}
			row.createCell(6).setCellValue(single);
			row.createCell(7).setCellValue(subarea.getPosition());

		}

		return book;
	}

	@Override
	public String getSubareaByDecidedzoneId(Subarea subarea) {
		String key = "subarea_decidedzoneId_" + subarea.getDecidedZone().getId();
		String subareaList = template.opsForValue().get(key);
		if (MyUtils.isNotBlank(subareaList)) {
			return subareaList;
		}
		List<Subarea> list = subareaDao.findByDecidedZone(subarea.getDecidedZone());
		String json = FastJSONUtils.toJSON(list);
		template.opsForValue().set(key, json);
		return json;
	}
}
