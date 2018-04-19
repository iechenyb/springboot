package com.example.demo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cyb.MyBootStarter;
import com.cyb.date.DateUtil;
import com.cyb.po.Flight;
import com.cyb.po.Message;
import com.cyb.service.TransactionSeviceImpl;
/**
 *作者 : iechenyb<br>
 *类描述:单元测试 事务控制存在问题，不可以测试事务处理机制<br>
 *创建时间: 2018年4月3日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "transactionManager")
@SpringBootTest(classes = MyBootStarter.class)
public class TransactionTest {
	Log log = LogFactory.getLog(TransactionTest.class);
	@Autowired
	TransactionSeviceImpl service;
	
	@Test
	public void saveFlight(){
		System.out.println("=============1================");
		Flight f = new Flight();
		//f.setId(1L); 自动设置id
		f.setName("009 flight");
		f.setTime(DateUtil.date2long14());
		service.saveFlight(f);
		System.out.println("=============2================");
	}
	
	@Test
	public void saveMessage(){
		System.out.println("=============1================");
		Flight f = new Flight();
		f.setName("009 flight");
		f.setTime(DateUtil.descTimeToSec());
		
		Message m = new Message();
		m.setMsg("009 ordered success！");
		m.setFid(f.getId());
		m.setTime(DateUtil.date2long14());
		service.saveMessage(m);
		System.out.println("=============2================");
	}
}
