package com.kiiik.vas.example.dao;
import java.util.List;

import com.kiiik.vas.example.model.MyUser;

/**
 *作者 : iechenyb<br>
 *类描述: 将sql写到配置文件中<br>
 *创建时间: 2018年5月4日
 */
public interface UserDao {
	public MyUser getUserByAccount(String account);
	public void insertMyUser(MyUser user);
	public void updateByPrimaryKey(MyUser user);
	public void deleteByPrimaryKey(Integer id);
	public List<MyUser> getAllUser();
}
