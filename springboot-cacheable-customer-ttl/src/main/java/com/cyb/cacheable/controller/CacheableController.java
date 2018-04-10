package com.cyb.cacheable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.cacheable.bean.News;
import com.cyb.cacheable.service.CacheableServiceImpl;

@RestController
public class CacheableController {
	@Autowired
	CacheableServiceImpl service;
    @RequestMapping("getData")
	public String readData(int num){
    	try {
			readNews();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return "success";
	}
    public void readNews() throws InterruptedException{
		for(long i=0;i<5;i++){
			System.out.println("====================1====================");
			News tmp = service.getNewsById("1");
			System.out.println("====================2===================="+tmp.getTitle());
		}
	}
}
