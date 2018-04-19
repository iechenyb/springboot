package com.cyb.access;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{
	Log log = LogFactory.getLog(UserArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz= parameter.getParameterType();
		return clazz == MyUser.class;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return UserContext.getUser();
	}
}
