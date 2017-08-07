package com.heima.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月5日 下午10:14:17
 */
public class FastJsonUtil {

	public static String toJsonString(Object obj, String... properties) {
		SerializeFilter filter = new SimplePropertyPreFilter(properties);

		return JSON.toJSONString(obj, filter);
	}

	public static String toJsonString(Object obj) {

		return JSON.toJSONString(obj);
	}

}
