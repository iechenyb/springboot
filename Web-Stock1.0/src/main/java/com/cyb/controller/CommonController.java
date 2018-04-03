package com.cyb.controller;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.condition.ConditionService;
import com.cyb.service.UserServiceImpl;
import com.google.common.collect.ImmutableMap;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月7日
 */
@RequestMapping("common")
@Controller
public class CommonController {

	@Autowired
	ConditionService conditionService;

	@ResponseBody
	@GetMapping("conditionService")
	public String conditionService() {
		return conditionService.show();
	}

	@ResponseBody
	@GetMapping("responseEntity")
	public ResponseEntity<Map<String, String>> testResponseEntity() {
		Map<String, String> map = ImmutableMap.of("key", "value");
		return ResponseEntity.ok(map);
	}

	@ResponseBody
	@GetMapping("ImmutableMap")
	public Map<String, String> ImmutableMap() {
		return ImmutableMap.of("key", "value");
	}


	public boolean hasRole(String ids, int curId) {
		boolean has = false;
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			if (curId == Integer.valueOf(id[i])) {
				System.out.println("delete id is" + curId);
				has = true;
				break;
			}
		}
		return has;
	}

	

	@GetMapping("toPage")
	public String toPage(Map<String, String> param) {
		param.put("name", "chenyb");
		return "index";
	}

	/*@GetMapping("toPage1")
	public ModelAndView toPage1() {
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb1");
		view.setViewName("index");
		return view;
	}

	@GetMapping("toPage2")
	public ModelAndView toPage2() {
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb2");
		view.setViewName("index");
		return view;
	}

	@GetMapping("toPage3")
	public ModelAndView toPage3() {
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb3");
		view.setViewName("index");
		return view;
	}*/

	@SuppressWarnings("unchecked")
	@GetMapping("userinfor")
	@ResponseBody
	public String userInfor() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Iterator<GrantedAuthority> iter = (Iterator<GrantedAuthority>) userDetails.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority g = iter.next();
			System.out.println("用户角色" + g.getAuthority());
		}
		return userDetails.getUsername();
	}

	// ==========mvc单元测试用例=====================
	@GetMapping("name")
	@ResponseBody
	public String myName(String p1, String p2) {
		System.out.println(p1 + "," + p2);
		return "mvc测试接口";
	}
	
}
