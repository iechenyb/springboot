package com.kiiik.web.system.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *作者 : iechenyb<br>
 *类描述: 如何考虑分页<br>
 *创建时间: 2018年10月18日
 */
import org.springframework.web.bind.annotation.RestController;

import com.cyb.date.DateUtil;
import com.kiiik.pub.bean.Page;
import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.IdCardGenerator;
import com.kiiik.utils.RandomUtils;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.service.UserServiceImpl;
import com.kiiik.web.system.vo.UserRoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("user")
@Api("用户管理模块")
public class UserController {
	Log log = LogFactory.getLog(UserController.class);
	
	@Autowired
	GenericService genericService;
	
	@SuppressWarnings("unchecked")
	@PostMapping("genUsers")
	@ApiOperation("模拟生成用户信息，仅供测试使用")
	public ResultBean<String> genUsers(@ApiParam("生成用户数量") Integer theNumbersOfToGen){
		User po = null;
		if (theNumbersOfToGen == null) {
			theNumbersOfToGen = 10;
		}
		List<Object> users = new ArrayList<Object>();
		for (int i = 0; i < theNumbersOfToGen; i++) {
			po = new User();
			// po.setId(i); 不设置 采用自增长方式
			po.setUserName(RandomUtils.getChineaseName());
			po.setEmpNo(RandomUtils.getPingYin(po.getUserName()));
			po.setPassword("111111");//RandomUtils.getPassWord(8)
			po.setEmail(RandomUtils.getEmail(8, 10));
			po.setIdCard(IdCardGenerator.generate());
			po.setIsEffect(i%2);
			po.setLastLoginIp(RandomUtils.getIp());
			po.setLoginIp(RandomUtils.getIp());
			po.setOperateID(String.valueOf(RandomUtils.getNum(100,200)));
			po.setLastLoginTime(Long.valueOf(DateUtil.date2long14()));
			po.setPhone(RandomUtils.getTel());
			po.setSex(i%2);
			//genericService.insertDBEntity(po);
			try {
				users.add(po);
			} catch (Exception e) {
				// 忽略生产中文乱码的问题
			}
		}
		genericService.insertDBEntityBatch(users);
		return new ResultBean<String>("生成"+theNumbersOfToGen+"个用户信息！").success();
	}
	
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 前端根据指定额属性进行封装，比如子查询username时，json格式为:
     *{
     * "username":"chenyuanbao"
     *}<br>
     *创建时间: 2017年7月15日hj12
     *@param user
     *@return
     */
	@SuppressWarnings("unchecked")
	@PostMapping("list")
	@ApiOperation("用户列表")
	public ResultBean<List<User>> listUsers(@RequestBody User user){
		List<User> users = genericService.queryDBEntityList(user);
		return new ResultBean<List<User>>(users).success();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("listPage")
	@ApiOperation("用户列表分页查询")
	public ResultBean<List<User>> listUsersPage(User user,Page page){
		List<User> users = genericService.queryDBEntityList(user,page.getPageNum(),page.getPageSize()," id asc");
		return new ResultBean<List<User>>(users).success();
	}
	

	@SuppressWarnings({ "unchecked"})
	@PostMapping("add")
	@ApiOperation("新增用户信息")
	public ResultBean<String> addUser(@RequestBody User user){
		User user_tmp = null;
		user_tmp = genericService.queryDBEntitySingleComplex(User.class, new ComplexCondition()
				.and().col("empno").eq(user.getEmpNo()).or().col("phone").eq(user.getPhone()));
		int count = 0 ;
		if(user_tmp==null){
			count = genericService.insertDBEntity(user);
			return new ResultBean<Integer>(count).fail("用户插入成功!");
		}
		return new ResultBean<Integer>(count).success("用户已经存在！");
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("deleteById")
	@ApiOperation("根据主键删除用户信息")
	public ResultBean<String> delUser(Integer id){
		User user = new User();
		user.setId(id);
		int count = genericService.deleteDBEntityByKey(user);
		return new ResultBean<String>("删除"+count+"记录！").success();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("update")
	@ApiOperation("更新用户信息")
	public ResultBean<String> updUser(@RequestBody User user){
		User user_tmp = null;
		user_tmp = genericService.queryDBEntitySingleComplex(User.class, 
				new ComplexCondition().col("id").notIn(user.getId()).and(new ComplexCondition()
				.col("empno").eq(user.getEmpNo()).or().col("phone").eq(user.getPhone())));
		if(user_tmp!=null){
			return new ResultBean<String>().success("修改的用户名已经存在！");
		}else{
			int count = genericService.updateDBEntityByKey(user);
			return new ResultBean<String>().success("更新成功！更新记录数"+count);
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("getUserRoles")
	@ApiOperation("获取用户的角色信息")
	public ResultBean<List<UserRoleVo>> getUserRole(String userId){
		return new ResultBean<List<UserRoleVo>>(userService.getUserRoles(userId)).success();
	}
	
	@Autowired
	UserServiceImpl userService;
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 全覆盖式存储，不用判断有无重复记录<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleIds
	 *@param userId
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("saveUserRole")
	@ApiOperation("保存用户角色")
	public ResultBean<String> saveUserRole(@RequestParam(value = "roleIds[]") String[] roleIds,@RequestParam(value = "userId") String userId){
		if(roleIds.length==0||userId==null){
			return new ResultBean<String>().success("角色或者用户信息不能为空！");
		}
		int total = roleIds.length;
		if(total>0){
			userService.saveUserRoles(roleIds, userId);
		}
		return new ResultBean<String>().success("用户角色信息保存成功！！");
	}
	
}
