package com.cyb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cyb.po.NetNotify;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
public interface NetNotifyRepository extends JpaRepository<NetNotify, Long> {
	@Query("select u from NetNotify u where u.account = ?1 and u.type=?2 and u.time=?3") 
	List<NetNotify> getSendCountBySomeOne(String account,String type,long today); 
}