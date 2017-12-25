package com.cyb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.po.MyUser;
import com.cyb.service.UserServiceImpl;

/**
 *作者 : iechenyb<br>
 *类描述: 事务管理测试-没有权限控制<br>
 *创建时间: 2017年12月13日
 */
@Controller
@RequestMapping("/sw")
public class TransactionController {
	
    @Autowired
    UserServiceImpl userService;
    
    //http://localhost:8080//sw/testSW?has=1&name=qw
    @GetMapping("/testJdbcSW")
    @ResponseBody
    public String jdbc(int has,String name){
    	userService.updateTX(has,name,1);
    	return "测试结束！";
    }
    
    @GetMapping("/testHibernateSW")
    @ResponseBody
    public String hibernate(int has,String name){
    	userService.updateTX(has,name,2);
    	return "测试结束！";
    }
    
    @GetMapping("/commDao")
    @ResponseBody
    public String hibernate(MyUser user){
    	userService.saveMyUser(user);
    	return "测试结束！";
    }
}