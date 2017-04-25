package com.cyb.app.order.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.app.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
  Log log = LogFactory.getLog(OrderController.class);
  @Resource(name="orderService")
  OrderService orderService;
  @Autowired  
  private OrderService orderService0; 
  @RequestMapping("/query")
  public String getId(String id) {
	log.info(id +",service="+this.orderService+","+this.orderService0);
	this.orderService.print("中文测试，哈哈！");
	this.orderService0.print("中文测试，嘿嘿！");
    return id.toString();
  }
}
