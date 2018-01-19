package com.cyb.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyb.service.PlanServiceImpl;

/**
 * 作者 : iechenyb<br>
 * 类描述: https://www.cnblogs.com/lic309/p/4089633.html<br>
 * 创建时间: 2017年12月14日
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {
	Log log = LogFactory.getLog(ScheduledTasks.class);

	@Autowired
	PlanServiceImpl planService;
	@Scheduled(cron = "0 * *  * * * ")
	//@Scheduled(fixedRate = 1000 * 60*5)
	public void reportCurrentTime() {
		planService.savePlan(null);
		// 默认测试
		log.info("执行默认的检测信息!");
	}
	// 每1分钟执行一次
	@Scheduled(cron = "0 0 *  * * * ")
	public void reportCurrentByCron() {
		log.info("Scheduling Tasks Examples By Cron " );
	}
}
