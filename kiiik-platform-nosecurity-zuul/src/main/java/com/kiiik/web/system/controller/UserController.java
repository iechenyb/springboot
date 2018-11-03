package com.kiiik.web.system.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *作者 : iechenyb<br>
 *类描述: 如何考虑分页<br>
 *创建时间: 2018年10月18日
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.date.DateUtil;
import com.kiiik.pub.bean.Page;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.bean.SessionUser;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.RandomUtils;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.service.UserServiceImpl;
import com.kiiik.web.system.vo.PasswordVo;
import com.kiiik.web.system.vo.UserRoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("user")
@Api("用户管理模块")
public class UserController extends BaseController{
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
			po.setIsEffect(i%2);
			po.setLastLoginIp(RandomUtils.getIp());
			po.setLoginIp(RandomUtils.getIp());
			po.setLastLoginTime(Long.valueOf(DateUtil.date2long14()));
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
     *创建时间: 2017年7月15日
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
				.and().col("empno").eq(user.getEmpNo()));
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
				.col("empno").eq(user.getEmpNo())));
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
	public ResultBean<List<UserRoleVo>> getUserRole(Integer userId){
		return new ResultBean<List<UserRoleVo>>(userService.getUserRoles(userId)).success();
	}
	
	@Autowired
	UserServiceImpl userService;
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 全覆盖式存储，不用判断有无重复记录<br>
	 *创建时间: 2017年7月15日
	 *@param roleIds
	 *@param userId
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("saveUserRole")
	@ApiOperation("保存用户角色")
	public ResultBean<String> saveUserRole(@RequestParam(value = "roleIds[]") Integer[] roleIds,@RequestParam(value = "userId") Integer userId){
		if(roleIds.length==0||userId==null){
			return new ResultBean<String>().success("角色或者用户信息不能为空！");
		}
		int total = roleIds.length;
		if(total>0){
			userService.saveUserRoles(roleIds, userId);
		}
		return new ResultBean<String>().success("用户角色信息保存成功！！");
	}
	
	
	
	//-------------------------------------用户登录相关功能-------------------------------------
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation("用户登陆，ajax请求用")
	@ResponseBody
	public ResultBean<String> loginJson(String username, String password, HttpServletRequest req) {
		try{
			if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
				return new ResultBean<String>().fail("用户名或者密码不能为空！");
			}
			Authentication request = new UsernamePasswordAuthenticationToken(username, password);
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			req.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		}catch(Exception e){
			e.printStackTrace();
			if(e instanceof BadCredentialsException){
				return new ResultBean<String>().fail("用户名或者密码错误！");
			}
			return new ResultBean<String>().fail("登录失败！");
		}
		return new ResultBean<String>().success("登录成功！");
	}
	
	@PostMapping(value ="/loginPage", produces = {"text/html"})
	@ApiOperation("用户登陆，页面跳转用")
	public ModelAndView login(String username, String password, HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		System.out.println("username:"+username+",password:"+password);
		try{
			if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
				view.addObject("errmsg", "用户名或者密码不能为空！");
				view.setViewName("/user/login_page");
				return view;
			}
			Authentication request = new UsernamePasswordAuthenticationToken(username, password);
			Authentication result = authenticationManager.authenticate(request);
			System.out.println("result:"+result);
			SecurityContextHolder.getContext().setAuthentication(result);
			req.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		}catch(Exception e){
			e.printStackTrace();
			view.addObject("errmsg", "登录失败");
			if(e instanceof BadCredentialsException){
				view.addObject("errmsg", "用户名或者密码错误！");
			}
			view.setViewName("/user/login_page");
			return view;
		}
		view.setViewName("/index");
		// 直接返回首页
		return view;
	}

	
	@GetMapping("/toLoginPage")
	@ApiOperation("会话失效，跳转请求！")
	public ModelAndView toLoginPage() {
		log.info("正在跳转到登录页面！");
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/login_page");
		return view;
	}
	
	@GetMapping("/toLogin")
	@ResponseBody
	public ResultBean<String> toLogin() {
		log.info("正在跳转到登录页面！");
		return new ResultBean<String>().sessionTimeOut("会话超时，请重新登陆！");
	}

	@ResponseBody
	@GetMapping("infor")
	@ApiOperation("用户信息")
	public String getInfor(HttpServletRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("用户名：" + auth.getName());
		Object[] roles = auth.getAuthorities().toArray();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < roles.length; i++) {
			sb.append(roles[i] + ",");
		}
		return "用户名： " + auth.getName() + ",角色信息：" + sb.toString();
	}

	@GetMapping("/logoutPage")
	@ApiOperation("用户登出,页面跳转用")
	public String logoutPage(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SecurityContextHolder.clearContext();
		session.removeAttribute("SPRING_SECURITY_CONTEXT");
		session.invalidate();
		// 直接返回首页
		return "/user/login_page";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/logout")
	@ApiOperation("用户登出,ajax请求")
	public ResultBean<String> logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SecurityContextHolder.clearContext();
		session.removeAttribute("SPRING_SECURITY_CONTEXT");
		session.invalidate();
		// 直接返回首页
		return new ResultBean<String>().success("登出成功！");
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/updatePassword")
	@ResponseBody
	@ApiOperation("用户密码修改")
	public ResultBean<String> updatePassword(@RequestBody @Validated  PasswordVo passvo ) {
		if(passvo.getNewPassword().equals(passvo.getOldPassword())){
			return new ResultBean<String>().fail("密码相同，无需更新！");
		}
		SessionUser su = this.getSessionUser();
		User user = new User();
		user.setEmpNo(su.getEmpNo());
		user.setPassword(passvo.getOldPassword());
		User dbUser = genericService.queryDBEntitySingle(user);
		if(dbUser!=null){
			user.setId(dbUser.getId());
			user.setPassword(passvo.getNewPassword());
			genericService.updateDBEntityByKey(user);
		}else{
			return new ResultBean<String>().fail("旧密码不正确！");
		}
		// 直接返回首页
		return new ResultBean<String>().success("密码更新成功！");
	}
	
}

