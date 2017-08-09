package com.heima.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.dao.StaffDao;
import com.heima.domain.Staff;
import com.heima.service.StaffService;
import com.heima.utils.MyUtils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:20:03
 */
@Service
@Transactional
public class StaffServiceImpl implements StaffService {

	private StaffDao staffDao;

	public StaffDao getStaffDao() {
		return staffDao;
	}

	@Autowired
	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@Override
	public void updateStaff(Staff model) {
		staffDao.save(model);
	}

	@Override
	public boolean checkTel(Staff staffModel) {
		String inputTel = staffModel.getTelephone();
		String usedId = staffModel.getId();
		if (MyUtils.isNotBlank(usedId)) {
			Staff staffById = staffDao.findOne(usedId);
			if (staffById.getTelephone().equals(inputTel))
				return true;
		}
		Staff staffByphone = staffDao.findByTelephone(inputTel);
		if (staffByphone == null) {
			return true;
		}
		return false;
	}

	@Override
	public Page getPage(PageRequest pageRequest, Map<String, String> params) {
		if (params != null && params.size() > 0) {
			Specification<Staff> specification = new Specification<Staff>() {
				@Override
				public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new LinkedList<>();
					if (MyUtils.isNotBlank(params.get("name"))) {
						list.add(cb.like(root.get("name").as(String.class), "%" + params.get("name") + "%"));
					}
					if (MyUtils.isNotBlank(params.get("telephone"))) {
						list.add(cb.equal(root.get("telephone").as(String.class), params.get("telephone")));
					}
					if (MyUtils.isNotBlank(params.get("station"))) {
						list.add(cb.like(root.get("station").as(String.class), "%" + params.get("station") + "%"));
					}
					if (MyUtils.isNotBlank(params.get("haspda"))) {
						list.add(cb.equal(root.get("haspda").as(String.class), params.get("haspda")));
					}
					if (MyUtils.isNotBlank(params.get("standard"))) {
						list.add(cb.equal(root.get("standard").as(String.class), params.get("standard")));
					}
					return cb.and(list.toArray(new Predicate[list.size()]));
				}
			};
			return staffDao.findAll(specification, pageRequest);
		}
		return staffDao.findAll(pageRequest);
	}

	@Override
	public void batchDelete(String idStr) {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			staffDao.logicDelete(id);
		}
	}

	@Override
	public void batchRevert(String idStr) {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			staffDao.logicRevert(id);
		}
	}

	@Override
	public List<Staff> getStaffList() {
		return staffDao.getStaffList();
	}
}
