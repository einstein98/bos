package com.heima.utils;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午9:25:27
 */
public class MyUtils {
	public static boolean isNotBlank(String str) {
		str = str.trim();
		if (str != null && str.length() >= 0) {
			return true;
		}
		return false;
	}
}
