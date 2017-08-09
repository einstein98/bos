package com.heima.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.heima.domain.Subarea;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:25:35
 */
public interface SubareaService {

	boolean checkId(String oldId, String id);

	void updateSubarea(Subarea model);

	String getPage(Subarea subarea, PageRequest pageRequest);

	List<Subarea> getSubareaList();

}
