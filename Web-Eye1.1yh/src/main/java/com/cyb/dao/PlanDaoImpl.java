package com.cyb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.Plan;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Repository
public class PlanDaoImpl extends HibernateBaseDao<Plan> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void updatePlan(Plan plan){
		String sql = "update WEB_YH_JH  set content=?"
		+",time='"+plan.getTime()+"' where id= ?";
		jdbcTemplate.update(sql,new Object[]{plan.getContent()+"cyb",plan.getId()});
	}
	public void deletePlans(){
		String sql = "delete from  WEB_YH_JH ";
		jdbcTemplate.execute(sql);
	}
}