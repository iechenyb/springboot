package com.example.demo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.CacheAableApplication;
import com.cyb.cacheable.bean.News;
import com.cyb.cacheable.service.CacheableServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheAableApplication.class)
public class CacheTest {
	Log log = LogFactory.getLog(CacheTest.class);
	
	@Autowired
	CacheableServiceImpl service;
	//测试从redis中读数据
	@Test
	public void testGetDataFromRedisCache() throws InterruptedException{
		readNews();
	}
	//测试修改或者新增时将数据更新到缓存
	@Test
	public void testAddModifyToRedisCache() throws InterruptedException{
		System.out.println("=======================MODIFY================");
		News user = new News();//需要序列化
		user.setId("1");
		user.setTitle("name1");
		user.setContent("any002");
		service.save(user.getId(),user.getTitle());//保存修改到db
		System.out.println("=======================MODIFY================");
	}
	//删除user下的某一个key值
	@Test
	public void testDeleteOneToRedisCache() throws InterruptedException{
		System.out.println("=======================delone============");
		News user = new News();//需要序列化
		user.setId("0");
		user.setTitle("name0");
		user.setContent("any003");
		service.delete(user.getId(),user.getTitle());//删除一个指定的user_id缓存数据
		System.out.println("删除成功！");
		//readNews();
		System.out.println("=======================delone================");
	}
	//删除所有user键下的key值
	@Test
	public void testDeleteAllToRedisCache(){
		System.out.println("=======================delall================");	
		service.deleteAll();
		//readNews();
		System.out.println("=======================delall================");
	}
	@Test
	public void readNews() throws InterruptedException{
		for(long i=0;i<5;i++){
			System.out.println("====================1====================");
			News tmp = service.getNewsById(""+i,"name"+i);
			System.out.println("====================2===================="+tmp.getTitle());
		}
	}
}
