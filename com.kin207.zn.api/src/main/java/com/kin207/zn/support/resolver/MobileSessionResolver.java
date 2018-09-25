package com.kin207.zn.support.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.kin207.zn.AppConst;
import com.kin207.zn.support.MobileSession;

/**
 * APP session 分析类
 * @author wujun
 */
public class MobileSessionResolver implements WebArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter parameter, NativeWebRequest request) throws Exception {
		if (parameter.getParameterType() != MobileSession.class) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		String userId = request.getHeader(AppConst.MOBILE_KEY_USER_ID);
		if (userId == null) {
			return MobileSession.build(null);
		}
		
		return MobileSession.build(Integer.valueOf(userId));
	}
	
}
