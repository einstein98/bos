package com.heima.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.StandardDao;
import com.heima.domain.Standard;
import com.heima.service.StandardService;
import com.heima.utils.FastJSONUtils;
import com.heima.utils.MyUtils;
import com.heima.utils.RedisUtils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 上午9:41:15
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	private StandardDao standardDao;
	private RedisTemplate<String, String> template;

	public RedisTemplate<String, String> getTemplate() {
		return template;
	}

	@Autowired
	public void setTemplate(RedisTemplate<String, String> template) {
		this.template = template;
	}

	public StandardDao getStandardDao() {
		return standardDao;
	}

	@Autowired
	public void setStandardDao(StandardDao standardDao) {
		this.standardDao = standardDao;
	}

	@Override
	public void updateStandard(Standard model) {
		standardDao.save(model);
	}

	@Override
	public Page getPage(PageRequest pageRequest) {
		return standardDao.findAll(pageRequest);
	}

	@Override
	public void batchDelete(String idStr) {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			standardDao.logicDelete(Integer.parseInt(id));
		}
		RedisUtils.clearRedisCache(template);
	}

	@Override
	public void batchRevert(String idStr) {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			standardDao.logicRevert(Integer.parseInt(id));
		}
		RedisUtils.clearRedisCache(template);

	}

	@Override
	public String standardList() {
		String standard_list = template.opsForValue().get("standard_list");
		if (MyUtils.isNotBlank(standard_list)) {
			return standard_list;
		}
		List<Standard> list = standardDao.getAvailableStandardList();
		standard_list = FastJSONUtils.toJSONWithProperties(list, "name");
		template.opsForValue().set("standard_list", standard_list);
		return standard_list;
	}

}
