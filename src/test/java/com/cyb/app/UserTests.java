package com.cyb.app;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyb.app.user.dao.UserRepository;
import com.cyb.app.user.pojo.User;


@RunWith(SpringRunner.class)
@SpringBootTest(properties="application.properties")
public class UserTests {
	@Autowired
	private UserRepository userRepository;
	@SuppressWarnings("deprecation")
	@Test
	public void contextLoads() {
		System.out.println("exec");
		User user = userRepository.findUserByName("chenyb");//dao层如何实现
		if(user!=null){
			System.out.println(user.getUsername());
		}
		//save();
		//queryUser();
		Assert.assertNotNull("没有查到值！", user);
	}
	public void save(){
		User user_ = new User();
		user_.setAddress("xxxx");
		user_.setSex("1");
		user_.setUsername("chenyb");
		userRepository.save(user_);
	}
	public void queryUser(){
		User user = userRepository.findUserByName("chenyb");//dao层如何实现
		if(user!=null){
			System.out.println(user.getUsername());
		}
	}	
}
