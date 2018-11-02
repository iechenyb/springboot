package com.kiiik.web.system.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User{
	private Integer id;//用户主键
	private String showUserName;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemUser(String empno, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(empno, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShowUserName() {
		return showUserName;
	}

	public void setShowUserName(String showUserName) {
		this.showUserName = showUserName;
	}

	
    
}
