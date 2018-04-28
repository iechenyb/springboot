package com.cyb.app.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	Log log = LogFactory.getLog(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0 * ? * *")
    public void reportCurrentTime() {
        log.info("The time is now= " + dateFormat.format(new Date()));
    }
    
    @Scheduled(fixedRate = 150000)
    public void reportCurrentTime1() {
       log.info("The time is now: " + dateFormat.format(new Date()));
    }
}
