package com.cyb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *作者 : iechenyb<br>
 *类描述: 使用注解控制权限<br>
 *创建时间: 2017年12月13日
 */
@Controller
@RequestMapping("visitor")
public class VisitorController  {

	
	// user or admin can visit
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/home")
	@ResponseBody
	public String home() {
		return "home";
	}

	// admin can visit
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	// user can visit
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user")
	@ResponseBody
	public String user() {
		return "user";
	}
}
