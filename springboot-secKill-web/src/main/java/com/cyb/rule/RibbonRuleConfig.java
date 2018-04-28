package com.cyb.rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年10月23日
 */
@Configuration
public class RibbonRuleConfig {
	Log log = LogFactory.getLog(RibbonRuleConfig.class);
	@Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }

}
