package com.example.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.config.DS;
import com.example.dao.UserDao;
import com.example.model.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月4日
 */
@Service
public class MyUserServiceImpl {
	Log log = LogFactory.getLog(MyUserServiceImpl.class);
	
	@Autowired
	UserDao userDao;
	
	@DS("ds1")
	public MyUser getUser(String account){
		return userDao.getUserByAccount(account);
	}
	public void save(MyUser user){
		userDao.insertMyUser(user);
	}
	public void update(MyUser user){
		userDao.updateByPrimaryKey(user);
	}
	public void delete(Integer id){
		userDao.deleteByPrimaryKey(id);
	}
}
