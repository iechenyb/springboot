package com.cyb.app.sk.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.sk.dao.GoodsRepository;
import com.cyb.app.sk.dao.OrderRepository;
import com.cyb.app.sk.po.Order;
import com.cyb.app.sk.service.OrderService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月26日
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService{
	Log log = LogFactory.getLog(OrderServiceImpl.class);
	
    @Autowired
	OrderRepository orderDao;
    
	@Autowired
    GoodsRepository goodsDao;
	
	public boolean makeOrder(String goodsName, long userId, int buys,int port) {
		//修改库存
		int updateRecords = goodsDao.decGoods(goodsName, buys);
		if(updateRecords>0){
			Order order = new Order();
			order.setBuys(buys);
			order.setUserId(userId);
			order.setGoodsName(goodsName);
			order.setOrderTime(System.currentTimeMillis());
			orderDao.save(order);
			return true;
		}else{
			return false;
		}
	}

	public boolean hasMakeOrder(String goodsName, long userId) {
		return orderDao.hasMakeOrder(userId, goodsName)==null?false:true;
	}
}
