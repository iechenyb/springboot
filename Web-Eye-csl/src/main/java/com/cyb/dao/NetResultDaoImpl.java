package com.cyb.dao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.NetResult;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Repository
public class NetResultDaoImpl extends HibernateBaseDao<NetResult>{
	Log log = LogFactory.getLog(NetResultDaoImpl.class);
	
	@Autowired
	public	NetResultRepository rep;//没有事务，不能成功保存到数据库
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void saveNetResult(NetResult nr){
		entityManager.persist(nr);
	}
}
