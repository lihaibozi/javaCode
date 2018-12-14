package com.kin207.zn.support.resolver;


import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.kin207.zn.AppConst;
import com.kin207.zn.support.MobileParam;

/**
 * APP参数分析类
 * @author wujun
 */
public class MobileParamResolver implements WebArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter parameter, NativeWebRequest request) throws Exception {
		if (parameter.getParameterType() != MobileParam.class) {
			return WebArgumentResolver.UNRESOLVED;
		}
		return ((HttpServletRequest)request.getNativeRequest()).getAttribute(AppConst.MOBILE_KEY_PARAM);
	}
	
}
