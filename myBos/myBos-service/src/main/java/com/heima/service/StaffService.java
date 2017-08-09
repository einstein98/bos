package com.heima.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.heima.domain.Staff;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:19:26
 */
public interface StaffService {

	void updateStaff(Staff model);

	boolean checkTel(Staff staff);

	Page getPage(PageRequest pageRequest, Map<String, String> params);

	void batchDelete(String ids);

	void batchRevert(String ids);

	List<Staff> getStaffList();

}
