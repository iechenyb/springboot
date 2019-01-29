package com.kiiik.web.feign.client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kiiik.web.feign.config.EnableUserInfoTransmitterAutoConfiguration;
import com.kiiik.web.feign.fallback.BaseApiServiceFallBack;

import feign.Headers;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 *fallback 是根据类型查找默认调用器的，所以需要进行组件化！
 */
@FeignClient(value="BASE-SERVICE",
fallback =BaseApiServiceFallBack.class,
configuration = EnableUserInfoTransmitterAutoConfiguration.class)
public interface  BaseApiService {
	Log log = LogFactory.getLog(BaseApiService.class);
	@RequestMapping(value="/api/query",method=RequestMethod.GET)//基础服务的全路径
	@Headers({"Content-Type: application/json","Accept: application/json"})
	public String query(@RequestParam("name") String name);//必须带@requestparam注解，否则无法解析
	
	@RequestMapping(value="/api/query")//基础服务的全路径
	public String queryDefaultGet(@RequestParam("name") String name);//默认get测试
	
	@RequestMapping(value="/api/query",method=RequestMethod.GET)//基础服务的全路径
	public String queryNoRequestParamAnnotation(String name);//提示post不支持
	
	@GetMapping(value="/api/query")//基础服务的全路径
	public String queryGetMapping(@RequestParam("name") String name);
	
	@PostMapping(value="/api/add")//基础服务的全路径
	public String addPostMapping(@RequestParam("name") String name);
	
}

