package com.kiiik.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.zuul.filter.AccessFilter;
import com.kiiik.zuul.filter.ErrorFilter;
import com.kiiik.zuul.web.log.repository.SystemLogRepository;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@RefreshScope
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@Controller
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
    @Autowired
	SystemLogRepository logRes;
    
    @Bean
    public AccessFilter accessFilter() {
    	AccessFilter filter = new AccessFilter();
    	filter.setSystemLogRepository(logRes);
        return filter;
    }
    @Bean 
    public ErrorFilter errorFilter(){
        return new ErrorFilter();
    }
	@GetMapping("/")
	public ModelAndView toLogin() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/index");
		view.addObject("author", "chenyuanbao");
		return view;
	}
	
	@GetMapping("/500")
	public ModelAndView t500() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/index");
		view.addObject("author", "chenyuanbao");
		System.out.println(1/0);
		return view;
	}
	
}
