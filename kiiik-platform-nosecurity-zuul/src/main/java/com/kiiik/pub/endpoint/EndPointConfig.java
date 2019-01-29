package com.kiiik.pub.endpoint;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月27日
 */
@Configuration
public class EndPointConfig {
	Log log = LogFactory.getLog(EndPointConfig.class);
	@Bean
    public Endpoint<Map<String, Object>> customEndPoint() {
        return new SystemEndPoint();
    }
}
