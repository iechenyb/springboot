package com.cyb.app.sk.service;

public interface GoodsService {
	//初始化库存
	public void initStock(String goodsName,int sotck);
	//扣减库存
	public boolean decStock(String goodsName,int buys);
	//查询库存
	public int queryStock(String goodsName);
}
