package com.heima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.StandardDao;
import com.heima.domain.Standard;
import com.heima.service.StandardService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 上午9:41:15
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {
	private StandardDao standardDao;

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
	}

	@Override
	public void batchRevert(String idStr) {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			standardDao.logicRevert(Integer.parseInt(id));
		}
	}

}
