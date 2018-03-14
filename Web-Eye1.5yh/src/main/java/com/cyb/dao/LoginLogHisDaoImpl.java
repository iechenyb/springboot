package com.cyb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.Plan;
import com.cyb.po.UserLoginLogHistory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Repository
public class LoginLogHisDaoImpl extends HibernateBaseDao<UserLoginLogHistory> {
	@Autowired
	JdbcTemplate jdbcTemplate;
}