package com.dhc.controller;

import java.util.HashMap;

import com.dhc.constant.Const;
import com.dhc.util.SysStatus;
import com.jfinal.core.Controller;

/**
 * 简单常用控制类
 * @author DHC
 * @version v1.0
 */
public class ControllerCommon extends Controller {

	public static ControllerCommon ctrCommon = new ControllerCommon();

	public static volatile boolean isError = false;
	public static HashMap<String, Object> resultMap = new HashMap<String, Object>();
	
	public static boolean isError() {
		return isError;
	}
	
	/**
	 * 静态初始化Map方法
	 * @return
	 */
	public static HashMap<String, Object> initHashMap() {
		resultMap.put(Const.STATUS, SysStatus.CODESUCCESS);
		resultMap.put(Const.MESSAGE,
				SysStatus.initStatusMap().get(SysStatus.CODESUCCESS));
		return resultMap;
	}
	
	/**
	 * 静态错误消息方法
	 * @param msg
	 * @return
	 */
	public static HashMap<String, Object> errorMsg(Object msg) {
		isError = true;
		resultMap.clear();
		
		resultMap.put(Const.STATUS, SysStatus.CODECOMMONERROR);
		resultMap.put(Const.MESSAGE, msg);
		return resultMap;

	}
	
	/**
	 * 静态错误代码方法
	 * @param code
	 * @return
	 */
	public static HashMap<String, Object> errorCode(String code) {
		isError = true;
		resultMap.clear();
		
		resultMap.put(Const.STATUS, code);
		resultMap.put(Const.MESSAGE, SysStatus.initStatusMap().get(code));
		return resultMap;

	}
	
	/**
	 * 返回Map的JSON方法
	 * @param object
	 * @return
	 */
	public HashMap<String, Object> returnJsonToClient(Object object) {
		if (!isError()) {
			resultMap.clear();
			resultMap = initHashMap();
			resultMap.put(Const.RESULT, object);
		}else {
			isError = false;  //初始化
		}
		return resultMap;
	}

}
