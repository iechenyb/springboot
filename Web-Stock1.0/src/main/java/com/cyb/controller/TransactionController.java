package com.cyb.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.date.DateUtil;
import com.cyb.po.Flight;
import com.cyb.po.Message;
import com.cyb.po.MyUser;
import com.cyb.service.TransactionSeviceImpl;
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
    public String jdbc(int hasException,String name,Long id){
    	userService.updateTX(hasException,name,id,1);
    	return "测试结束！";
    }
    
    @GetMapping("/testHibernateSW")
    @ResponseBody
    public String hibernate(int hasException,String name,Long id){
    	userService.updateTX(hasException,name,id,2);
    	return "测试结束！";
    }
    
    @GetMapping("/testJpaSave")
    @ResponseBody
    public String jpaTra(int type,MyUser user){
    	if(type==1){
    	   userService.saveJap1(user);
        }else{
    	   userService.saveJap2(user);
       }
    	String method="entityManager.persist";
    	if(type==2){
    		method="myUserRep(jpa).save";
    	}
    	return method+" 测试结束！";
    }
    
    @GetMapping("/testHiberSave")
    @ResponseBody
    public String hibernate(MyUser user){
    	userService.saveMyUser(user);
    	return "测试结束！";
    }
    
	//jpa事务测试
	@GetMapping("testJpaUpdate")
	@ResponseBody
	public String jpaUpdateTest(Long id,String username){
		userService.update(username,id);//jpa sql更新(测试用例不可以测试，报session创建失败)
		return "success";
	}
	
	@Autowired
	TransactionSeviceImpl service;
	
	@GetMapping("saveFlight")
	@ResponseBody
	public String saveFlight(){
		System.out.println("=============1================");
		Flight f = new Flight();
		//f.setId(1L); 自动设置id
		f.setName("009航班");
		f.setTime(DateUtil.descTimeToSec());
		service.saveFlight(f);
		System.out.println("=============2================");
		return "success";
	}
	
	@GetMapping("saveMessage")
	@ResponseBody
	public String saveMessage(){
		System.out.println("=============1================");
		Flight f = new Flight();
		f.setName("009 flight");
		f.setTime(DateUtil.descTimeToSec());
		
		Message m = new Message();
		m.setMsg("009 ordered！");
		m.setFid(f.getId());
		m.setTime(DateUtil.descTimeToSec());
		service.saveMessage(m);
		System.out.println("=============2================");
		return "success";
	}
	
	@GetMapping("oneKeyOrderFlightProxy")
	@ResponseBody
	public String  saveInOneMethodProxy(int hasException ){
		Flight f = new Flight();
		f.setName("009 flight");
		f.setTime(DateUtil.descTimeToSec());
		try {
			service.saveInOneMethod(f,hasException);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "success";
	}
	
	@GetMapping("oneKeyOrderFlight")
	@ResponseBody
	public String  saveInOneMethod(int hasException ){
		Flight f = new Flight();
		f.setName("009 flight");
		f.setTime(DateUtil.descTimeToSec());
		try {
			service.saveInOneMethodCommon1(f,hasException);
			System.out.println("threadname="+Thread.currentThread().getName());
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "success";
	}
	@GetMapping("showThreadName")
	@ResponseBody
	public String showThreadName(){
		System.out.println("threadname="+Thread.currentThread().getName());
		return "success";
	}
}