package com.cyb.app.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cyb.app.order.controller.OrderController;
/*@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableScheduling*/
@SpringBootApplication
public class ScheduledTasks {
	Log log = LogFactory.getLog(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = 1000)
    @Scheduled(cron = "0 0 * ? * *")
    public void reportCurrentTime() {
        log.info("The time is now= " + dateFormat.format(new Date()));
    }
    
    @Scheduled(fixedRate = 150000)
    public void reportCurrentTime1() {
       log.info("The time is now: " + dateFormat.format(new Date()));
    }
}
