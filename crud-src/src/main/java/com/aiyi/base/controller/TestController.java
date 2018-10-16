package com.aiyi.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiyi.base.pojo.TestUserPo;
import com.aiyi.base.service.TestService;

@Controller
public class TestController {

	@Resource
	private TestService testService;
	
	/**
	 * 添加测试
	 * @return
	 */
	@RequestMapping("testAdd")
	@ResponseBody
	public Object testAddList(){
		
		long t = System.currentTimeMillis();
		int n = testService.addList();
		long tT = System.currentTimeMillis();
		
		double s = (tT - t) / 1000;
		
		return "共添加" + n + "条数据，用时" + s + "秒";
	}
	
	/**
	 * 查询测试
	 * @return
	 */
	@RequestMapping("testSelect")
	@ResponseBody
	public Object testSelectFmt(){
		
		long t = System.currentTimeMillis();
		List<TestUserPo> listUser = testService.listUser();
		long tT = System.currentTimeMillis();
		
		double s = (tT - t) / 1000;
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("msg", "共查询" + listUser.size() + "条记录,用时" + s + "秒");
		map.put("oData", listUser);
		
		return map;
		
	}
}
