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
import com.cyb.redis.RedisKVPo;
import com.cyb.redis.RedisTempalte;
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
	
}
