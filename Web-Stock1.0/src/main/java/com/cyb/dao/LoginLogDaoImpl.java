package com.cyb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.UserLoginLog;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Repository
public class LoginLogDaoImpl extends HibernateBaseDao<UserLoginLog> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public UserLoginLog getLoginLog(String username){
		return this.get("username", username);
	}
	
	public void updateMy(UserLoginLog o){
		this.getSession().update(o);
	}
}