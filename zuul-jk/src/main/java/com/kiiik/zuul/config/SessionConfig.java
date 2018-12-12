package com.kiiik.zuul.config;

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
	// 冒号后的值为没有配置文件时，制动装载的默认值
	@Value("${redis.hostname:localhost}")
	String hostName;
	@Value("${redis.port:6379}")
	int port;

	@SuppressWarnings("deprecation")
	@Bean public JedisConnectionFactory connectionFactory() { 
		JedisConnectionFactory connection = new JedisConnectionFactory(); 
		connection.setPort(port);
		connection.setHostName(hostName); 
		return connection; 
	}

}
