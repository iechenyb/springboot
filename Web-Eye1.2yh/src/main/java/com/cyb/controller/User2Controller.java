package com.cyb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.aop.ResultBean;
import com.cyb.contants.PlanContants;
import com.cyb.dao.MyUserRepository;
import com.cyb.dao.PlanTypeRepository;
import com.cyb.date.DateUtil;
import com.cyb.po.MyUser;
import com.cyb.po.UserLoginLog;
import com.cyb.service.LoginLogServiceImpl;
import com.cyb.service.UserServiceImpl;

/**
 *作者 : iechenyb<br>
 *类描述: 用户管理测试<br>
 *创建时间: 2017年12月13日
 */
@Controller
@RequestMapping("/users2")
public class User2Controller {
    
    @Autowired
    UserServiceImpl userService;
    
    @Autowired
    LoginLogServiceImpl loginLogService;
    
    @SuppressWarnings("unchecked")
	@GetMapping("/onlineInformation")
    @ResponseBody
    public ResultBean<Object> MyUser(HttpServletRequest req){
    	Map<String,String> onlineUsers = new HashMap<>();
    	onlineUsers.put("在线人数(hash)", PlanContants.onlineUser.keySet().size()+"");
    	onlineUsers.put("在线人员账号", PlanContants.onlineUser.keySet().toString());
    	if(req.getAttribute("count")!=null){
    		AtomicInteger in = (AtomicInteger) req.getAttribute("count");
    		onlineUsers.put("在线人员数（监听）",in.get()+"" );
    	}
    	return new ResultBean<>().success().data(onlineUsers);
    }
    
    @GetMapping("/getUser")
    @ResponseBody
    public MyUser MyUser(String username){
    	return userService.getUserByName(username);
    }
    
    @Autowired
    private  AuthenticationManager authenticationManager ;
    
    @SuppressWarnings("unchecked")
	@PostMapping("/login1")
    @ResponseBody
    public ResultBean<Object> login1(@RequestBody MyUser user,HttpServletRequest req) {
    	 try {
             Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
             System.out.println("before:" + request);
             Authentication result = authenticationManager.authenticate(request);
             System.out.println("after:" + result);
             SecurityContextHolder.getContext().setAuthentication(result);
             req.getSession().setAttribute("userid", user.getUsername());
             PlanContants.onlineUser.put(user.getUsername(), user.getUsername());
             loginLogService.saveLoginLog(user, req);
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             return new ResultBean<Object>().fail("Authentication failed: " +e.getMessage());
         }
		return new ResultBean<Object>().success("登录成功！");
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/login2")
    @ResponseBody
    public ResultBean<Object> login2(MyUser user,HttpServletRequest req) {
    	 try {
             Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
             System.out.println("before:" + request);
             Authentication result = authenticationManager.authenticate(request);
             System.out.println("after:" + result);
             SecurityContextHolder.getContext().setAuthentication(result);
             req.getSession().setAttribute("userid", user.getUsername());
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             return new ResultBean<Object>().fail("Authentication failed: " +e.getMessage());
         }
		return new ResultBean<Object>().success("登录成功！");
    }
}