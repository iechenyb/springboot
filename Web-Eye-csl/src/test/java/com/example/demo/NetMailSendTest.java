package com.example.demo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cyb.MyBootStarter;
import com.cyb.service.NetNotifyService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月4日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
public class NetMailSendTest {
	Log logger = LogFactory.getLog(getClass());
	
   @Autowired
	NetNotifyService service;
	
    @Test
    public void sendSimpleMail() throws Exception {
    	//service.sendMail();
    	service.writeException();
    }
  
}
