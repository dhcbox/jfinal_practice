package com.dhc.util;

import java.util.HashMap;

public class SysStatus {

	public static String CODECOMMONERROR = "400400"; // 默认错误编码
	public static String CODESUCCESS = "200000"; // 成功

	public static HashMap<String, String> map = new HashMap<String, String>();

	public static HashMap<String, String> initStatusMap() {

		map.put(SysStatus.CODECOMMONERROR, "系统错误");
		map.put(SysStatus.CODESUCCESS, "成功");

		return map;
	}

}
