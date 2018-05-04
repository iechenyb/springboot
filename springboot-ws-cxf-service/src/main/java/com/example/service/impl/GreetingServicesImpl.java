package com.example.service.impl;

import javax.jws.WebService;

import com.example.service.GreetingServices;
import com.example.service.User;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月3日
 * http://localhost:8002/services/GreetingServices?wsdl
 */
@WebService(serviceName = "GreetingServices"// 服务名
, targetNamespace = "http://service.example.com"// http://service.netbar.temple.xiaojf.cn报名倒叙，并且和接口定义保持一致
, endpointInterface = "com.example.service.GreetingServices") // 包名
//@Component
public class GreetingServicesImpl implements GreetingServices {
	public GreetingServicesImpl(){
		
	}
	public String sayHello(String name) {
		return "hello , " + name;
	}

	public User queryUser(String name) {
		User user = new User();
		user.setUserName(name);
		user.setUserAge(20);
		return user;
	}
}
