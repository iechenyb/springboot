package com.cyb.controller;

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

import com.cyb.dao.MyUserRepository;
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

    @GetMapping("/getUser")
    @ResponseBody
    public MyUser MyUser(String username){
    	return userService.getUserByName(username);
    }
    
    @Autowired
    private  AuthenticationManager authenticationManager ;//= new SampleAuthenticationManager();

    @GetMapping("/toRedirect")  
    public String helloJsp(){  
        System.out.println("HelloController.helloJsp().hello=");  
        return "redirect:/lhmj/login.jsp";
    }  
    
    @PostMapping("/login")
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
    
    @GetMapping("/exit")
    @ResponseBody
    public String exit(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	 try {
    		 Assert.notNull(request, "HttpServletRequest required");
	         HttpSession session = request.getSession(false);
	         if (session != null) {
	              session.invalidate(); //使当前会话失效
	         }
    	     SecurityContextHolder.clearContext(); //清空安全上下文
         } catch (AuthenticationException e) {
             System.out.println("Authentication failed: " + e.getMessage());
             return "退出失败";
         }
    	 System.out.println("用户退出！");
		return "退出成功";
    }
    
    @Autowired
    MyUserRepository myUserRepository;
    
    //默认jpa保存
    @PostMapping("/signup")
    @ResponseBody
    public String signUp(@RequestBody MyUser user) {
    	return userService.saveRegister(user);
    }
    
    @PostMapping("/hiberSave")
    @ResponseBody
    public String hiberSave(@RequestBody MyUser user) {
    	userService.saveMyUser(user);
    	return "success";
    }
}