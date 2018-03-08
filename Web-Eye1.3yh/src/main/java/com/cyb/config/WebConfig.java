package com.cyb.config;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cyb.interceptor.MyInterceptor;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月28日
 */
/*@Configuration
@EnableWebMvc
@ComponentScan*/
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    @SuppressWarnings("unused")
	private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("404.html");
        //registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE); 
        super.addViewControllers(registry);    
    }
    
    
    @Override 
    public void configurePathMatch(PathMatchConfigurer configurer) { 
        configurer.setUseSuffixPatternMatch(false); 
        super.configurePathMatch(configurer); 
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/lhmj/**").addResourceLocations("/lhmj/");
        registry.addResourceHandler("phone/**").addResourceLocations("phone/");
        super.addResourceHandlers(registry);  
     }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    } 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：除了excludePathPatterns，其他都拦截判断
        registry.addInterceptor(new MyInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/users/login")
        .excludePathPatterns("/users/logout")
        .excludePathPatterns("users/register")
        ;
        super.addInterceptors(registry);
    }

}
