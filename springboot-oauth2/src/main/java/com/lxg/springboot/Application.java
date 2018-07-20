package com.lxg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lxg
 * on 2017/2/18.
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
public class Application  extends WebMvcConfigurerAdapter{

    public static void main(String[] args){
        new SpringApplication(Application.class).run(args);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/toLogin").setViewName("login");//登录视图
	    registry.addViewController("/refuse").setViewName("refuse");//拒绝访问页面提示
	    registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }
}
