package com.example.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.xml.ws.Endpoint;
import com.example.service.GreetingServices;
import com.example.service.impl.GreetingServicesImpl;
import com.example.service.impl.UserServicesImpl;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月3日
 */
@Configuration
public class CxfConfig {
	
    @Autowired
    private Bus bus;
    
    @Bean
    public Bus bus(){
    	return new SpringBus();
    }
    
   /* @Autowired
    private GreetingServices greetingServices;*/
    
    //services 默认services  可以手动指定
    /*@Bean  
    public ServletRegistrationBean dispatcherServlet() {  
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");  
    }  */
    @Bean
    public Endpoint greetingServices() {
    	System.out.println("bus类型："+bus);
        EndpointImpl endpoint = new EndpointImpl(bus,new GreetingServicesImpl());
        endpoint.publish("/greetingServices");//接口发布在 /greetingServices 目录下
        return endpoint;
    }
    @Bean
    public Endpoint userServices() {
        EndpointImpl endpoint = new EndpointImpl(bus,new UserServicesImpl());
        endpoint.publish("/userServices");//接口发布在 /userServices 目录下
       // endpoint.getInInterceptors().add(new AuthInterceptor());  
        return endpoint;
    }
}
