package com.cyb.app.order.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.order.dao.OrderDao;

@Service
public class OrderService {
	  @Resource(name="orderDao")
	  OrderDao orderDao;
	  @Autowired  
	  private OrderDao orderDao0; 
      public void print(String str){
       orderDao.print(str);
       orderDao0.print(str);
     }
}
