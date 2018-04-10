package com.cyb.cacheable.config;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月3日
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
	Log log = LogFactory.getLog(CacheConfig.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.afterPropertiesSet();
		setSerializer(redisTemplate);
		return redisTemplate;
	}

	@SuppressWarnings("rawtypes")
	private void setSerializer(RedisTemplate<String, String> template) {
		@SuppressWarnings("unchecked")
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		// om.setVisibility(PropertyAccessor.ALL,
		// JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer);
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager rcm = new CustomizedRedisCacheManager(redisTemplate);
				//new RedisCacheManager(redisTemplate);
		// 设置缓存过期时间，秒
		//rcm.setDefaultExpiration(60);
		return rcm;
	}
	@Bean
	public KeyGenerator keyGenerator() {
	    return new KeyGenerator() {
	        @Override
	        public Object generate(Object target, Method method, Object... params) {
	            StringBuilder sb = new StringBuilder();
	            String[] value = new String[1];
	            Cacheable cacheable = method.getAnnotation(Cacheable.class);
	            if (cacheable != null) {
	                value = cacheable.value();
	            }
	            CachePut cachePut = method.getAnnotation(CachePut.class);
	            if (cachePut != null) {
	                value = cachePut.value();
	            }
	            CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
	            if (cacheEvict != null) {
	                value = cacheEvict.value();
	            }
	            if(value[0].contains("#")){
	            	sb.append(value[0].split("#")[0]);
	            }else{
	                sb.append(value[0]);
	            }
	            for (Object obj : params) {
	                sb.append(":" + obj.toString());
	            }
	            return sb.toString();
	        }
	    };
	}
	
	/*@Bean
	public KeyGenerator keyGenerator() {
	    return new KeyGenerator() {
	        @Override
	        public Object generate(Object target, Method method, Object... params) {
	            StringBuilder sb = new StringBuilder();
	            sb.append(target.getClass().getName());
	            sb.append(":" + method.getName());
	            for (Object obj : params) {
	                sb.append(":" + obj.toString());
	            }
	            return sb.toString();
	        }
	    };
	}*/
}
