package com.cyb.app.sk.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.redis.RedisTempalte;
import com.cyb.app.sk.dao.GoodsRepository;
import com.cyb.app.sk.service.GoodsService;
/**
 *作者 : iechenyb<br>
 *类描述: db中操作库存<br>
 *创建时间: 2018年4月26日
 */
@Service("redisGoodsServiceImpl")
public class RedisGoodsServiceImpl implements GoodsService{
	Log log = LogFactory.getLog(RedisGoodsServiceImpl.class);
	
	@Autowired
	RedisTempalte redis;
	
	public void initStock(String goodsName, int stock) {
		redis.set(3, goodsName, String.valueOf(stock));
	}

	public boolean decStock(String goodsName, int buys) {
		Object stock =  redis.get(3,goodsName);
		if(stock==null){
			return false;
		 }
		int newStock = Integer.valueOf(stock.toString())-buys;
		if(newStock>=0){
			redis.set(3, goodsName, String.valueOf(newStock));
			return true;
		}else{
			return false;
		}
		
	}

	public int queryStock(String goodsName) {
		Object stock =  redis.get(3,goodsName);
		if(stock==null){
			return 0;
		 }
		return Integer.valueOf(stock.toString());
	}
}
