package com.heima.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
	public String getPage(Map<String, String> params, PageRequest pageRequest) {
		String key = "subarea-" + pageRequest.getPageNumber() + "-" + pageRequest.getPageSize();
		// 封装查询条件
		Specification<Subarea> spec = new Specification<Subarea>() {
			@Override
			public Predicate toPredicate(Root<Subarea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new LinkedList<>();
				String addresskey = params.get("addresskey");
				if (MyUtils.isNotBlank(addresskey)) {
					list.add(cb.equal(root.get("addresskey").as(String.class), addresskey));
				}
				String province = params.get("province");
				if (MyUtils.isNotBlank(province)) {
					list.add(cb.like(root.get("province").as(String.class), province));
				}
				String city = params.get("city");
				if (MyUtils.isNotBlank(city)) {
					list.add(cb.like(root.get("city").as(String.class), city));
				}
				String district = params.get("district");
				if (MyUtils.isNotBlank(district)) {
					list.add(cb.like(root.get("district").as(String.class), district));
				}

				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};

		// 封装多条件key
		String addresskey = params.get("addresskey");
		if (MyUtils.isNotBlank(addresskey)) {
			key = key + "_addresskey:" + addresskey;
		}
		String province = params.get("province");
		if (MyUtils.isNotBlank(province)) {
			key = key + "_province:" + province;
		}
		String city = params.get("city");
		if (MyUtils.isNotBlank(city)) {
			key = key + "_city:" + city;
		}
		String district = params.get("district");
		if (MyUtils.isNotBlank(district)) {
			key = key + "_district:" + district;
		}
		String result = template.opsForValue().get(key);
		if (MyUtils.isNotBlank(result)) {
			return result;
		}
		Page<Subarea> page = subareaDao.findAll(spec, pageRequest);
		String pageJson = MyUtils.getPageJson(page);
		template.opsForValue().set(key, pageJson);
		return pageJson;
	}
}
