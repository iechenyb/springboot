package com.example.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.example.model.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月4日
 */
public interface UserDao {
	Log log = LogFactory.getLog(UserDao.class);
	public MyUser getUserByAccount(String account);
	public void insertMyUser(MyUser user);
	public void updateByPrimaryKey(MyUser user);
	public void deleteByPrimaryKey(Integer id);
}
