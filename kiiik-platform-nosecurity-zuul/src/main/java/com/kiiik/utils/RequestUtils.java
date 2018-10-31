package com.kiiik.utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.DateUtils;

import com.kiiik.pub.bean.SessionUser;
import com.kiiik.web.log.bean.SystemLog;
import com.kiiik.web.system.vo.SystemUser;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日下午3:05:37
 */
public class RequestUtils {
	Log log = LogFactory.getLog(RequestUtils.class);
	
	public static String getProjectAbsPath(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath();
	}
	public static SystemLog getSystemLog(HttpServletRequest request) {
		SystemLog log = new SystemLog();
		if(!StringUtils.isEmpty(request.getRemoteUser())){
			log.setOperator(request.getRemoteUser());
		}else{
			log.setOperator("default");
		}
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
