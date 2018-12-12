package com.kiiik.zuul.web.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.zuul.utils.RequestUtils;
import com.kiiik.zuul.web.log.bean.SystemLog;
import com.kiiik.zuul.web.log.repository.SystemLogRepository;


/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月3日上午9:28:39
 */
@Controller
@RequestMapping("user")
public class UserController {
	Log log = LogFactory.getLog(UserController.class);
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ModelAndView login(String username, String password, HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
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
			System.out.println(e);
			view.addObject("errmsg", "登录失败");
			if(e instanceof BadCredentialsException){
				view.addObject("errmsg", "用户名或者密码错误！");
			}
			view.setViewName("/user/login_page");
			return view;
		}
		view.setViewName("/index");
		try{
			SystemLog log = RequestUtils.getSystemLog(req);
			log.setOperator(username);
			logRes.save(log);
		}catch(Exception e){}
		// 直接返回首页
		return view;
	}

	@Autowired
	SystemLogRepository logRes;
	
	@GetMapping("/toLogin")
	public ModelAndView toLogin() {
		log.info("正在跳转到登录页面！");
		ModelAndView view = new ModelAndView();
		view.setViewName("user/login_page");
		return view;
	}

	@ResponseBody
	@GetMapping("infor")
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

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SecurityContextHolder.clearContext();
		session.removeAttribute("SPRING_SECURITY_CONTEXT");
		session.invalidate();
		// 直接返回首页
		return "/user/login_page";
	}
}
