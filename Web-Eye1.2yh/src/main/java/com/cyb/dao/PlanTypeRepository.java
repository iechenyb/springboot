package com.cyb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cyb.po.PlanType;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
public interface PlanTypeRepository extends JpaRepository<PlanType, Long> {
	@Query("SELECT u  FROM PlanType u where u.jhlx=?1 ") 
	List<PlanType> findPlanJhlx(String jhlx); 
}