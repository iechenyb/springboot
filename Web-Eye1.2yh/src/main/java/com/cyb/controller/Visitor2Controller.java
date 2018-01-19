package com.cyb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *作者 : iechenyb<br>
 *类描述: 不使用注解控制权限， 使用配置文件控制权限<br>
 *创建时间: 2017年12月13日
 */
@Controller
@RequestMapping("visitor2")
public class Visitor2Controller  {

	
	@GetMapping("/home")
	@ResponseBody
	public String home() {
		return "home";
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	@GetMapping("/user")
	@ResponseBody
	public String user() {
		return "user";
	}
}
