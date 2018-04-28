package com.cyb.app.sk.service.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.cyb.app.lock.redis.RedisBasedDistributedLock;
import com.cyb.app.lock.redis.RedisUtil;
import com.cyb.app.redis.RedisClient;
import com.cyb.app.redis.RedisTempalte;
import com.cyb.app.sk.dao.OrderRepository;
import com.cyb.app.sk.po.Order;
import com.cyb.app.sk.service.OrderService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月26日
 */
@Service("redisOrderServiceImpl")
public class RedisOrderServiceImpl implements OrderService {
	Log log = LogFactory.getLog(RedisOrderServiceImpl.class);

	@Autowired
	OrderRepository orderDao;

	@Autowired
	RedisTempalte redis;

	/**
	 * 存在超卖的问题decrBy
	 */
	public boolean makeOrder1(String goodsName, long userId, int buys) {
		Object stock = redis.get(3, goodsName);
		if (stock == null) {
			return false;
		}
		int newStock = Integer.valueOf(stock.toString()) - buys;
		if (newStock >= 0) {
			redis.set(3, goodsName, String.valueOf(newStock));
			Order order = new Order();
			order.setBuys(buys);
			order.setUserId(userId);
			order.setGoodsName(goodsName);
			order.setOrderTime(System.currentTimeMillis());
			orderDao.save(order);
			redis.set(3, userId + "-" + goodsName, String.valueOf(buys), 120);// 购买记录存储2分钟
			return true;
		} else {
			return false;
		}
	}

	// 依旧存在少卖的问题
	public boolean makeOrder2(String goodsName, long userId, int buys) {
		// 虽然不会超卖，但是库存容易减为负数
		long newstock = Long.valueOf(redis.decrBy(3, goodsName, Long.valueOf(buys)));
		if (newstock < 0) {
			return false;
		}
		if (newstock >= 0) {
			Order order = new Order();
			order.setBuys(buys);
			order.setUserId(userId);
			order.setGoodsName(goodsName);
			order.setOrderTime(System.currentTimeMillis());
			orderDao.save(order);
			redis.set(3, userId + "-" + goodsName, String.valueOf(buys));
			return true;
		} else {
			return false;
		}
	}

	public boolean makeOrder3(String goodsName, long userId, int buys) {
		// 虽然不会超卖，但是库存容易减为负数
		long newstock = Long.valueOf(redis.decrByCas(3, goodsName, Long.valueOf(buys)));
		if (newstock == 0) {
			return false;
		}
		if (newstock == 1) {
			Order order = new Order();
			order.setBuys(buys);
			order.setUserId(userId);
			order.setGoodsName(goodsName);
			order.setOrderTime(System.currentTimeMillis());
			orderDao.save(order);
			redis.set(3, userId + "-" + goodsName, String.valueOf(buys));
			return true;
		} else {
			return false;
		}
	}
	@Autowired
	RedisClient client;
	
	public Jedis getJedis(){
		Jedis jedis = null;
		for(int i=0;i<5;i++){
			try{
					jedis = client.getRedis();
					if(jedis !=null){
						break;
					}
				}catch(Exception e){
					//获取连接失败，则重新获取5次
				}
		}
		return jedis;
	}
	// base optimitic lock to buy  ok
	public boolean makeOrder5(String goodsName, long userId, int buys) {
		boolean buySuccess = false;
		Jedis jedis = getJedis();
		for (int i = 0; i < 10; i++) { // have try ten times
			System.out.println("顾客:" + userId + "开始抢商品");
			try {
				jedis.watch(goodsName);
				int prdNum = Integer.parseInt(jedis.get(goodsName));// 当前商品个数
				if (prdNum -buys > 0) {
					Transaction transaction = jedis.multi();
					//transaction.set(goodsName, String.valueOf(prdNum - buys));// 每个人只能买一个 则zhengchang
					transaction.decrBy(goodsName, buys);
					List<Object> result = transaction.exec();
					if (result == null || result.isEmpty()) {
						System.out.println("悲剧了，顾客:" + userId + "没有抢到商品");// 可能是watch-key被外部修改，或者是数据操作被驳回
					} else {
						jedis.sadd("clientList", userId+"#"+buys);
						System.out.println("好高兴，顾客:" + userId + "抢到商品");
						buySuccess = true;
						break;
					}
				} else {
					System.out.println("悲剧了，库存为0，顾客:" + userId + "没有抢到商品");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jedis.unwatch();
				client.returnRedis(jedis);
			}
		}
		return buySuccess;
	}
	//悲观锁 
	public boolean makeOrder(String goodsName, long userId, int buys,int port) {
		boolean buySuccess = false;
		Jedis jedis = getJedis();
		RedisBasedDistributedLock redisBasedDistributedLock = new RedisBasedDistributedLock(jedis, "lock.lock", 5 * 1000);
		for(int i=0;i<10;i++){
			//先判断缓存是否有商品
			if(Integer.valueOf(jedis.get(goodsName))<= 0) {
				break;
			}
			//缓存还有商品，取锁，商品数目减去1
			System.out.println("顾客:" + userId + "开始抢商品");
			if (redisBasedDistributedLock.tryLock(3,TimeUnit.SECONDS)) { //等待3秒获取锁，否则返回false
				int prdNum = Integer.valueOf(jedis.get(goodsName)); //再次取得商品缓存数目
				if (prdNum - buys > 0) {
					jedis.decrBy(goodsName, buys);
					jedis.sadd("clientList", userId+"#"+buys+"#"+port);// 抢到商品记录一下
					System.out.println("好高兴，顾客:" + userId + "抢到商品");
					buySuccess = true;
				} else {
					System.out.println("悲剧了，库存为0，顾客:" + userId + "没有抢到商品");
				}
				redisBasedDistributedLock.unlock();
				break;
			}
		}
		//释放资源
		redisBasedDistributedLock = null;
		client.returnRedis(jedis);
		return buySuccess;
	}
	// 查询下单记录
	public boolean hasMakeOrder(String goodsName, long userId) {
		return redis.exists(3, userId + "-" + goodsName);
	}

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void testMiaosha() {
		Object goods = redisTemplate.opsForList().leftPop("sku:awards");
		if (goods != null) {
			int num = new Random().nextInt(1000) + 1;
			Long result = redisTemplate.opsForSet().add("candidate:userids", num + "");
			if (result > 0) {
				System.out.println("成功秒杀");
			} else {
				System.out.println("duplicate :::::: " + num);
				redisTemplate.opsForList().rightPush("sku:awards", "1");
			}
		} else {
			System.out.println("秒杀失败");
		}
	}
}
