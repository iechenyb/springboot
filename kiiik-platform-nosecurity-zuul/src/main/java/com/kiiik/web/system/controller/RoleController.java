package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.Role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("role")
@Api(value = "角色管理模块", description = "角色基本信息操作API", tags = "RoleApi")
public class RoleController {
	Log log = LogFactory.getLog(RoleController.class);
	
	@Autowired
	GenericService genericService;
	
	
    /**
     * 
     *作者 : iechenyb<br>
     *<br>
     *创建时间: 2017年7月15日
     *@param user
     *@return
     */
	@SuppressWarnings("unchecked")
	@PostMapping("list")
	@ApiOperation("角色列表信息")
	public ResultBean<List<Role>> listRoles(@RequestBody Role role){
		List<Role> roles = genericService.queryDBEntityList(role);
		return new ResultBean<List<Role>>(roles).success();
	}
	

	@SuppressWarnings({ "unchecked"})
	@PostMapping("add")
	@ApiOperation("新增角色信息")
	public ResultBean<String> addRole(@RequestBody Role role){
		Role role_tmp = null;
		role_tmp = genericService.queryDBEntitySingleComplex(Role.class, 
				new ComplexCondition()
				.and()
				.col("rolename")
				.eq(role.getRoleName()));
		int count = 0 ;
		if(role_tmp==null){
			count = genericService.insertDBEntity(role);
			return new ResultBean<Integer>(count).success("角色插入成功!");
		}
		return new ResultBean<Integer>(count).fail("角色名称已经存在！");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("deleteByIds")
	@ApiOperation("根据主键删除角色信息")
	public ResultBean<String> delRole(@RequestParam("ids") List<Integer> ids){
		int count = genericService.deleteDBEntityByKeyBatchs(new Role(),ids);
		return new ResultBean<String>("删除"+count+"记录！").success();
		
	}

	@SuppressWarnings("unchecked")
	@PostMapping("update")
	@ApiOperation("更新角色信息")
	public ResultBean<String> updRole(@RequestBody Role role){
		Role role_tmp = null;
		role_tmp = genericService.queryDBEntitySingleComplex(Role.class, 
				new ComplexCondition().col("id").notIn(role.getId()).and().col("rolename").eq(role.getRoleName()));
		if(role_tmp!=null){
			return new ResultBean<String>().fail("角色名已经存在！");
		}else{
			int count = genericService.updateDBEntityByKey(role);
			return new ResultBean<String>().success("更新成功！更新记录数"+count);
		}
	}
}
