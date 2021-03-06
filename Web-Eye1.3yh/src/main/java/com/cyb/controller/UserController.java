package com.cyb.controller;

import java.util.HashMap;
import java.util.List;
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
import com.cyb.po.MyUser;
import com.cyb.service.UserServiceImpl;

/**
 *作者 : iechenyb<br>
 *类描述: 用户管理测试<br>
 *创建时间: 2017年12月13日
 */
@Controller
@RequestMapping("/users")
public class UserController {

    
    @Autowired
    UserServiceImpl userService;
    

    public UserController(AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
    }
    
    @GetMapping("/getUserLoginInfor")
    @ResponseBody
    public List<Map<String,Object>> getUserLoginInfor(){
    	List<Map<String,Object>>  logs= userService.getUserLoginInfor();
    	return logs;
    }
    
    @GetMapping("/getAllUser")
    @ResponseBody
    public List<Map<String,Object>> getAllUser(){
    	List<Map<String,Object>>  users= userService.getUserList();
    	return users;
    }
    @GetMapping("/getUser")
    @ResponseBody
    public MyUser MyUser(String username){
    	commonMethod();
    	MyUser user = userService.getUserByName(username);
    	if(user==null){
    		throw new NullPointerException("查询的用户信息是空！");
    	}
    	user.setPassword("**************");
    	return user;
    }
    
    public void commonMethod(){
    	System.out.println("普通方法执行！");
    }
    
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
    
    @Autowired
    private  AuthenticationManager authenticationManager ;//= new SampleAuthenticationManager();

    @GetMapping("/toRedirect")  
    public String helloJsp(){  
        System.out.println("HelloController.helloJsp().hello=");  
        return "redirect:/lhmj/login.jsp";
    }  
    
    @PostMapping("/login1")
    @ResponseBody
    public String login(@RequestBody MyUser user) {
    	 try {
             Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
             System.out.println("before:" + request);
             Authentication result = authenticationManager.authenticate(request);
             System.out.println("after:" + result);
             SecurityContextHolder.getContext().setAuthentication(result);
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             return "登录失败";
         }
		return "登录成功";
    }
    @Autowired
	PlanTypeRepository planTypeRep;
    @PostMapping("/login")
    public ModelAndView login2(MyUser user,HttpServletRequest req) {
    	  ModelAndView view = new ModelAndView();
    	 try {
             Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
             System.out.println("before:" + request);
             Authentication result = authenticationManager.authenticate(request);
             System.out.println("after:" + result);
             SecurityContextHolder.getContext().setAuthentication(result);
             view.setViewName("phone/plan/index");
             view.addObject("msg", "登录成功");
         	 view.addObject("t1",planTypeRep.findPlanJhlx("cq"));//大类1
    		 view.addObject("t2",planTypeRep.findPlanJhlx("pk10"));//大类2
    		 view.addObject("username",user.getUsername());
    		 req.getSession().setAttribute("userid", user.getUsername());
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             view.setViewName("phone/plan/login");
             view.addObject("msg", "登录失败("+e.getMessage()+")");
         }
		return view;
    }
    @SuppressWarnings("unchecked")
	@GetMapping("/exit")
    @ResponseBody
    public ResultBean<Object> exit(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	 try {
    		 Assert.notNull(request, "HttpServletRequest required");
	         HttpSession session = request.getSession(false);
	         if (session != null) {
	              session.invalidate(); //使当前会话失效
	         }
    	     SecurityContextHolder.clearContext(); //清空安全上下文
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             return new ResultBean<Object>().fail("退出失败");
         }
    	 System.out.println("用户退出！");
		return new ResultBean<Object>("退出成功");
    }
    
    @GetMapping("/logout")
    @ResponseBody
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	ModelAndView view = new ModelAndView(); 
    	view.setViewName("phone/plan2/login");
    	try {
    		 Assert.notNull(request, "HttpServletRequest required");
	         HttpSession session = request.getSession(false);
	         if (session != null) {
	              session.invalidate(); //使当前会话失效
	         }
    	     SecurityContextHolder.clearContext(); //清空安全上下文
             view.addObject("msg", "退出成功");
             request.removeAttribute("userid");
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             view.addObject("msg", "退出失败");
             return view;
         }
    	 System.out.println("用户退出！");
		return view;
    }
    
    @Autowired
    MyUserRepository myUserRepository;
    
    //默认jpa保存
    @PostMapping("/signup")
    @ResponseBody
    public String signUp(@RequestBody MyUser user) {
    	return userService.saveRegister(user);
    }
    
   /* //默认jpa保存
    @GetMapping("/register")
    @ResponseBody
    public String register( MyUser user) {
    	return userService.saveRegister(user);
    }*/
    
    @PostMapping("/hiberSave")
    @ResponseBody
    public String hiberSave(@RequestBody MyUser user) {
    	userService.saveMyUser(user);
    	return "success";
    }
    @GetMapping("/tick")
    @ResponseBody
    public String jpaUpdate(String username,String zt) {
    	userService.jyUser(username,zt);
    	return "success";
    }
    @GetMapping("/tick2")
    @ResponseBody
    public String hiberUpdate(String username,String zt) {
    	userService.jyUser2(username,zt);
    	return "success";
    }
}