package com.cyb.app.sk.service;

public interface OrderService {
	//下单
	public boolean makeOrder(String goodsName,long userId,int buys,int port);
	//是否已经购买
	public boolean hasMakeOrder(String goodsName,long userId);
}
