package com.heima.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.DecidedZoneDao;
import com.heima.dao.SubareaDao;
import com.heima.domain.DecidedZone;
import com.heima.service.DecidedZoneService;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月9日 下午8:48:07
 */
@Service
@Transactional
public class DecidedZoneServiceImpl implements DecidedZoneService {

	private DecidedZoneDao decidedZoneDao;
	private SubareaDao subareaDao;

	public DecidedZoneDao getDecidedZoneDao() {
		return decidedZoneDao;
	}

	@Autowired
	public void setDecidedZoneDao(DecidedZoneDao decidedZoneDao) {
		this.decidedZoneDao = decidedZoneDao;
	}

	@Override
	public Page getPage(DecidedZone model, PageRequest pageRequest) {
		if (model != null) {
			// 条件查询封装
		}

		Page<DecidedZone> page = decidedZoneDao.findAll(pageRequest);
		for (DecidedZone decidedZone : page) {
			Hibernate.initialize(decidedZone.getStaff());

		}
		return page;
	}

	@Override
	public boolean checkId(String oldId, String newId) {
		if (oldId != null) {
			if (oldId.equals(newId)) {
				return true;
			}
		}

		DecidedZone decidedZone = decidedZoneDao.findOne(newId);
		if (decidedZone == null) {
			return true;
		}
		return false;
	}

	public SubareaDao getSubareaDao() {
		return subareaDao;
	}

	@Autowired
	public void setSubareaDao(SubareaDao subareaDao) {
		this.subareaDao = subareaDao;
	}

	@Override
	public void updateDecidedZone(String[] subareaIds, DecidedZone model) {
		decidedZoneDao.save(model);
		for (String subareaId : subareaIds) {
			subareaDao.addRegion(subareaId, model);
		}
	}
}
