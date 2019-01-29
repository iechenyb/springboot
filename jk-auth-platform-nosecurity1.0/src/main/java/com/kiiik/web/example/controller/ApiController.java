package com.kiiik.web.example.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.date.DateUtil;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.RandomUtils;
import com.kiiik.web.example.anno.RequestDateParam;
import com.kiiik.web.example.jsr.bean.ValidateBean;
import com.kiiik.web.system.po.User;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:03:14
 */
@Controller
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
	public Map<String,Object> curUserInfor() throws Exception {
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
	@ResponseBody
    public String requestDateParamTest(@RequestDateParam Date date,int a,String str) {
        System.out.println(date);
        return "parse date is " + date+"--->"+a+","+str;
    }
	
	@PostMapping("validateBean")
	@ResponseBody
	public R<String> validateBean(@RequestBody @Validated ValidateBean bean){
		System.out.println(bean);
		return new R<String>("").success();
	}
	
	@GetMapping("validateParam")
	@ApiImplicitParams({
		@ApiImplicitParam(name="param1",value="参数1",dataType="String",paramType="query")
	})//并未成功校验
	@ResponseBody
	public R<String> validateParam( @Validated @NotBlank String param1){
		System.out.println(param1);
		return new R<String>("").success(param1);
	}
	@Autowired
	GenericService genericService;

	@PostMapping("genUsers")
	@ApiOperation("模拟生成用户信息，仅供测试使用")
	public R<String> genUsers(@ApiParam("生成用户数量") Integer theNumbersOfToGen) {
		User po = null;
		if (theNumbersOfToGen == null) {
			theNumbersOfToGen = 10;
		}
		List<Object> users = new ArrayList<Object>();
		for (int i = 0; i < theNumbersOfToGen; i++) {
			po = new User();
			// po.setId(i); 不设置 采用自增长方式
			po.setUserName(RandomUtils.getChineaseName());
			po.setEmpNo(RandomUtils.getPingYin(po.getUserName()));
			po.setPassword("111111");// RandomUtils.getPassWord(8)
			po.setIsEffect(i % 2);
			po.setLastLoginIp(RandomUtils.getIp());
			po.setLoginIp(RandomUtils.getIp());
			po.setLastLoginTime(Long.valueOf(DateUtil.date2long14()));
			// genericService.insertDBEntity(po);
			try {
				users.add(po);
			} catch (Exception e) {
				// 忽略生产中文乱码的问题
			}
		}
		genericService.insertDBEntityBatch(users);
		return new R<String>("生成" + theNumbersOfToGen + "个用户信息！").success();
	}
	
	@GetMapping("paramListInt")
	@ApiOperation("传递list参数测试")
	@ResponseBody
	public List<Integer> delDepartmentEntity(@RequestParam(value = "ids") List<Integer> ids,Integer[] roleIds){
		 return ids;
	 }
			
}
