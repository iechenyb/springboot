package com.cyb.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.po.NetResult;
import com.cyb.service.NetServiceImpl;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月15日
 */
@RestController
@RequestMapping("net")
public class NetController {
	Log log = LogFactory.getLog(NetController.class);
	@Autowired
	NetServiceImpl netService;

	@GetMapping("/testUrl")
	@ResponseBody
	public String jdbc() {
		netService.saveUrlTest("");
		return "测试结束！";
	}
	
	@GetMapping("/getList")
	@ResponseBody
	public List<NetResult> getList() {
		return netService.getList();
	}

}
