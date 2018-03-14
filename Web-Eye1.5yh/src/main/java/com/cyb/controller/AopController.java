package com.cyb.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.aop.ResultBean;

/**
 *作者 : iechenyb<br>
 *类描述: 统一处理异常，dao和service都不再捕获异常
 *，全部提交到前端处理！<br>
 *创建时间: 2017年12月7日
 */
@RequestMapping("aop")
@Controller
public class AopController {
	
	//抛出异常！json测试
	@ResponseBody
	@GetMapping("ex")
	public ResultBean<Integer> ex(){
		System.out.println(1/0);
		return new ResultBean<Integer>(1);
	}
	
	//json测试 正常
	@ResponseBody
	@GetMapping("noex")
	public int infoRoles(){
		return 1;
	}
	//json测试 正常
	@ResponseBody
	@GetMapping("void")
	public void voidm(){
		
	}
	
	@GetMapping("toPage1")
	public ModelAndView toPage2(){
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb2");
		view.setViewName("index");
		System.out.println(1/0);
		return view;
	}
	
	@GetMapping("toPage2")
	public ModelAndView toPage3(String name){
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb3");
		view.setViewName("index");
		return view;
	}
}
