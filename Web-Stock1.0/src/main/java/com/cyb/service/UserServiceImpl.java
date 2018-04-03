package com.cyb.service;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.dao.MyUserRepository;
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
	
	@Autowired
	MyUserRepository myUserRep;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void updateTX(int hasException,String newName,Long id,int type){
		userDao.updateTx(hasException,newName,id,type);
	}
	
	public MyUser getUserByName(String userName){
		return userDao.getUserByName(userName);
	}
	
	public String saveRegister (MyUser user){
		return userDao.saveRegister(user);
	}
	
	public void saveJap2 (MyUser user){
		 myUserRep.save(user);
	}
	public void saveJap1 (MyUser user){
		 entityManager.persist(user);
	}
	public void saveMyUser(MyUser user){
		userDao.saveMyUser(user);
	}
	
	public void jyUser(String userName,String zt){
		userDao.jyUser(userName,zt);
	}
	public void jyUser2(String userName,String zt){
		userDao.jyUser2(userName,zt);
	}
	public List<Map<String,Object>>  getUserList() {
		return userDao.getUserList();
	}
	public List<Map<String,Object>>  getUserLoginInfor() {
		return userDao.getUserLoginInfor();
	}
	@Transactional
	public void update(String name,Long id){
		myUserRep.updateUserName(name, id);
		System.out.println(myUserRep.getOne(id).getUsername());
	}
}
