package com.cyb.app.sk.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.lock.redis.RedisUtil;
import com.cyb.app.redis.RedisClient;

import redis.clients.jedis.Jedis;

/**
 * redis乐观锁实例
 * 
 * @author iechenyb
 *
 */
@Service
public class OptimisticLockService {
	int prdNum = 3;// 商品个数
	int clientNum = 10000;// 模拟客户数目
	@Autowired
	RedisClient client;

	/**
	 * 输出结果
	 */
	public void printResult() {
		Jedis jedis = client.getRedis();
		Set<String> set = jedis.smembers("clientList");
		int i = 1;
		for (String value : set) {
			System.out.println("第" + i++ + "个抢到商品，" + value + " ");
		}
		client.returnRedis(jedis);
	}

	/**
	 * 初始化商品个数
	 */
	public void initPrduct(String goodsName, int prdNum) {
		String key = goodsName;
		String clientList = "clientList";// 抢购到商品的顾客列表
		Jedis jedis = client.getRedis();
		if (jedis.exists(key)) {
			jedis.del(key);
			jedis.del(key+"_bak");
		}
		
		if (jedis.exists(clientList)) {
			jedis.del(clientList);
		}
		jedis.set(key, String.valueOf(prdNum));// 初始化
		jedis.set(key+"_bak", String.valueOf(prdNum));
		client.returnRedis(jedis);
	}

	public Map<String, Object> readPrduct(String goodsName) {
		String key = goodsName;
		String clientList = "clientList";// 抢购到商品的顾客列表
		Jedis jedis = client.getRedis();
		Map<String, Object> infor = new LinkedHashMap<String, Object>();
		infor.put("商品名称", goodsName);
		Set<String> set = jedis.smembers(clientList);
		int saledSum=0;
		for(String str:set){
			saledSum = saledSum+Integer.valueOf(str.split("#")[1]);
		}
		infor.put("商品预售数", jedis.get(key+"_bak"));
		infor.put("商品秒杀数", saledSum);
		infor.put("商品剩余库存", jedis.get(key));
		infor.put("抢购成功客户信息", set);
		client.returnRedis(jedis);
		return infor;
	}
}
