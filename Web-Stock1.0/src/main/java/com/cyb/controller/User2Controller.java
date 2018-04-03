package com.cyb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.aop.ResultBean;
import com.cyb.contants.SystemContants;
import com.cyb.date.DateUtil;
import com.cyb.listener.OnLineBroker;
import com.cyb.log.LogRule;
import com.cyb.po.MyUser;
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
    	onlineUsers.put("在线人数(hash)", SystemContants.onlineUser.keySet().size()+"");
    	onlineUsers.put("在线人员账号", SystemContants.onlineUser.keySet().toString());
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
    	return  new ResultBean().success().data(req.getSession().getAttribute("userid"));
    }
    
    @Autowired
    private  AuthenticationManager authenticationManager ;
    
    @Autowired
	LogRule logRule;
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: json请求参数<br>
     *创建时间: 2017年7月15日hj12
     *@param user
     *@param req
     *@return
     */
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
             SystemContants.onlineUser.put(user.getUsername(), user.getUsername());
             loginLogService.saveLoginLog(user, req);
             logRule.saveSessionLog(DateUtil.timeToMilis()+"	"+req.getSession().getId()+" ["+user.getUsername()+"] 进入系统！");
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             return new ResultBean<Object>().fail("Authentication failed: " +e.getMessage());
         }
		return new ResultBean<Object>().success("登录成功！");
    }
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 普通请求参数<br>
     *创建时间: 2017年7月15日hj12
     *@param user
     *@param req
     *@return
     */
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