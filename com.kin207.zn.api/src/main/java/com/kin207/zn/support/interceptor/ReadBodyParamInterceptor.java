package com.kin207.zn.support.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kin207.zn.AppConst;
import com.kin207.zn.support.MobileKit;
import com.kin207.zn.support.MobileParam;

import common.utils.HttpRequestUtil;
import common.utils.JsonUtil;

/**
 * 拦截器处理
 */
public class ReadBodyParamInterceptor extends HandlerInterceptorAdapter{

	private static Logger log = Logger.getLogger(ReadBodyParamInterceptor.class);
	
	private static ThreadLocal<Long> start = new ThreadLocal<Long>();
	
	/**
	 * 请求处理之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		log.info("----------------------------------------");
		log.info("请求URL：" + HttpRequestUtil.currrentUrl(request));
		start.set(System.currentTimeMillis());
		MobileParam mobileParam = null;
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        key = key.toUpperCase();
	        String value = request.getHeader(key).toUpperCase();
	        map.put(key, value);
	    }
//	    map.put("SYSLANGUAGE", "EN");
		String encryptText = HttpRequestUtil.readStream(request);
		String jsonStr = encryptText;
		//String jsonStr = MobileKit.decryptMobileText(encryptText);//加密处理 kinfeng modify
		log.info("参数：" + jsonStr);
		if(jsonStr != null && !"".equals(jsonStr)){
			Map<String, Object> paramMap = JsonUtil.toMap(jsonStr);
			paramMap.putAll(map);
			mobileParam = new MobileParam(paramMap);
		}else{
			String str = "{}";
			Map<String, Object> paramMapNull = JsonUtil.toMap(str);
			paramMapNull.putAll(map);
			mobileParam = new MobileParam(paramMapNull);
		}
		/*Map<String, Object> paramMap = JsonUtil.toMap(jsonStr);
		  MobileParam mobileParam = new MobileParam(paramMap);*/
		request.setAttribute(AppConst.MOBILE_KEY_PARAM, mobileParam);
		
		//true继续调用下一个Interceptor的preHandle方法,如果已经是最后一个Interceptor的时候调用当前请求的Controller方法
		return true;
	}
	
	/**
	 * 请求结束之后调用(需要当前对应的Interceptor的preHandle方法返回值是true时才会执行)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		long costMs = System.currentTimeMillis() - start.get();
		log.warn(" 执行消耗时间：" + costMs + " " + HttpRequestUtil.currrentUrl(request));
		log.info("----------------------------------------");
	}
}
