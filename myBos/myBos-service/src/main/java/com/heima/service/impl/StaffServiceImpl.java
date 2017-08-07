package com.heima.service.impl;

import java.util.ArrayList;
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

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月4日 下午6:33:24
 */
@Service
@Transactional
public class StaffServiceImpl implements StaffService {

	private StaffDao staffDao;

	@Override
	public Page<Staff> findPage(PageRequest request, Map<String, String> map) {
		if (map.size() != 0) {
			Specification<Staff> spec = new Specification<Staff>() {

				@Override
				public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<>();
					String name = map.get("name");
					if (name != null && name.length() != 0) {
						list.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
					}

					String tel = map.get("telephone");
					if (tel != null && tel.length() != 0) {
						list.add(cb.equal(root.get("telephone").as(String.class), tel));
					}

					String station = map.get("station");
					if (station != null && station.length() != 0) {
						list.add(cb.like(root.get("station").as(String.class), "%" + station + "%"));
					}
					String standard = map.get("standard");
					if (standard != null && standard.length() != 0) {
						list.add(cb.equal(root.get("standard").as(String.class), standard));
					}
					return cb.and(list.toArray(new Predicate[list.size()]));
				}
			};
			return staffDao.findAll(spec, request);
		} else {
			return staffDao.findAll(request);
		}
	}

	@Override
	public void updateStaff(Staff staff) {
		staffDao.save(staff);
	}

	public StaffDao getStaffDao() {
		return staffDao;
	}

	@Autowired
	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@Override
	public void batchDelete(String ids) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			staffDao.logicDelete(id);
		}

	}

	@Override
	public void batchRevert(String ids) {

		String[] idArr = ids.split(",");
		for (String id : idArr) {
			staffDao.logicRevert(id);
		}
	}

	@Override
	public boolean checkPhone(Staff staff) {
		String id = staff.getId();
		String input_tel = staff.getTelephone();
		if (id != null && id.length() > 0) {
			Staff existStaff = staffDao.findOne(id);
			if (existStaff != null) {
				if (existStaff.getTelephone().equals(input_tel)) {
					return true;
				}
			} else {
				System.out.println("快递员id出错");
			}
		}
		Staff staffByTel = staffDao.getStaffByTel(input_tel);
		if (staffByTel != null) {
			return false;
		} else {
			return true;
		}
	}

}
