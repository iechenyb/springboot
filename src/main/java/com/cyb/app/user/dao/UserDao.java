package com.cyb.app.user.dao;

import org.springframework.stereotype.Repository;
@Repository("userDao")
public class UserDao {
	public void print(String str){
		System.out.println("dao print "+str);
	}
}
