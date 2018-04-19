package com.cyb.config;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cyb.access.AccessInterceptor;
import com.cyb.access.UserArgumentResolver;
import com.cyb.aop.ResultBean;
import com.cyb.interceptor.MyInterceptor;

import net.sf.json.JSONObject;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月28日
 */
@Configuration
/*@EnableWebMvc
@ComponentScan*/
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	Log logger = LogFactory.getLog(WebConfig.class);
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
    @Autowired
    AccessInterceptor accessInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：除了excludePathPatterns，其他都拦截判断
        registry.addInterceptor(new MyInterceptor());
        registry.addInterceptor(accessInterceptor)
      /*  .addPathPatterns("/**")
        .excludePathPatterns("/users/login")
        .excludePathPatterns("/users/logout")
        .excludePathPatterns("users/register")*/
        ;
        
        super.addInterceptors(registry);
    }
    
    @Autowired
    UserArgumentResolver userArgumentResolver;
    
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
		super.addArgumentResolvers(argumentResolvers);
	}
	  //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                ResultBean<String> result = new ResultBean<String>();
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    if (e instanceof NullPointerException) {//业务失败的异常，如“账号或密码错误”
                        result.setCode(0);
                        result.setMsg(e.getMessage());
                        logger.error(e.getMessage());
                    } else {
                        result.setCode(0);
                        result.setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员!"+e.getMessage());
                        String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                        logger.error(message, e);
                    }
                } else {
                    if (e instanceof NoHandlerFoundException) {
                        result.setCode(0);
                        result.setMsg("接口 [" + request.getRequestURI() + "] 不存在");
                    } else {
                        result.setCode(0);
                        result.setMsg(e.getMessage());
                        logger.error(e.getMessage(), e);

                    }
                }
                responseResult(response, result);
                return new ModelAndView();
            }

        });
    }
   private void responseResult(HttpServletResponse response, ResultBean<String> result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSONObject.fromObject(result).toString());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
    

}
