package com.example.demo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cyb.MyBootStarter;
import com.cyb.h2.H2Manager;
import com.cyb.po.UserLoginLog;
import com.cyb.service.LoginLogServiceImpl;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月4日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
public class UpdateExceptionTest {
	Log logger = LogFactory.getLog(getClass());
	
   @Autowired
   LoginLogServiceImpl service;
	
    @Test
    public void updateTest() throws Exception {
    	UserLoginLog log = service.getLog();
    	log.setCount(100L);
    	service.updateTest(log);
    }
}
