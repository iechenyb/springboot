package com.kiiik;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@ServletComponentScan
@EnableDiscoveryClient
public class TaskPlatformStarter {
	public static void main(String[] args) {
		SpringApplication.run(TaskPlatformStarter.class, args);
	}
	
	@Bean
	SchedulerFactory gSchedulerFactory(){
		return new StdSchedulerFactory();
	}
	/*@GetMapping("info")
	public String infor(){
		return "增值服务服务端程序！";
	}
	
	@GetMapping("/")
	public String index(){
		return "/index";
	}*/
}
