package com.heima.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.RegionDao;
import com.heima.domain.Region;
import com.heima.service.RegionService;
import com.heima.utils.PinYin4jUtils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月6日 上午11:45:59
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {

	private RedisTemplate<String, String> redis;
	private RegionDao regionDao;

	@Override
	public void saveRegion(File file) throws FileNotFoundException, IOException {
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = book.getSheetAt(0);
		List<Region> list = new LinkedList<>();
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			Region region = new Region();

			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();

			region.setId(row.getCell(0).getStringCellValue());
			region.setProvince(province);
			region.setCity(city);
			region.setDistrict(district);
			region.setPostcode(row.getCell(4).getStringCellValue());

			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);

			String[] shortcode;
			if (province.equals(city)) {
				shortcode = PinYin4jUtils.getHeadByString(city + district, false);
			} else {
				shortcode = PinYin4jUtils.getHeadByString(city + district, false);
			}

			String[] citycode = PinYin4jUtils.stringToPinyin(city);

			region.setCitycode(PinYin4jUtils.stringArrayToString(citycode));
			region.setShortcode(PinYin4jUtils.stringArrayToString(shortcode));
			list.add(region);
		}
		regionDao.save(list);

	}

	@Override
	public Page<Region> getRegionPage(PageRequest pageRequest, Map<String, String> map) {
		if (map.size() == 0) {
			return regionDao.findAll(pageRequest);
		} else {
			Specification<Region> specification = new Specification<Region>() {

				@Override
				public Predicate toPredicate(Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new LinkedList<>();
					if (map.get("id") != null) {
						list.add(cb.equal(root.get("id").as(String.class), map.get("id")));
					}
					if (map.get("province") != null) {
						list.add(cb.like(root.get("province").as(String.class), "%" + map.get("province") + "%"));
					}
					if (map.get("city") != null) {
						list.add(cb.like(root.get("city").as(String.class), "%" + map.get("city") + "%"));
					}
					if (map.get("district") != null) {
						list.add(cb.like(root.get("district").as(String.class), "%" + map.get("district") + "%"));
					}
					if (map.get("postcode") != null) {
						list.add(cb.equal(root.get("postcode").as(String.class), map.get("postcode")));
					}
					if (map.get("shortcode") != null) {
						list.add(cb.equal(root.get("shortcode").as(String.class), map.get("shortcode")));
					}
					if (map.get("citycode") != null) {
						list.add(cb.equal(root.get("citycode").as(String.class), map.get("citycode")));
					}
					return cb.and(list.toArray(new Predicate[list.size()]));
				}
			};
			return regionDao.findAll(specification, pageRequest);
		}
	}

	@Override
	public boolean checkId(String code) {
		Region region = regionDao.findOne(code);
		if (region != null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkPostCode(String postcode, String id) {
		Region region = regionDao.findByPostcode(postcode);
		if (region != null) {
			if (!region.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	@Autowired
	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}
}
