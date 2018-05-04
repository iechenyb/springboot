package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描mapper文件
@MapperScan("com.example.dao")
public class WsCxfServiceApplication {
  
	public static void main(String[] args) {
		SpringApplication.run(WsCxfServiceApplication.class, args);
	}
}
