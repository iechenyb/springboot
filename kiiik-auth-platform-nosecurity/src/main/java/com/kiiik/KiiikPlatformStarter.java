package com.kiiik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@ServletComponentScan
@EnableDiscoveryClient
public class KiiikPlatformStarter {
	public static void main(String[] args) {
		SpringApplication.run(KiiikPlatformStarter.class, args);
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
