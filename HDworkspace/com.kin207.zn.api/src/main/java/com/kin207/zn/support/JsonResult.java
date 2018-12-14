package com.kin207.zn.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kin207.zn.AppConst;

import common.utils.JsonUtil;

@SuppressWarnings("rawtypes")
public class JsonResult<M extends JsonResult> {

	private static Logger log = Logger.getLogger(JsonResult.class);
	
	Map<String, Object> resultMap;
	
	protected JsonResult()
	{
		this.resultMap = new HashMap<String, Object>();
	}
	
	public static JsonResult create()
	{
		JsonResult result = new JsonResult();
		return result;
	}
	
	public M put(String key, Object value)
	{
		resultMap.put(key, value);
		return getMe();
	}
	
	public String success()
	{
		return success(null, null);
	}
	
	public String success(String msgCode, String msg)
	{
		resultMap.put(AppConst.MOBILE_KEY_CODE, AppConst.MOBILE_CODE_SUCCESS);
		if (msgCode != null) 
		{
			resultMap.put(AppConst.MOBILE_KEY_MSG_CODE, msgCode);
		}
		if (msg != null) 
		{
			resultMap.put(AppConst.MOBILE_KEY_MSG, msg);
		}
		return toJson();
	}
	
	public String fail() 
	{
		return fail(null, null);
	}
	
	public String fail(String msgCode, String msg) 
	{
		resultMap.put(AppConst.MOBILE_KEY_CODE, AppConst.MOBILE_CODE_FAIL);
		if (msgCode != null) 
		{
			resultMap.put(AppConst.MOBILE_KEY_MSG_CODE, msgCode);
		}
		if (msg != null) 
		{
			resultMap.put(AppConst.MOBILE_KEY_MSG, msg);
		}
		return toJson();
	}
	
	public String exits() 
	{
		return exits(null, null);
	}
	public String exits(String msgCode, String msg) 
	{
		resultMap.put(AppConst.MOBILE_KEY_CODE, AppConst.MOBILE_CODE_EXITS);
		if (msgCode != null) 
		{
			resultMap.put(AppConst.MOBILE_KEY_MSG_CODE, msgCode);
		}
		if (msg != null) 
		{
			resultMap.put(AppConst.MOBILE_KEY_MSG, msg);
		}
		return toJson();
	}
	public String toJson()
	{
		String json = JsonUtil.toJson(resultMap);
		log.info("返回：" + json);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	private M getMe() 
	{
		return (M)this;
	}
}
