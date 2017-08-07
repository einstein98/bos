package com.heima.service.impl;

import java.util.List;

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
 * @version 创建时间：2017年8月3日 下午8:47:30
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
	public void save(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public Page<Standard> findPage(PageRequest request) {
		Page<Standard> page = standardDao.findAll(request);
		return page;
	}

	@Override
	public void batchDelete(String ids) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			standardDao.logicDelete(Integer.parseInt(id));
		}

	}

	@Override
	public void batchRevert(String ids) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			standardDao.batchRevert(Integer.parseInt(id));
		}
	}

	@Override
	public List<Standard> getStandardList() {
		return standardDao.getStandardList();
	}

}
