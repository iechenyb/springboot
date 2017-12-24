package com.cyb.app.order.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
@Repository("orderDao")
public class OrderDao {
	Log log = LogFactory.getLog(OrderDao.class);
	public void print(String str){
		log.info("dao print "+str);
	}
}
