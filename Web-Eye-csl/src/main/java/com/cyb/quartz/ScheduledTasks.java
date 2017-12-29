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
    	  log.info ("执行默认的检测信息！: The time is now " + dateFormat ().format (new Date ()));
    }
    //单位是毫秒  10分钟执行一次定制
    @Scheduled(fixedRate = 1000 * 60)
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
    
   /* @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }*/
 
    /*@Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
    *//**这是一组定时任务A   开始*//*
    @Bean(name = "cronJob")
    public JobDetailFactoryBean creatptdataBetDayJob() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(CronJob.class);
        factoryBean.setName(CronJob.class.getName());
        factoryBean.setGroup("cronmygroup");
        factoryBean.setDurability(true);
        return factoryBean;
    }
 
    @Bean(name = "cronJobTrigger")
    public CronTriggerFactoryBean cronptdatajobTriggerFactoryBean(@Qualifier("cronJob") JobDetail jobDetail){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetail);
        stFactory.setStartDelay(3000);
        stFactory.setName("crontrigger");
        stFactory.setGroup("cronmygroup");
        String express = "0 * *  * * *";
        stFactory.setCronExpression(express);
        return stFactory;
    }
 
    @Bean
    public SchedulerFactoryBean ptdatajobSchedulerFactoryBean(DataSource dataSource, JobFactory jobFactory, @Qualifier("cronJobTrigger") Trigger sampleJobTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(sampleJobTrigger);
        return factory;
    }*/
    
}
