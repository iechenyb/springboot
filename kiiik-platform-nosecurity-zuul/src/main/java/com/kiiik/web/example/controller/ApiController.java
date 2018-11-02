package com.kiiik.web.example.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.web.example.anno.RequestDateParam;
import com.kiiik.web.example.jsr.bean.ValidateBean;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:03:14
 */
@RestController
@RequestMapping("api")
public class ApiController extends BaseController{
	
	Log log = LogFactory.getLog(ApiController.class);
	
	@GetMapping("query")
	@ResponseBody
	public String getP(String username) {
		System.out.println("查看权限和角色");
		return "信息列表!";
	}

	@ResponseBody
	@GetMapping("add")
	public String add(String name) {
		return "添加信息成功！";
	}

	@ResponseBody
	@GetMapping("delete")
	public String delete(HttpServletRequest req) {
		return "删除信息成功";
	}

	@ResponseBody
	@GetMapping("update") 
	public String update(String name) {
		return "更新信息成功！";
	}
	@ResponseBody
	@GetMapping("free") 
	public String free(String name) {
		return "free！";
		
	}
	@ResponseBody
	@GetMapping("curUserInfor") 
	public Map<String,Object> curUserInfor() {
		Map<String,Object> ret = new HashMap<>();
		ret.put("sessionUser",getSessionUser());
		ret.put("systemUser",getSystemUser());
		return ret;
		
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param date 自定义标签测试
	 *@param a
	 *@param str
	 *@return @RequestParam("date") 不可用
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name="date",value="日期",dataType="java.util.Date",paramType="query")
	})
	@GetMapping(value = "requestDateParamTest")
    public String requestDateParamTest(@RequestDateParam Date date,int a,String str) {
        System.out.println(date);
        return "parse date is " + date+"--->"+a+","+str;
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping("validateBean")
	public ResultBean<String> validateBean(@RequestBody @Validated ValidateBean bean){
		System.out.println(bean);
		return new ResultBean<String>("").success();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("validateParam")
	@ApiImplicitParams({
		@ApiImplicitParam(name="param1",value="参数1",dataType="String",paramType="query")
	})//并未成功校验
	public ResultBean<String> validateParam( @Validated @NotBlank String param1){
		System.out.println(param1);
		return new ResultBean<String>("").success(param1);
	}
}
