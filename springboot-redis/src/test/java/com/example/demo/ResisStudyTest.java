package com.example.demo;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.RedisApplication;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class ResisStudyTest {
	@Autowired
	ShardedJedisPool shardedJedisPool;

	@Autowired
	JedisPool jedisPool;
	
   /* @AfterClass
    public void destory(){
    	shardedJedisPool.destroy();
    	jedisPool.destroy();
    }*/
	@Test
	public void testJedisNormal() {
		Long s = System.currentTimeMillis();
		Jedis jedis = jedisPool.getResource();
		for (int i = 1; i <= 100000; i++) {
			jedis.set("x" + i, "x" + i);
		}
		Long e = System.currentTimeMillis();
		System.out.println("不用事务的普通的jedis执行耗时" + (e - s) / 1000 + "s"+(e - s) % 1000 + "ms");//3s
		//4s
	}
	
	@Test
	public void testJedisPipeLine() {
		Long s = System.currentTimeMillis();
		Jedis jedis = jedisPool.getResource();
		Pipeline pipe = jedis.pipelined();
		for (int i = 1; i <= 100000; i++) {
			pipe.set("x" + i, "x" + i);
		}
		pipe.syncAndReturnAll();
		Long e = System.currentTimeMillis();
		System.out.println("testJedisPipeLine执行耗时" + (e - s) / 1000 + "s"+(e - s) % 1000 + "ms");//3s
		//208ms
	}
	
	@Test
	public void testJedisPipeLineTran() {
		Long s = System.currentTimeMillis();
		Jedis jedis = jedisPool.getResource();
		Pipeline pipe = jedis.pipelined();
		pipe.multi();
		for (int i = 1; i <= 100000; i++) {
			pipe.set("x" + i, "x" + i);
		}
		pipe.exec();
		pipe.syncAndReturnAll();
		Long e = System.currentTimeMillis();
		System.out.println("testJedisPipeLine执行耗时" + (e - s) / 1000 + "s"+(e - s) % 1000 + "ms");//3s
		//266ms
	}

	@Test
	public void testJedisWithTransaction() {
		Long s = System.currentTimeMillis();
		Jedis jedis = jedisPool.getResource();
		Transaction tx = jedis.multi();
		for (int i = 1; i <= 100000; i++) {
			tx.set("x" + i, "x" + i);
		}
		@SuppressWarnings("unused")
		List<Object> rs=tx.exec();
		Long e = System.currentTimeMillis();
		System.out.println("用事务的普通的jedis执行耗时" + (e - s) / 1000 + "s"+(e - s) % 1000 + "ms");
		jedis.disconnect(); 
		//258ms
	}
	
	@SuppressWarnings("deprecation")
	@Test
    public void shardNormal(){
    	Long s = System.currentTimeMillis();
    	ShardedJedis one = shardedJedisPool.getResource();
    	for (int i = 1; i <= 100000; i++) {
    		one.set("x" + i, "x" + i);
		}
    	shardedJedisPool.returnResource(one);
    	Long e = System.currentTimeMillis();
    	System.out.println("shardNormal执行耗时" + (e - s) / 1000+"s"+(e - s) % 1000 + "ms");
    	//4s.301ms
    }
	
	@SuppressWarnings({ "unused", "deprecation" })
	@Test
    public void shardPipelinedPool(){
    	Long s = System.currentTimeMillis();
    	ShardedJedis one = shardedJedisPool.getResource();
    	ShardedJedisPipeline pipeline = one.pipelined();
    	for (int i = 1; i <= 100000; i++) {
    		pipeline.set("x" + i, "x" + i);
		}
    	List<Object> rs = pipeline.syncAndReturnAll();
    	shardedJedisPool.returnResource(one);
    	Long e = System.currentTimeMillis();
    	System.out.println("shardNormal执行耗时" + (e - s) / 1000+"s"+ (e - s) % 1000 + "ms");//301ms
       //270ms
	}
	
	@Test
	public void testLogin(){
		Jedis jedis = jedisPool.getResource();
		jedis.set("ip", "localhost");
		jedis.expire("ip", 60);//60秒内过期
	}
}
