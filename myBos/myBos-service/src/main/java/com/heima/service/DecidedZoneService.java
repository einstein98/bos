package com.heima.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.heima.domain.DecidedZone;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月9日 下午8:47:45
 */
public interface DecidedZoneService {

	Page getPage(DecidedZone model, PageRequest pageRequest);

	boolean checkId(String oldId, String newId);

	void updateDecidedZone(String[] subareaIds, DecidedZone model);

}
