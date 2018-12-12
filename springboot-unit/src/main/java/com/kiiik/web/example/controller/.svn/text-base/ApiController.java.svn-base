package com.kiiik.web.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:03:14
 */
@RestController
@RequestMapping("api")
public class ApiController {
	
	Log log = LogFactory.getLog(ApiController.class);
	
	@GetMapping("query")
	@ResponseBody
	public ResultBean<String> getP(String username) {
		System.out.println("查看权限和角色");
		return new ResultBean<String>().success("信息列表!");
	}

	@ResponseBody
	@PostMapping("add")
	public ResultBean<String> add(CalDate date) {
		return new ResultBean<String>().success("添加信息成功！");
	}

	@ResponseBody
	@DeleteMapping("delete")
	public ResultBean<String> delete() {
		return new ResultBean<String>().success("删除信息成功");
	}

	@ResponseBody
	@PutMapping("update") 
	public ResultBean<String> update(CalDate date) {
		return new ResultBean<String>(JSON.toJSONString(date)).success("更新信息成功！");
	}
	
	@ResponseBody
	@GetMapping("free") 
	public ResultBean<String> free(String name) {
		return new ResultBean<String>().success("free！");
	}
}
