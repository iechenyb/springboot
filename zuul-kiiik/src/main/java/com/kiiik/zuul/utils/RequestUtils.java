package com.kiiik.zuul.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 */
import org.thymeleaf.util.DateUtils;

import com.kiiik.zuul.pub.bean.SessionUser;
import com.kiiik.zuul.web.auth.bean.SystemUser;
import com.kiiik.zuul.web.log.bean.SystemLog;

public class RequestUtils {
	Log log = LogFactory.getLog(RequestUtils.class);

	public static SystemLog getSystemLog(HttpServletRequest request) {
		SystemLog log = new SystemLog();
		log.setOperator(request.getRemoteUser());
		log.setUri(request.getRequestURI());
		log.setModule("预留");
		log.setClientIp(request.getRemoteHost());
		log.setVisitorTime(DateUtils.formatISO(new Date()));
		return log;
	}

	public static SessionUser getSessionUser(Authentication authentication) {
		SystemUser user = (SystemUser) authentication.getPrincipal();
		SessionUser suser = new SessionUser();
		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
		if (!CollectionUtils.isEmpty(roles)) {
			List<String> rs = new ArrayList<String>(roles.size());
			for (SimpleGrantedAuthority sga : roles) {
				rs.add(sga.getAuthority());
			}
			suser.setRoles(rs);
		} else {
			suser.setRoles(new ArrayList<String>());
		}
		suser.setUserId(user.getId());
		suser.setUserName(user.getUsername());
		return suser;
	}
	
}
