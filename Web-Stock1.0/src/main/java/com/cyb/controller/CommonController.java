package com.cyb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.access.AccessLimit;
import com.cyb.access.AccessLimitEl;
import com.cyb.access.UserContext;
import com.cyb.aop.ResultBean;
import com.cyb.condition.ConditionService;
import com.cyb.validate.bean.ValidBean;
import com.google.common.collect.ImmutableMap;

import io.swagger.annotations.Api;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月7日
 */
@Api("测试metrics")
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
	/**
	 * http://localhost:8080/stock/common/validateBeanCommon?name=123&age=13
	 * swagger 编程json请求有误
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param bean
	 *@param bindingResult
	 *@return
	 */
	@GetMapping("validateBean")
    @ResponseBody
    public String say1(@Valid ValidBean bean, BindingResult bindingResult){      
		System.out.println("value    "+bean);
		System.out.println(bindingResult.getFieldError());
		for(FieldError error : bindingResult.getFieldErrors()){
		    System.out.println(error.getField()+"=="+error.getDefaultMessage()+"==="+error.getCode());
	     }
        return bindingResult.hasErrors() ? 
		bindingResult.getFieldError().getDefaultMessage() : "right";
    }
	
	@GetMapping("validateBeanCommon")
    @ResponseBody
    public String say2( ValidBean bean, BindingResult bindingResult){      
		System.out.println("value    "+bean);
		System.out.println(bindingResult.getFieldError());
        return bindingResult.hasErrors() ? 
		bindingResult.getFieldError().getDefaultMessage() : "right";
    }
	
	@GetMapping("accessNeedLogin")
    @ResponseBody
    @AccessLimit(maxCount=3,needLogin=true,seconds=60)
    public String accessNeedLogin( ){
		return "accessNeedLogin ";
    }
	/**
	 * ddos访问限制
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 */
	@GetMapping("accessNoLogin")
    @ResponseBody
    @AccessLimit(maxCount=3,needLogin=false,seconds=60)
    public String accessNoLogin( ){
		return "accessNoLogin ";
    }
	
	@GetMapping("accessNoLoginEl")
    @ResponseBody
    @AccessLimitEl(maxCount="ddos.test.maxCount",needLogin="ddos.test.needLogin",seconds="ddos.test.seconds")
    public String accessNoLoginEL( ){
		return "accessNoLogin ";
    }
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 简单登录测试 <br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 */
	@GetMapping("login")
    @ResponseBody
    public String login(ValidBean user,HttpServletRequest req){
		//user.setUser_id(new Random().nextLong());
		///UserContext.setUser(user);
		req.getSession().setAttribute("user", user);
		return "userid is "+user.getName();
    }
	@ResponseBody
	@GetMapping("String/{name}")
	public String hello(@PathVariable String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		return "Hello "+name+"!";
	}
	
	@ResponseBody
	@GetMapping("list")
	public List<String> list( String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		ArrayList<String> arr = new ArrayList<String>(); 
		arr.add(name);
		return arr;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("ResultBean")
	public ResultBean<String> ResultBean(String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		return new ResultBean<String>().data(name).success().msg("统一返回值定义测试");
	}
	
	@ResponseBody
	@GetMapping("user/{name}")
	public User user(@PathVariable String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		return new User(name);
	}
	
	@GetMapping("page")
	public ModelAndView page(){
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}
	
	@ResponseBody
	@GetMapping("map/{name}")
	public Map<String,Object> map(@PathVariable String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("name", name);
		return ret;
	}
	
	@Autowired
	Environment env;
	@ResponseBody
	@GetMapping("environment")
	public String readProperties(String path){
		System.out.println(" threadname "+Thread.currentThread().getName());
		System.out.println("====="+env.getProperty("${a.b.c}"));
		return env.getProperty("a.b.c");
	}
	//=========================jmeter=================
	@ResponseBody
	@GetMapping("jemeter/get")
	public String getJemeter(String id,String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		System.out.println("id="+id+",name"+name);
		return "id="+id+",name="+name;
	}
	@ResponseBody
	@PostMapping("jemeter/post")
	public String postJmeter(String id,String name){
		System.out.println(" threadname "+Thread.currentThread().getName());
		System.out.println("====="+id+"====="+name);
		return "id="+id+",name="+name;
	}
}

class User{
	private String name;
    public User(String name){
    	this.name = name;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
