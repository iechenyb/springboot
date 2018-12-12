package com.kiiik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cyb.h2.H2Manager;

@SpringBootApplication
@Controller
@ServletComponentScan
public class CentralizeAuthorizeStarter {
	public static void main(String[] args) {
		SpringApplication.run(CentralizeAuthorizeStarter.class, args);
	}
	
	@GetMapping("info")
	public String infor(){
		return "增值服务服务端程序！";
	}
	
	@GetMapping("/")
	public String index(){
		return "/index";
	}
}
