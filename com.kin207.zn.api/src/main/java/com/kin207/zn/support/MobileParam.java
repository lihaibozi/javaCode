package com.kin207.zn.support;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kin207.zn.AppConst;

import properties.ClientMsg;
import common.plugin.mybatis.Model;


public class MobileParam {

	private Map<String, Object> paramMap;
	private MobileResult jsonResult;
	
	public MobileParam(){}
	
	public MobileParam(Map<String, Object> paramMap) 
	{
		this.paramMap = paramMap;
	}

	
	/*****************************
	 ***** 常用获取特定参数操作 ***** 
	 *****************************/
	
	public Integer getMoblieUserId() 
	{
		return getParaToInt(AppConst.MOBILE_KEY_USER_ID);
	}
	
	public String getLanguage() 
	{   
		String language = AppConst.MOBILE_KEY_LANGUAGE_CH;
		if(getPara(AppConst.MOBILE_KEY_LANGUAGE.toUpperCase())!=null){
		language = getPara(AppConst.MOBILE_KEY_LANGUAGE.toUpperCase()); 
		}
		if (LanguageKit.isZh(language) || LanguageKit.isEn(language)||LanguageKit.isKorean(language)||LanguageKit.isJp(language)) 
		{
			return language;
		}
		return LanguageKit.LANGUAGE_DEFAULT;
	}
	
	public <M extends Model<M>> M getModel(Class<M> clazz, String properties) 
	{
		M model = Model.create(clazz);
		
		if (model == null) 
		{
			throw new RuntimeException("创建实体失败");
		}
		
		String[] propertyArr = properties.split(",");
		for (String property : propertyArr) 
		{
			String pro = property.trim();
			model.set(pro, getBodyParam(pro));
		}
		return model;
	}
	
	
	@SuppressWarnings("unchecked")
	public <M extends Model<M>> List<M> getModelList(String columnName, Class<M> clazz, String properties) 
	{
		if (getBodyParam(columnName) == null) 
		{
			return new ArrayList<M>();
		}
		
		List<Map<String, Object>> list = (List<Map<String, Object>>)getBodyParam(columnName);
		
		if (list == null || list.isEmpty()) 
		{
			return new ArrayList<M>();
		}
		
		List<M> models = new ArrayList<M>();
		
		String[] propertyArr = properties.split(",");
		for (Map<String, Object> map : list) 
		{
			M model = Model.create(clazz);
			for (String property : propertyArr) 
			{
				String pro = property.trim();
				model.set(pro, map.get(pro));
			}
			models.add(model);
		}
		return models;
	}
	
	
	/**************************
	 ***** 获取参数基本操作 ***** 
	 **************************/
	
	public Object getBodyParam(String name)
	{
		return paramMap.get(name);
	}
	
	
	public String getPara(String name) 
	{
		Object value = getBodyParam(name);
		if (value != null && value instanceof String) 
		{
			return (String)value;
		}
		return value == null ? null : value.toString();
	}
	
	
	public String getPara(String name, String defaultValue) 
	{
		String result = getPara(name);
		return result != null && !"".equals(result) ? result : defaultValue;
	}
	
	
	public Integer getParaToInt(String name) 
	{
		Object value = getBodyParam(name);
		if (value != null && value instanceof Integer) 
		{
			return (Integer)value;
		}
		return value == null ? null : Integer.parseInt(value.toString()); 
	}
	
	
	public Integer getParaToInt(String name, Integer defaultValue) 
	{
		Integer value = getParaToInt(name);
		return value == null ? defaultValue : value; 
	}
	
	
	public Long getParaToLong(String name) 
	{
		Object value = getBodyParam(name);
		if (value != null && value instanceof Long) 
		{
			return (Long)value;
		}
		return value == null ? null : Long.parseLong(value.toString());
	}
	
	
	public Long getParaToLong(String name, Long defaultValue) 
	{
		Long value = getParaToLong(name);
		return value == null ? defaultValue : value;
	}
	
	
	public Boolean getParaToBoolean(String name, Boolean defaultValue) 
	{
		Boolean value = getParaToBoolean(name);
		return value == null ? defaultValue : value;
	}
	
	
	public Boolean getParaToBoolean(String name) 
	{
		Object value = getBodyParam(name);
		if (value == null)
		{
			return null;
		}
		
		if (value instanceof Boolean) 
		{
			return (Boolean)value;
		}
		
		String result = value.toString().trim().toLowerCase();
		if ("1".equals(result) || "true".equals(result))
		{
			return Boolean.TRUE;
		}else if ("0".equals(result) || "false".equals(result))
		{
			return Boolean.FALSE;
		}
		throw new RuntimeException("Can not parse the parameter \"" + result + "\" to boolean value.");
	}
	
	
	public Date getParaToDate(String name, String pattern) 
	{
		Object value = getBodyParam(name);
		if (value == null || value.toString().trim().length() <= 0) 
		{
			return null;
		}
		try {
			return new java.text.SimpleDateFormat(pattern).parse(value.toString());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Date getParaToDate(String name) 
	{
		return getParaToDate(name, "yyyy-MM-dd");
	}
	
	public Date getParaToDate(String name, Date defaultValue) 
	{
		Date value = getParaToDate(name);
		return value == null ? null : value;
	}
	
	
	
	/**************************
	 ******* 返回操作 ********* 
	 **************************/
	
	public MobileParam put(String key, Object value)
	{
		getJsonResult().put(key, value);
		return this;
	}

	public String renderSuccess()
	{
		return getJsonResult().success();
	}
	
	public String renderSuccess(String msgCode, String defaultMsg)
	{
		String msg = ClientMsg.get(msgCode, getLanguage());
		msg = msg == null ? defaultMsg : msg;
		return getJsonResult().success(msgCode, msg);
	}
	
	public String renderFail() 
	{
		return getJsonResult().fail();
	}
	
	public String renderFail(String msgCode, String defaultMsg) 
	{
		String msg = ClientMsg.get(msgCode, getLanguage());
		msg = msg == null ? defaultMsg : msg;
		return getJsonResult().fail(msgCode, msg);
	}
	public String renderExits() 
	{
		return getJsonResult().exits();
	}
	
	public String renderExits(String msgCode, String defaultMsg) 
	{
/*		String msg = ClientMsg.get(msgCode, getLanguage());
		msg = msg == null ? defaultMsg : msg;*/
		return getJsonResult().exits(msgCode, defaultMsg);
	}
	
	private MobileResult getJsonResult() 
	{
		if (jsonResult == null) 
		{
			jsonResult = MobileResult.create();
		}
		return jsonResult;
	}
}
