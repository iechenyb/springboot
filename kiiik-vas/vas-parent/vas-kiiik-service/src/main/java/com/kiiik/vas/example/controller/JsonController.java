package com.kiiik.vas.example.controller;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.utils.ParamterUtils;
import com.kiiik.vas.example.model.RQ;
import com.kiiik.vas.example.model.User;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述:get 请求没有 request body 所以  json请求只能用post<br>
 *创建时间: 2018年5月15日
 */
@RestController
public class JsonController {
	Log log = LogFactory.getLog(JsonController.class);
	/**
	 * [
		{"name":"test0","password":"gz0"}
		,{"name":"test1","password":"gz1"}
		]
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param list
	 *@return
	 */
	@PostMapping(value="recList")
	public Object paraList(@RequestBody  List<User> list){
		return list;
	}
	/**
	 *	{"name":"test0","password":"gz0"} 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param user
	 *@return
	 */
	@PostMapping(value="addUser")
	public User addUser(@RequestBody User user) {        
	    return user;  
	}    
	
	/**
	 * ["a","b"]
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param arrStr
	 *@return
	 */
	@PostMapping(value="arrStr")
	public List<String> addUser(@RequestBody List<String> arrStr) {        
	    return arrStr;  
	}   
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 复杂的请求对象
	 *RequestBody jsr校验<br>
	 *创建时间: 2017年7月15日hj12
	 *@param arrStr
	 *@return
	 */
	@PostMapping(value="rqPost")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public  ResultBean<RQ> addUserPost(@Valid  @RequestBody RQ rq,BindingResult bindingResult){
	   ParamterUtils.checkObjectParams(bindingResult);
	   try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    return new ResultBean<RQ>(rq);  
	}    
	@GetMapping(value="rqGet")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public  ResultBean<RQ> addUserGet(RQ rq,BindingResult bindingResult){
	   ParamterUtils.checkObjectParams(bindingResult);
	   try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    return new ResultBean<RQ>(rq);  
	}   
	@GetMapping("/sayHello")
	public String localMethodName(@RequestParam("words") String words){
		return "Hi "+words;
	}
}
