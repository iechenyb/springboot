package com.cyb.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月17日
 */
public class BaseController {
	Log log = LogFactory.getLog(BaseController.class);

	protected String getUser() {
		String user = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("X-AUTH-ID");
		return user;
	}
}
