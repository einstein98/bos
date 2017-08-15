package com.heima.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.DecidedZoneDao;
import com.heima.dao.SubareaDao;
import com.heima.domain.DecidedZone;
import com.heima.domain.Staff;
import com.heima.service.DecidedZoneService;
import com.heima.utils.MyUtils;

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
	public Page getPage(DecidedZone model, PageRequest pageRequest, String hasDecidedzone) {
		Specification<DecidedZone> spec = new Specification<DecidedZone>() {

			@Override
			public Predicate toPredicate(Root<DecidedZone> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new LinkedList<>();
				if (hasDecidedzone != null) {
					if ("1".equals(hasDecidedzone)) {
						list.add(cb.isNotEmpty(root.get("subareas").as(Set.class)));
					} else if ("2".equals(hasDecidedzone)) {
						list.add(cb.isEmpty(root.get("subareas").as(Set.class)));
					}
				}
				if (model != null) {
					Join<DecidedZone, Staff> join = root
							.join(root.getModel().getSingularAttribute("staff", Staff.class), JoinType.INNER);
					if (model.getStaff() != null && MyUtils.isNotBlank(model.getStaff().getStation())) {
						list.add(cb.like(join.get("station").as(String.class),
								"%" + model.getStaff().getStation() + "%"));
					}
					if (MyUtils.isNotBlank(model.getId())) {
						list.add(cb.equal(root.get("id").as(String.class), model.getId()));
					}
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};

		Page<DecidedZone> page = decidedZoneDao.findAll(spec, pageRequest);
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
