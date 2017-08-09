package com.heima.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.heima.domain.Region;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午1:22:29
 */
public interface RegionService {

	String getPage(PageRequest pageRequest, Map<String, String> params);

	boolean checkId(String newId, String id);

	boolean checkPostcode(String postcode, String id);

	void updateRegion(Region model);

	void upload(File upload) throws FileNotFoundException, IOException;

	String getRegionList(String param);

}
