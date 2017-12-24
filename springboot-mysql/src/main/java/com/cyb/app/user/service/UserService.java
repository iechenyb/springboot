package com.cyb.app.user.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.user.dao.UserDao;
import com.cyb.app.user.dao.UserRepository;
import com.cyb.app.user.pojo.User;

@Service
public class UserService {
	  @Resource(name="userDao")
	  UserDao userDao;
	  @Autowired  
	  private UserDao userDao0;
	  @Autowired  
	  private UserRepository userDao1; 
      public void print(String str){
       userDao.print(str);
       userDao0.print(str);
     }
      public User getUserByName(String name){
    	  return userDao1.findUserByName(name);
      }
}
