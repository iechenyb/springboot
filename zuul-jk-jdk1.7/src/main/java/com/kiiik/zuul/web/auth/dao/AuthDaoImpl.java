package com.kiiik.zuul.web.auth.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.kiiik.zuul.web.auth.bean.RoleMenu;
import com.kiiik.zuul.web.auth.bean.SystemUser;
import com.kiiik.zuul.web.contant.Contants_Test;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月23日
 */
@Repository
public class AuthDaoImpl {
	Log log = LogFactory.getLog(AuthDaoImpl.class);
	
	@Autowired
    JdbcTemplate jdbctemplate;
	
	public List<RoleMenu> getSystemRoleMenus(){
		return jdbctemplate.query("select rolename,url from t_sys_role_menu rm ,t_sys_role r,t_sys_menu m where rm.menuid=m.id and rm.roleId=r.id", new RowMapper<RoleMenu>(){
			@Override
			public RoleMenu mapRow(ResultSet rs, int rownum) throws SQLException {
				RoleMenu rm = new RoleMenu();
				rm.setRoleName(rs.getString("rolename"));
				rm.setUrl(rs.getString("url"));
				return rm;
			}
			
		});
	}
	
	public List<String> getRoleByUserId(Integer userId){
		return jdbctemplate.queryForList("select rolename from t_sys_role r ,t_sys_user_role ur where r.id = ur.roleid and ur.userid="+userId,
				String.class);
	}
	
	public SystemUser getSystemUser(String username){
		SystemUser user =  null;
		try{
		 	user = jdbctemplate.queryForObject("select * from t_sys_user where empno='"+username+"'", new RowMapper<SystemUser>(){
				@Override
				public SystemUser mapRow(ResultSet rs, int rownum) throws SQLException {
					SystemUser user = new SystemUser(rs.getString("empno"),
							rs.getString("password"),
							rs.getBoolean("iseffect"),
							true,true,true,
							getAuthoritiesById(rs.getInt("id"),rs.getString("empno"))
							);
					user.setId(rs.getInt("id"));
					return user;
				}
		 	});
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据用户主键查询角色信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param username
	 *@return
	 */
	public Collection<? extends GrantedAuthority> getAuthoritiesById(Integer userId,String empno) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		List<String> roles = getRoleByUserId(userId);
		if(!CollectionUtils.isEmpty(roles)){
			for(String role:roles){
				list.add(new SimpleGrantedAuthority(role));
			}
		}
		list.add(Contants_Test.testRoles(empno));
		return list;
	}
}
