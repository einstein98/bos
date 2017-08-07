package com.heima.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.heima.domain.Standard;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月3日 下午8:45:54
 */
public interface StandardService {

	void save(Standard standard);

	Page<Standard> findPage(PageRequest request);

	void batchDelete(String ids);

	void batchRevert(String ids);

	List<Standard> getStandardList();

}
