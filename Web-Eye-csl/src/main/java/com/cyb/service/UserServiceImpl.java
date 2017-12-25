package com.cyb.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.dao.UserDaoImpl;
import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Service
public class UserServiceImpl {
	
	@Autowired
	UserDaoImpl userDao;
	
	public void updateTX(int hasException,String newName,int type){
		userDao.updateTx(hasException,newName,type);
	}
	
	public MyUser getUserByName(String userName){
		return userDao.getUserByName(userName);
	}
	
	public String saveRegister (MyUser user){
		return userDao.saveRegister(user);
	}
	
	public void saveMyUser(MyUser user){
		userDao.saveMyUser(user);
	}
}
