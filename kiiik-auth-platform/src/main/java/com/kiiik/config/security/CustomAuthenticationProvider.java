package com.kiiik.config.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.kiiik.vas.base.dao.UserAuthDao;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月3日下午1:56:52
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	Log log = LogFactory.getLog(CustomAuthenticationProvider.class);
	@Autowired
	private UserAuthDao userAuthDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginName = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		if (vaildateUser(loginName, password, grantedAuths)) {
			Authentication auth = new UsernamePasswordAuthenticationToken(loginName, password, grantedAuths);
			return auth;
		} else {
			return null;
		}
	}

	public boolean vaildateUser(String loginName, String password, List<GrantedAuthority> grantedAuths) {
		/*
		 * User user = userRepository.findByLoginName(loginName); if (user ==
		 * null || loginName == null || password == null) { return false; } if
		 * (user.getPassword().equals(SHA.getResult(password)) &&
		 * user.getUserStatus().equals(UserStatus.NORMAL)) { Set<Role> roles =
		 * user.getRoles(); if (roles.isEmpty()) { grantedAuths.add(new
		 * SimpleGrantedAuthority(RoleType.USER.name())); } for (Role role :
		 * roles) { grantedAuths.add(new
		 * SimpleGrantedAuthority(role.getRoleType().name())); logger.debug(
		 * "username is " + loginName + ", " + role.getRoleType().name()); }
		 * return true; }
		 */
		grantedAuths.addAll(userAuthDao.getAuthorities(loginName));
		return true;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
