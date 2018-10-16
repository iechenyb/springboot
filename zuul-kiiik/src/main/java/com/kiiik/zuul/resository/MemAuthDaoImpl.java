package com.kiiik.zuul.resository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:06:01
 */
@Component
public class MemAuthDaoImpl implements UserAuthDao {
	Log log = LogFactory.getLog(MemAuthDaoImpl.class);
    /**
     * 读取用户角色资源信息
     */
	public Map<String, String> roleResources(String username) {
		Map<String, String> auths = new HashMap<String, String>();
		//管理员权限
		auths.put("/api1/api/add", "ROLE_ADD");
		auths.put("/api1/api/delete", "ROLE_DELETE");
		auths.put("/api1/api/update", "ROLE_UPDATE");
		auths.put("/api1/api/query", "ROLE_QUERY");
		return auths;
	}
	public Map<String, ConfigAttribute> roleConfigAttribute(String username){
    	Map<String,ConfigAttribute> auths = new HashMap<String,ConfigAttribute>();
    	Map<String, String>  resources = roleResources();
		for(String url:roleResources().keySet()){
			ConfigAttribute configAttribute = new SecurityConfig(resources.get(url));// 资源标识
    		auths.put(url, configAttribute);
		}
    	return auths;
    }

	public Collection<? extends GrantedAuthority> getAuthorities(String username) {
		log.info("username="+username);
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		// 根据自定义逻辑来返回用户权限，如果用户权限返回空或者和拦截路径对应权限不同，验证不通过ROLE_
		if (username.equals("add")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_ADD");
			list.add(au);
		} else if (username.equals("del")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_DELETE");
			list.add(au);
		} else if (username.equals("query")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_QUERY");
			list.add(au);
			// 没有任何角色
		} else if (username.equals("upd")) {
			GrantedAuthority au = new SimpleGrantedAuthority("ROLE_UPDATE");
			list.add(au);
		}
		System.out.println("role:"+list.get(0).getAuthority());
		return list;
	}

	public Map<String, String> roleResources() {
		return roleResources(null);
	}
}
