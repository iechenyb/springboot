package com.cyb.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月3日
 */
@Configuration
@EnableCaching
public class CacheConfig {
	Log log = LogFactory.getLog(CacheConfig.class);
}
