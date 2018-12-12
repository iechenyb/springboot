package com.kiiik.vas.example.dao;

import org.springframework.stereotype.Repository;

import com.kiiik.vas.base.ibatis.v1.AbstractDaoImpl;
import com.kiiik.vas.example.model.MyUser;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月10日
 */
@Repository
public class UserDaoImpl extends AbstractDaoImpl<MyUser> {
	
	public static void main(String[] args) {
		UserDaoImpl aaa = new UserDaoImpl();
		System.out.println(aaa.getStatement());
		aaa.clear();
	}
}
