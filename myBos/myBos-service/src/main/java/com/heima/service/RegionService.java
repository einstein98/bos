package com.heima.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.heima.domain.Region;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月6日 上午11:44:36
 */
public interface RegionService {

	void saveRegion(File file) throws FileNotFoundException, IOException;

	boolean checkId(String code);

	boolean checkPostCode(String postcode, String id);

	Page<Region> getRegionPage(PageRequest pageRequest, Map<String, String> parameterMap);

}
