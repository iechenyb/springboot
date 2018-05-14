package com.kiiik.vas.example.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiiik.vas.example.dao.UserDao;
import com.kiiik.vas.example.dao.UserMapper;
import com.kiiik.vas.example.model.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月4日
 */
@Service
public class MyUserServiceImpl2 {
	Log log = LogFactory.getLog(MyUserServiceImpl2.class);
	
	@Autowired
	UserMapper userDao;
	
	public MyUser getUser(String account){
		return userDao.findMyUsersByName(account);
	}
	public void save(MyUser user){
		userDao.insert(user);
	}
	public void update(MyUser user){
		userDao.updateMyUser(user);
	}
	public void delete(Integer id){
		userDao.deleteMyUserById(id);
	}
	//写法待研究
	public List<MyUser> getUserList(){
		return userDao.getAllMyUsers();
	}
}
