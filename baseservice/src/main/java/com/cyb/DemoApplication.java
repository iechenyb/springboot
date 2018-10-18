package com.cyb;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
//@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement(){
    	MultipartConfigFactory factory = new MultipartConfigFactory();
    	factory.setMaxFileSize(100*1024L*1024L);
    	return factory.createMultipartConfig();
    }
}
