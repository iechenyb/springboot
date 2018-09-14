package com.kiiik.vas.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.utils.RequestUtils;

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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/login")
	 * 
	 * @ResponseBody public ResultBean<Object> login(String username,String
	 * password,HttpServletRequest req) { Authentication request = new
	 * UsernamePasswordAuthenticationToken(username, password);
	 * System.out.println("before:" + request); Authentication result =
	 * authenticationManager.authenticate(request); System.out.println("after:"
	 * + result); SecurityContextHolder.getContext().setAuthentication(result);
	 * req.getSession().setAttribute("userid", username); return new
	 * ResultBean<Object>().success("登录成功！"); }
	 */
	@PostMapping("/login")
	public ModelAndView login(String username, String password, HttpServletRequest req) {
		Authentication request = new UsernamePasswordAuthenticationToken(username, password);
		System.out.println("before:" + request);
		Authentication result = authenticationManager.authenticate(request);
		System.out.println("after:" + result);
		SecurityContextHolder.getContext().setAuthentication(result);
		req.getSession().setAttribute("userid", username);
		HttpSession session = req.getSession();
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		ModelAndView view = new ModelAndView();
		view.setViewName("/index");
		view.addObject("basePath", RequestUtils.getProjectAbsPath(req));
		// 直接返回首页
		return view;
	}

	/*
	 * @GetMapping("/toLogin") public String toLogin() { log.info("正在跳转到登录页面！");
	 * return "/user/login_page"; }
	 */

	@GetMapping("/toLogin")
	public ModelAndView toLogin() {
		log.info("正在跳转到登录页面！");
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/login_page");
		view.addObject("author", "chenyuanbao");
		return view;
	}

	@SuppressWarnings("unchecked")
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

		SecurityContextImpl securityContextImpl = (SecurityContextImpl) req.getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT");
		// 登录名
		System.out.println("Username:" + securityContextImpl.getAuthentication().getName());
		// 登录密码，未加密的
		System.out.println("Credentials:" + securityContextImpl.getAuthentication().getCredentials());
		WebAuthenticationDetails details = (WebAuthenticationDetails) securityContextImpl.getAuthentication()
				.getDetails();
		// 获得访问地址
		System.out.println("RemoteAddress" + details.getRemoteAddress());
		// 获得sessionid
		System.out.println("SessionId" + details.getSessionId());
		// 获得当前用户所拥有的权限
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl.getAuthentication()
				.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			System.out.println("Authority" + grantedAuthority.getAuthority());
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
