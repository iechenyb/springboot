package com.kiiik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.kiiik.pub.filter.AccessFilter;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.req.RequestParamAnalysis;

@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@Controller
@EnableWebMvc
public class ZuulAuthServiceStarter {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulAuthServiceStarter.class, args);
	}
	
	@GetMapping("info")
	@ResponseBody
	public String infor(){
		return "增值服务服务端程序！";
	}
	
	@Autowired
	GenericService genericService;
	
	@Autowired
	RequestParamAnalysis analysis;
	    
	@Bean
	public AccessFilter accessFilter() {
		AccessFilter filter = new AccessFilter();
		filter.setGenericService(genericService);
		filter.setAnalysis(analysis);
		return filter;
	}

	@GetMapping("/")
	public ModelAndView toLogin() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/index");
		return view;
	}
}
