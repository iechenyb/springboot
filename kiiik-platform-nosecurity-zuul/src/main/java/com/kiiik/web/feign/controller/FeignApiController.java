package com.kiiik.web.feign.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.web.feign.client.BaseApiService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: 远端过程调用<br>
 * 创建时间: 2018年12月28日 feign消费服务时，以GET方式请求的条件:
 * 
 * 如果想让服务消费者采用GET方式调用服务提供者，那么需要:
 * 
 * 1.服务消费者这边feign调用时，在所有参数前加上@RequestParam注解。
 * 
 * 2.服务消费者这边feign调用时，指明为GET方式（注:如果不指明method,那么在条件1满足的情况下，采用的是默认的GET方式）。
 * 
 * 注:这里条件1和条件2，是“且”的关系(都满足时，才为GET)。
 * 
 * @HystrixCommand的fallbackMethod属性实现回退的。然而，Feign是以接口形式工作的
 */
@RestController
@RequestMapping("api/feign")
public class FeignApiController {
	Log log = LogFactory.getLog(FeignApiController.class);

	@Autowired
	BaseApiService remoteBaseService;

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述:测试 1 正常启动服务 2 关闭服务<br>
	 * 创建时间: 2018年12月28日 fallback 生效条件 feign.hystrix.enabled=true
	 * & @EnableHystrix
	 * 测试终点，是否将用户信息一并传入，当使用feign调用子服务的时候？？
	 * @param name
	 * @return
	 */
	@GetMapping("fallback")
	public String add(String name) {
		String res = remoteBaseService.query(name);
		System.out.println("add返回结果requestparam：" + res);
		res = remoteBaseService.queryDefaultGet(name);
		System.out.println("addDefaultGet返回结果get：" + res);
		res = remoteBaseService.queryNoRequestParamAnnotation(name);
		System.out.println("addNoRequestParamAnnotation返回结果：" + res);
		res = remoteBaseService.queryGetMapping(name);
		System.out.println("getMapping返回结果get：" + res);
		res = remoteBaseService.addPostMapping(name);
		System.out.println("postMapping返回结果post：" + res);
		return res;
	}

	@GetMapping("/fallback/{id}")
	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") }, fallbackMethod = "findByIdFallback")
	public String findById(@PathVariable String id) {
		return remoteBaseService.addPostMapping(id);
	}

	/**
	 * fallback方法
	 * 
	 * @param id
	 * @return
	 */
	public String findByIdFallback(String id) {
		return "findByIdFallback";
	}
}
