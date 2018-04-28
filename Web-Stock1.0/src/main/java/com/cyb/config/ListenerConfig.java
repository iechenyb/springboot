package com.cyb.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cyb.listener.ApplicationStartListener;
/**
 *作者 : iechenyb<br>
 *类描述: InitializingBean<br>
 *创建时间: 2018年4月24日
 */
@Configuration
public class ListenerConfig {
	Log log = LogFactory.getLog(ListenerConfig.class);
	@Bean
    public ApplicationStartListener applicationStartListener(){
        return new ApplicationStartListener();
    }
}
