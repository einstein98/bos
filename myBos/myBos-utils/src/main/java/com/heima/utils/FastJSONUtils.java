package com.heima.utils;

import java.util.Collections;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月8日 下午10:32:35
 */
public class FastJSONUtils {

	public static String toJSONWithProperties(Object object, String... properties) {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(properties);
		Collections.addAll(filter.getIncludes(), properties);
		return JSON.toJSONString(object, filter);
	}

	public static String toJSONWithOutProperties(Object object, String... properties) {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		Collections.addAll(filter.getExcludes(), properties);
		return JSON.toJSONString(object, filter);
	}

	public static String toJSON(Object object) {
		return JSON.toJSONString(object);
	}

}
