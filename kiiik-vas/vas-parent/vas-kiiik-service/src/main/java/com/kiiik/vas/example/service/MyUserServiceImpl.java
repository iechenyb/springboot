package com.kiiik.vas.example.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiiik.vas.example.dao.UserDao;
import com.kiiik.vas.example.model.MyUser;
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
	//写法待研究
	public List<MyUser> getUserList(){
		return userDao.getAllUser();
	}
}
