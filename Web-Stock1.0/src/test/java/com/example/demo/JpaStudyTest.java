package com.example.demo;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cyb.MyBootStarter;
import com.cyb.dao.MyUserRepository;
import com.cyb.po.MyUser;
import com.cyb.service.UserServiceImpl;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月27日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
public class JpaStudyTest {
	Log log = LogFactory.getLog(JpaStudyTest.class);
	@Autowired
	MyUserRepository userDao;
	@Autowired
	UserServiceImpl userService;
	@Test
	public void testJpa(){
		System.out.println(userDao.count());
		List<MyUser> users = userDao.findAll();
		for(MyUser user:users){
			System.out.println(user.getUser_id()+","+user.getUsername()+","+user.getPassword()+","+user.getZt());
		}
		System.out.println(userDao.exists(1l));
		System.out.println(userDao.findByUsername1("user").get(0).getUsername());
		System.out.println(userDao.findByUsername2("admin").get(0).getUsername());
		//userDao.setUserName("iechenyb",33l);
		System.out.println(userDao.getOne(33L).getUsername());
		userService.update("iechenyb",33l);
		//userDao.
	}
}
