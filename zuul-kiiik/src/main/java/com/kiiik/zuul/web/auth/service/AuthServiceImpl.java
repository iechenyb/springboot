package com.kiiik.zuul.web.auth.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.kiiik.zuul.web.auth.bean.RoleMenu;
import com.kiiik.zuul.web.auth.bean.SystemUser;
import com.kiiik.zuul.web.auth.dao.AuthDaoImpl;
import com.kiiik.zuul.web.contant.Contants_Test;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:06:01
 */
@Repository
public class AuthServiceImpl {
	Log log = LogFactory.getLog(AuthServiceImpl.class);
   
	@Autowired
	AuthDaoImpl authDao;
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 查询系统的所有 角色-权限信息<br>
	 *创建时间: 2018年10月23日
	 *@return
	 */
	public Map<String, Collection<ConfigAttribute>> systemRoleMenus(){
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
		List<RoleMenu> auths = authDao.getSystemRoleMenus();
		auths.addAll(Contants_Test.testRoleMenu());
		if(!CollectionUtils.isEmpty(auths)){
			for(RoleMenu rm:auths){
				//同一个url对应多个角色
				if(!StringUtils.isEmpty(rm.getUrl())){
					if(resourceMap.get(rm.getUrl())==null){
						resourceMap.put(rm.getUrl(), new HashSet<ConfigAttribute>());
					}
					ConfigAttribute configAttribute = new SecurityConfig(rm.getRoleName());// 角色标记
					resourceMap.get(rm.getUrl()).add(configAttribute);
				}
			}
		}
		return resourceMap;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据用户的名称(职工号)查询角色信息<br>
	 *创建时间: 2017年7月15日
	 *@param username
	 *@return
	 */
	public Collection<? extends GrantedAuthority> getAuthoritiesByName(String username) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		return list;
	}
	
	public SystemUser getSystemUser(String username){
		return authDao.getSystemUser(username);
	}
	
}
