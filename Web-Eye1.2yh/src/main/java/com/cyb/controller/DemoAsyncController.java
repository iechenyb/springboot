package com.cyb.controller;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.aop.ResultBean;
import com.cyb.service.DemoAsyncServiceImpl;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月16日
 */
@RestController  
@RequestMapping(value="/async")   
public class DemoAsyncController {
	Log log = LogFactory.getLog(DemoAsyncController.class);
	@Resource  
    private DemoAsyncServiceImpl demoAsyncService;  
  
    /** 
     * 测试异步方法调用顺序 
     */  
    @SuppressWarnings("unchecked")
	@ApiOperation(value="测试异步方法调用顺序", notes="getEntityById")  
    @RequestMapping(value = "/getTestDemoAsync", method = RequestMethod.GET)  
    public @ResponseBody ResultBean<Integer> getEntityById() throws Exception {  
        long start = System.currentTimeMillis();  
        Future<String> task1 = demoAsyncService.doTaskOne();  
        Future<String> task2 = demoAsyncService.doTaskTwo();  
        Future<String> task3 = demoAsyncService.doTaskThree();  
        while(true) {  
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待  
                break;  
            }  
            Thread.sleep(1000);  
        }  
        long end = System.currentTimeMillis();  
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");  
        return new ResultBean<Integer>().success().data(1);  
    }  
}
