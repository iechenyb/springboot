package com.kiiik.zuul.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月15日
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class SessionConfig {
	Log log = LogFactory.getLog(SessionConfig.class);
	// 冒号后的值为没有配置文件时，制动装载的默认值
	@Value("${redis.hostname:localhost}")
	String HostName;
	@Value("${redis.port:6379}")
	int Port;

	@SuppressWarnings("deprecation")
	@Bean public JedisConnectionFactory connectionFactory() { 
		JedisConnectionFactory connection = new JedisConnectionFactory(); 
		connection.setPort(6011);
		connection.setHostName("192.168.108.119"); 
		return connection; }

}
