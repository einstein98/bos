package com.heima.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午9:25:27
 */
public class MyUtils {
	public static boolean isNotBlank(String str) {
		if (str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static String getPageJson(Page pageData) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		String jsonString = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
		return jsonString;
	}
}
