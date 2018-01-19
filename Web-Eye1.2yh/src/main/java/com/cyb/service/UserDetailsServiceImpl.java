package com.cyb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyb.dao.MyUserRepository;
import com.cyb.exception.StatusNotAllowException;
import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private MyUserRepository applicationUserRepository;

	public UserDetailsServiceImpl(MyUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser applicationUser = null;
		applicationUser = applicationUserRepository.findByUsername(username);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(username);
		}
		if("1".equals(applicationUser.getZt())){
			throw new StatusNotAllowException("账号已经被禁用!");
		}
		return new User(applicationUser.getUsername(), applicationUser.getPassword(), getAuthorities(username));
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		// 根据自定义逻辑来返回用户权限，如果用户权限返回空或者和拦截路径对应权限不同，验证不通过
		if (username.equals("ymh20180119")||username.equals("cyb")) {//管理员
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_ADMIN");
			list.add(au);
			GrantedAuthority au1 = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au1);
		} else {//普通用户
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au);
		}/*else{// any
			GrantedAuthority au1 = new SimpleGrantedAuthority("ROLE_USER");
			list.add(au1);
			GrantedAuthority au2 = new SimpleGrantedAuthority("ROLE_ADMIN");
			list.add(au2);
		}*/
		System.out.println("初始化角色信息！"+list.size()+","+list.get(0).getAuthority());
		return list;
	}
}
