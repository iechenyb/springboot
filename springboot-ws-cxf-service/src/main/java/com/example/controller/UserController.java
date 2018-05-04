package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.MyUser;
import com.example.service.impl.MyUserServiceImpl;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月4日
 */
@RequestMapping("user")
@Controller
public class UserController {
	@Autowired
	MyUserServiceImpl userService;
    //http://localhost:8002/user/get?acc=iechenyb
	@GetMapping("get")
	@ResponseBody
	public MyUser getUser(String acc) {
		return userService.getUser(acc);
	}
	
	@GetMapping("save")
	@ResponseBody
	public MyUser save() {
		MyUser user = new MyUser();
		user.setId(1);
		user.setAccount("sssss");
		user.setPassword("sfsddf");
		userService.save(user);
		return user;
	}
	
	@GetMapping("update")
	@ResponseBody
	public MyUser update(MyUser user) {
		userService.update(user);
		return user;
	}
	
	@GetMapping("delete")
	@ResponseBody
	public String delete(Integer id) {
		userService.delete(id);
		return "删除成功";
	}
}
