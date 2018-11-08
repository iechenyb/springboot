package com.kiiik.web.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.contant.Contants_Test;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.vo.SystemUser;
import com.kiiik.web.system.vo.UserRoleVo;

@Component
public class SystemUserDetailsService implements UserDetailsService {
    
    @Autowired
    private  GenericService genericService;
    
    @Autowired
    UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String empno) throws UsernameNotFoundException {
    	User user = new User();
    	user.setEmpNo(empno);
    	user = genericService.queryDBEntitySingle(user);
    	if(user==null){
    		throw new UsernameNotFoundException("用户名或密码不正确");
    	}
    	List<UserRoleVo> roles = userService.getUserRoles(user.getId());
    	SystemUser sysUser =  new SystemUser(
				user.getEmpNo()
				,user.getPassword()
				,user.getIsEffect()==1?true:false,
				true,
				true,
				true,
				getAuthoritiesById(roles,empno));
    	sysUser.setId(user.getId());
    	sysUser.setShowUserName(user.getUserName());
		return sysUser;
    }
    
    public Collection<? extends GrantedAuthority> getAuthoritiesById(List<UserRoleVo> roles,String empno) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		if(!CollectionUtils.isEmpty(roles)){
			for(UserRoleVo role:roles){
				list.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
		}
		list.add(Contants_Test.testRoles(empno));
		return list;
	}
    
}