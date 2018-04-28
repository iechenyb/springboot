package com.cyb.listener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>ApplicationEvent
 *创建时间: 2018年4月24日
 */
//@Component
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent>{
	Log log = LogFactory.getLog(ApplicationStartListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		 System.out.println("我的父容器为：" + contextRefreshedEvent.getApplicationContext().getParent());
	     System.out.println("初始化时我被调用了。");
		System.out.println("springboot 项目启动监听！");//不仅仅显示一次
	}
}
