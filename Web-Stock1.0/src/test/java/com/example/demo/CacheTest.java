package com.example.demo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cyb.MyBootStarter;
import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月3日
 */
import com.cyb.service.CacheableServiceImpl;

import ch.qos.logback.core.net.SyslogOutputStream;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
public class CacheTest {
	Log log = LogFactory.getLog(CacheTest.class);
	
	@Autowired
	CacheableServiceImpl service;
	//测试从redis中读数据
	@Test
	public void testAddToRedisCache(){
		for(long i=0;i<5;i++){
			System.out.println("====================1====================");
			MyUser user = new MyUser();//需要序列化
			user.setUser_id(i);
			user.setUsername("iechenyb");
			user.setZt("any");
			user.setPassword("dont tell you");
			MyUser tmp = service.getUserByName(i);
			System.out.println("====================2===================="+tmp.getUsername());
		}
	}
	//测试修改或者新增时将数据更新到缓存
	@Test
	public void testAddModifyToRedisCache(){
		System.out.println("=======================1================");
		MyUser user = new MyUser();//需要序列化
		user.setUser_id(1l);
		user.setUsername("iechenyb002");
		user.setZt("any002");
		user.setPassword("dont tell you003");
		service.save(user);//保存修改到db
		MyUser tmp = service.getUserByName(1L);//从缓存中读取数据
		System.out.println("=======================2================"+tmp.getZt());
	}
	//删除user下的某一个key值
	@Test
	public void testDeleteOneToRedisCache(){
		System.out.println("=======================1================");
		MyUser user = new MyUser();//需要序列化
		user.setUser_id(1l);
		user.setUsername("iechenyb002");
		user.setZt("any002");
		user.setPassword("dont tell you003");
		service.delete(user);//删除一个指定的user_id缓存数据
		System.out.println("删除成功！");
		MyUser tmp = service.getUserByName(1L);//从缓存中读取数据
		System.out.println("=======================2================"+tmp.getZt());
	    service.getUserByName(1L);//从缓存中读取数据
	}
	//删除所有user键下的key值
	@Test
	public void testDeleteAllToRedisCache(){
		System.out.println("=======================1================");	
		service.deleteAll();
		MyUser tmp = service.getUserByName(1L);//从缓存中读取数据
		System.out.println("=======================2================"+tmp.getZt());
	    System.out.println("开始查询...");
		service.getUserByName(1L);//从缓存中读取数据
	}
	
}
