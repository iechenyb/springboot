package com.cyb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.Plan;
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
	
	/*public int getLoginLog(String username){
		List list = jdbcTemplate.queryForList("SELECT username FROM MS_SECURITY_USER_LOGIN_LOG where username= '"+username+"'");
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}else{
			return list.size();
		}
	}*/
	public UserLoginLog getLoginLog(String username){
		return this.get("username", username);
	}
	public void deletePlans(){
		String sql = "delete from  WEB_YH_JH ";
		jdbcTemplate.execute(sql);
	}
}