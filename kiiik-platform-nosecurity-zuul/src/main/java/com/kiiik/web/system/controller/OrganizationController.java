package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.system.service.impl.OrganizationServiceImpl;
import com.kiiik.web.system.utils.TreeNode;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月7日
 */
@RestController
@RequestMapping("org")
public class OrganizationController {
	Log log = LogFactory.getLog(OrganizationController.class);
	@Autowired
	OrganizationServiceImpl orgService;
	
	@Autowired
	GenericService genericService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation("组织结构树")
	@GetMapping("tree")
	public ResultBean<TreeNode> orgTree(){
		return new ResultBean<TreeNode>(orgService.getOranizationTree()).success();
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation("查询员工信息")
	@PostMapping("findEmp")
	public ResultBean<List<EmployeeEntity>> listEmp(@RequestBody EmployeeEntity emp){
		if(!StringUtils.isEmpty(emp.getPassword())){
			emp.setPassword(null);//密码查询禁用
		}
		return new ResultBean<List<EmployeeEntity>>(genericService.queryDBEntityList(emp)).success();
	}
	
}
