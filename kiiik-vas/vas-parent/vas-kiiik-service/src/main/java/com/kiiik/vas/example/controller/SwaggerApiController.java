package com.kiiik.vas.example.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.vas.example.param.ValidBeanVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月14日
 */
@Api(value = "UserController", tags = { "user interface (need english description!)" })
@RestController
public class SwaggerApiController {
	Log log = LogFactory.getLog(SwaggerApiController.class);

	@ApiOperation(value = "方法描述信息", notes = "注意问题点")
	@GetMapping("/methodOne")
	public String methodOne(@ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
			@ApiParam(name = "username", value = "用户名") @RequestParam String username) {
		return id + "#" + username;
	}
   //返回值以VO结尾，则新增执行时间或者进行结果再封装。
	@ApiOperation(value = "方法描述信息", notes = "注意问题点")
	@GetMapping("/methodTwo")
	public ValidBeanVO methodTwo(@ModelAttribute ValidBeanVO bean) {
		return bean;
	}
}
