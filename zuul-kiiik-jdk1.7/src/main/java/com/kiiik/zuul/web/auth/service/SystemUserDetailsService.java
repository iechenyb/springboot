package com.kiiik.zuul.web.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kiiik.zuul.web.auth.bean.SystemUser;

@Component
public class SystemUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AuthServiceImpl authDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	SystemUser user = authDao.getSystemUser(username);
		try {
			System.out.println("user:"+user);
			if(user==null){
				throw new UsernameNotFoundException("用户名或密码不正确");
			}
			return user;
		} catch (Exception e) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
    }
}