package com.cyb.app.sk.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.sk.dao.UserRepository;
import com.cyb.app.sk.po.User;
import com.cyb.app.sk.service.UserService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月26日
 */
@Service
public class UserServiceImpl implements UserService{
	Log log = LogFactory.getLog(UserServiceImpl.class);
    @Autowired
    UserRepository userDao;
	public User queryUserById(String token) {
		return userDao.findUserByToken(token);
	}
}
