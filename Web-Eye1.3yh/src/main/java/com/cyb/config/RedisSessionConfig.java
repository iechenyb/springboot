package com.cyb.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月8日
 */
@Configuration
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为60秒  
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)  
public class RedisSessionConfig {
	Log log = LogFactory.getLog(RedisSessionConfig.class);
}
