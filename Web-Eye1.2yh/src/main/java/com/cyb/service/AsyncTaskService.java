package com.cyb.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月7日
 */
@Service
@Async //注解表明该方法是一个异步方法,注解在类上则表示类中所有方法都是异步的.
public class AsyncTaskService {
	Log log = LogFactory.getLog(AsyncTaskService.class);
	public void executeAsyncTask(Integer i) {
        System.out.println("执行异步任务：" + i);
    }

    public void executeAsyncTaskPlus(Integer i) {
        System.out.println("执行异步任务+1：" + (i + 1));
    }
}
