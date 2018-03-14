package com.cyb.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.cyb.condition.ConditionService;
import com.cyb.condition.LinuxCondition;
import com.cyb.condition.LinuxConditionService;
import com.cyb.condition.WindowsCondition;
import com.cyb.condition.WindowsConditionService;
/**
 *作者 : iechenyb<br>
 *类描述:动态加载bean<br>
 *创建时间: 2018年3月7日
 */
@Configuration
public class ConditionalConfig {
	Log log = LogFactory.getLog(ConditionalConfig.class);
	
    @Bean
    @Conditional(WindowsCondition.class)
    public ConditionService windowsList() { return new WindowsConditionService(); }

    @Bean
    @Conditional(LinuxCondition.class)
    public ConditionService linuxList() { return new LinuxConditionService(); }
}
