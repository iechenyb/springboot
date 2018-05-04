package com.example.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.example.service.User;
import com.example.service.UserServices;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月3日
 * http://localhost:8002/services/GreetingServices?wsdl
 */
@WebService(serviceName = "UserServices"// 服务名
, targetNamespace = "http://service.example.com"// http://service.netbar.temple.xiaojf.cn报名倒叙，并且和接口定义保持一致
, endpointInterface = "com.example.service.UserServices") // 包名
//@Component
public class UserServicesImpl implements UserServices {
	public UserServicesImpl(){
		
	}
	public String sayHello(String name) {
		return "hello , " + name;
	}

	public User queryUser(String name) {
		User user = new User();
		user.setUserName(name);
		user.setUserAge(20);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("int", 12);
		map.put("string", "chenyuanbao");
		//map.put("user", user);
		user.setMapData(map);
		List<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add("string");
		//list.add(user);
		user.setListData(list);
		return user;
	}
}
