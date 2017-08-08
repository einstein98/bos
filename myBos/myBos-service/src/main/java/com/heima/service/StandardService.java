package com.heima.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.heima.domain.Standard;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 上午9:40:43
 */
public interface StandardService {

	void updateStandard(Standard model);

	Page getPage(PageRequest pageRequest);

	void batchDelete(String ids);

	void batchRevert(String ids);

}
