package com.cyb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cyb.po.Plan;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {
	@Query("select u from Plan u where u.jhlx = ?1 and u.jhbh=?2") 
	Plan findPlan(String jhlx,String jhbh); 
	
	@Query("select u from Plan u where u.id = ?1") 
	Plan findPlanById(long id); 
}