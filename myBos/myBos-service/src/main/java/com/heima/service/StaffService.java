package com.heima.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.heima.domain.Staff;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月4日 下午6:28:58
 */
public interface StaffService {

	Page<Staff> findPage(PageRequest request, Map<String, String> map);

	void updateStaff(Staff staff);

	void batchDelete(String ids);

	void batchRevert(String ids);

	boolean checkPhone(Staff staff);

}
