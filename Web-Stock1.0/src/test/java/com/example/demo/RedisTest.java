package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cyb.MyBootStarter;
import com.cyb.redis.RedisClient;
import com.cyb.redis.RedisKVPo;
import com.cyb.redis.RedisTempalte;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
public class RedisTest {
	Log log = LogFactory.getLog(RedisTest.class);
	
	@Autowired
    private RedisTempalte redisTempalte;
	
	@Autowired
	private RedisClient client;
	//默认60秒到期
	@Test
	public void testCommon(){
		long s = System.currentTimeMillis();
		for(int i=0;i<10000;i++){
			redisTempalte.del(3, "xx"+i);
		}
		long e = System.currentTimeMillis();
		System.out.println("耗时："+(e-s));
	}
	//默认60秒到期
	@Test
	public void testPipleLine(){
		long s = System.currentTimeMillis();
		List<RedisKVPo> list = new ArrayList<RedisKVPo>();
		for(int i=0;i<10;i++){
			list.add(new RedisKVPo( "xx"+i, "xx"));
		}
		redisTempalte.setPipeLine(3, list);
		long e = System.currentTimeMillis();
		System.out.println("耗时："+(e-s));
	}
	//为什么Redis不支持回滚？
	@Test
	public void testTx(){
		Jedis jedis = client.getRedis();
		jedis.resetState();
		Transaction t = jedis.multi();
		t.watch("age");
		/*jedis.set("name", "chenyb");
			jedis.set("age", "20");
			jedis.incr("age");
			Cannot use Jedis when in Multi. Please use Transation or reset jedis state.
		*/
		t.select(4);
		t.set("name", "chenyb");
		t.set("age", "string");
		t.incr("age");
		//[OK, OK, OK, redis.clients.jedis.exceptions.JedisDataException: ERR value is not an integer or out of range]
		List<Object> res = t.exec();
		System.out.println(res);
		if(res!=null){
			System.out.println("执行成功");
		}else{
			System.out.println("执行失败");
		}
	}
	
}
