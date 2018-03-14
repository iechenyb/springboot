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
import com.cyb.config.SystemConfigSettings;
import com.cyb.contants.PlanContants;
import com.cyb.dao.MyUserRepository;
import com.cyb.dao.PlanTypeRepository;
import com.cyb.date.DateUtil;
import com.cyb.listener.OnLineBroker;
import com.cyb.log.LogRule;
import com.cyb.po.MyUser;
import com.cyb.po.UserLoginLog;
import com.cyb.service.LoginLogServiceImpl;
import com.cyb.service.UserServiceImpl;
import com.cyb.utils.DESUtil;

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
    	if(req.getSession().getAttribute("count")!=null){
    		AtomicInteger in = (AtomicInteger) req.getSession().getAttribute("count");
    		onlineUsers.put("在线人员数（监听session）",in.get()+"" );
    	}
    	onlineUsers.put("在线人员数（监听常量）",OnLineBroker.counter.get()+"" );
    	return new ResultBean<>().success().data(onlineUsers);
    }
    
    @GetMapping("/getUser")
    @ResponseBody
    public MyUser MyUser(String username){
    	return userService.getUserByName(username);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getUserName")
    @ResponseBody
    public ResultBean<String> getUserName(HttpServletRequest req){
    	return  new ResultBean().success().data(req.getUserPrincipal().getName());
    }
    
    @Autowired
    private  AuthenticationManager authenticationManager ;
    @Autowired
	LogRule logRule;
    @SuppressWarnings("unchecked")
	@PostMapping("/login1")
    @ResponseBody
    public ResultBean<Object> login1(@RequestBody MyUser user,HttpServletRequest req) {
    	 try {
    		 String originPassword = new DESUtil().strDec(user.getPassword(), req.getSession().getAttribute("k1").toString(), req.getSession().getAttribute("k2").toString(), req.getSession().getAttribute("k3").toString());
             Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), originPassword);
             System.out.println("before:" + request);
             Authentication result = authenticationManager.authenticate(request);
             System.out.println("after:" + result);
             SecurityContextHolder.getContext().setAuthentication(result);
             req.getSession().setAttribute("userid", user.getUsername());
             PlanContants.onlineUser.put(user.getUsername(), user.getUsername());
             loginLogService.saveLoginLog(user, req);
             logRule.saveSessionLog(DateUtil.timeToMilis()+"	"+req.getSession().getId()+" ["+user.getUsername()+"] 进入系统！");
             req.getSession().removeAttribute("k1");
             req.getSession().removeAttribute("k2");
             req.getSession().removeAttribute("k3");
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