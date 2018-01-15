package com.cyb.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 启动的时候观察控制台是否打印此信息<br>
 *创建时间: 2017年12月13日
 */
public class HelloServiceImpl {
	Log log = LogFactory.getLog(HelloServiceImpl.class);
	 /**
     *启动的时候观察控制台是否打印此信息;
     */
    public HelloServiceImpl(){
           System.out.println("HelloService.HelloService()");
    }
}
