package com.kiiik;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@MapperScan("com.kiiik.vas")//扫描mapper
@SpringBootApplication
public class KiiikVasServiceStarter extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(KiiikVasServiceStarter.class, args);
	}
}
