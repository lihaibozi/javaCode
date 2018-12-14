package com.kin207.zn.support.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.kin207.zn.AppConst;
import com.kin207.zn.support.H5Param;

/**
 * H5参数分析类
 * @author wujun
 */
public class H5ParamResolver implements WebArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter parameter, NativeWebRequest request) throws Exception {
		if (parameter.getParameterType() != H5Param.class) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		String userId = request.getHeader(AppConst.MOBILE_KEY_USER_ID);
		String appVersion = request.getHeader(AppConst.MOBILE_KEY_APP_VERSION);
		String os = request.getHeader(AppConst.MOBILE_KEY_OS);
		
		return H5Param.build(userId, os, appVersion);
	}
	
}
