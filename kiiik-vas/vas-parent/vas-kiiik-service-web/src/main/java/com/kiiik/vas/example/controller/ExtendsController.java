package com.kiiik.vas.example.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.vas.example.dao.DemoDaoSuper;
import com.kiiik.vas.example.model.RQ;
import com.kiiik.vas.example.model.User;
/**
 *作者 : iechenyb<br>
 *类描述: 没有必要这样写，注解均没有被继承！！！<br>
 *创建时间: 2018年5月16日
 */
@RestController
public class ExtendsController implements DemoDaoSuper {
	Log log = LogFactory.getLog(ExtendsController.class);

	@Override
	public Object paraList(List<User> user) {
		return null;
	}

	@Override
	public User addUser(User user) {
		return null;
	}

	@Override
	public List<String> addUser(List<String> arrStr) {
		return null;
	}

	@Override
	public ResultBean<RQ> addUserPost(RQ rq) {
		return null;
	}

	@Override
	public ResultBean<RQ> addUserGet(RQ rq) {
		return null;
	}
}
