package com.cyb.quartz;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cyb.po.NetClass;
import com.cyb.service.NetClsServiceImpl;
import com.cyb.service.NetServiceImpl;
/**
 *作者 : iechenyb<br>
 *类描述: https://www.cnblogs.com/lic309/p/4089633.html<br>
 *创建时间: 2017年12月14日
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{
	Log log = LogFactory.getLog(ScheduledTasks.class);
	
	@Autowired
	NetServiceImpl netService;
	
	@Autowired
	NetClsServiceImpl clsService;
	//五分钟一次默认测试
    @Scheduled(fixedRate = 1000 * 60*5)
    public void reportCurrentTime(){
    	//默认测试
    	  netService.saveUrlTest("http://www.baidu.com");
    	  System.out.println ("执行默认的检测信息！: The time is now " + dateFormat ().format (new Date ()));
    }
    //单位是毫秒  10分钟执行一次定制
    @Scheduled(fixedRate = 1000 * 60*10)
    public void checkNet(){
    	System.out.println("执行定制的检测信息！");
    	List<NetClass> lst = clsService.getList();
    	if(!CollectionUtils.isEmpty(lst)){
    		for(NetClass cls:lst){
    			netService.saveUrlTest(cls);
    		}
    	}
    }


    //每1分钟执行一次
    @Scheduled(cron = "0 * *  * * * ")
    public void reportCurrentByCron(){
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }
    
}
