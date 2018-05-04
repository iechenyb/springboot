package com.example;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WsCxfClientApplication {

	 @Bean
	   JaxWsDynamicClientFactory cxfFactory(){
		   JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		   
		   return factory;
	   }
	public static void main(String[] args) {
		SpringApplication.run(WsCxfClientApplication.class, args);
	}
}
