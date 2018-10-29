package com.kiiik.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.zuul.filter.AccessFilter;
import com.kiiik.zuul.web.log.repository.SystemLogRepository;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@RefreshScope
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@Controller
public class ZuulApplication {
	HttpClientConfiguration x; 
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
       // System.out.println(MessageFormat.format("{0}-{1}",1,2));
    }
    
    @Autowired
	SystemLogRepository logRes;
    
    @Bean
    public AccessFilter accessFilter() {
    	AccessFilter filter = new AccessFilter();
    	filter.setSystemLogRepository(logRes);
        return filter;
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
	
	/*@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    config.setMaxAge(18000L);
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}*/
}
