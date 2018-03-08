package com.cyb.service;
import java.util.Random;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月16日
 */
@Service
public class DemoAsyncServiceImpl {
	Log log = LogFactory.getLog(DemoAsyncServiceImpl.class);
	 public static Random random =new Random();  
	  
	    @Async  
	    public Future<String> doTaskOne() throws Exception {  
	        System.out.println("开始做任务一");  
	        long start = System.currentTimeMillis();  
	        Thread.sleep(random.nextInt(10000));  
	        long end = System.currentTimeMillis();  
	        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");  
	        return new AsyncResult<>("任务一完成");  
	    }  
	  
	    @Async  
	    public Future<String> doTaskTwo() throws Exception {  
	        System.out.println("开始做任务二");  
	        long start = System.currentTimeMillis();  
	        Thread.sleep(random.nextInt(10000));  
	        long end = System.currentTimeMillis();  
	        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");  
	        return new AsyncResult<>("任务二完成");  
	    }  
	  
	    @Async  
	    public Future<String> doTaskThree() throws Exception {  
	        System.out.println("开始做任务三");  
	        long start = System.currentTimeMillis();  
	        Thread.sleep(random.nextInt(10000));  
	        long end = System.currentTimeMillis();  
	        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");  
	        return new AsyncResult<>("任务三完成");  
	    }  
}
