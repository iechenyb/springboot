package com.kiiik.vas.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 假如工程存在上下文，则需要在mapping上加上上下文信息
 * 比如 工程名为 vasservice
 * 如下测试用例 需要设置path参数
 * 当根据版本更新的时候 需要手动更改path参数值
 * @author DHUser
 *
 */
//发现远端服务
@FeignClient(name = "${vas.kiiik.service.name}",path="${vas.kiiik.service.context}")
interface ComputerFeignClient{
	@GetMapping("/json/sayHello")
	public String say(@RequestParam("words") String words);
}

@RestController
public class FeignStudyController {
	Log log = LogFactory.getLog(FeignStudyController.class);
	//本地调用远端服务
	@Autowired
	ComputerFeignClient client;
	
	@GetMapping("/sayHello")
	public String localMethodName(@RequestParam("words") String words){
		return client.say(words);//调用远端服务
	}
}
