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
import com.heima.domain.Region;
import com.heima.domain.Subarea;
import com.heima.service.SubareaService;
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
		String key = "subarea-" + pageRequest.getPageNumber() + "-" + pageRequest.getPageSize();
		// 封装查询条件
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
					String province = "%" + region.getProvince() + "%";
					Join<Subarea, Region> join = root.join(root.getModel().getSingularAttribute("region", Region.class),
							JoinType.INNER);
					if (MyUtils.isNotBlank(province)) {
						list.add(cb.like(join.get("province").as(String.class), province));
					}
					String city = "%" + region.getCity() + "%";
					if (MyUtils.isNotBlank(city)) {
						list.add(cb.like(join.get("city").as(String.class), city));
					}
					String district = "%" + region.getDistrict() + "%";
					if (MyUtils.isNotBlank(district)) {
						list.add(cb.like(join.get("district").as(String.class), district));
					}

					return cb.and(list.toArray(new Predicate[list.size()]));
				}
				return null;
			}
		};

		// 封装多条件key
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
		String result = template.opsForValue().get(key);
		if (MyUtils.isNotBlank(result)) {
			return result;
		}
		Page<Subarea> page = subareaDao.findAll(spec, pageRequest);
		String pageJson = MyUtils.getPageJson(page);
		template.opsForValue().set(key, pageJson, 30, TimeUnit.MINUTES);
		return pageJson;
	}

	@Override
	public List<Subarea> getSubareaList() {
		return subareaDao.getSubareaList();
	}
}
