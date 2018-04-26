package com.cyb.app.sk.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.redis.RedisTempalte;
import com.cyb.app.sk.dao.OrderRepository;
import com.cyb.app.sk.po.Order;
import com.cyb.app.sk.service.OrderService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月26日
 */
@Service("redisOrderServiceImpl")
public class RedisOrderServiceImpl implements OrderService{
	Log log = LogFactory.getLog(RedisOrderServiceImpl.class);
	
    @Autowired
	OrderRepository orderDao;
    
	@Autowired
    RedisTempalte redis;
	
	public boolean makeOrder(String goodsName, long userId, int buys) {
		Object stock =  redis.get(3,goodsName);
		if(stock==null){
			return false;
		 }
		int newStock = Integer.valueOf(stock.toString())-buys;
		if(newStock>=0){
			redis.set(3, goodsName, String.valueOf(newStock));
			Order order = new Order();
			order.setBuys(buys);
			order.setUserId(userId);
			order.setGoodsName(goodsName);
			order.setOrderTime(System.currentTimeMillis());
			orderDao.save(order);
			redis.set(3, userId+"-"+goodsName, String.valueOf(buys));
			return true;
		}else{
			return false;
		}
	}
    //查询下单记录
	public boolean hasMakeOrder(String goodsName, long userId) {
		return redis.exists(3, userId+"-"+goodsName);
	}
}
