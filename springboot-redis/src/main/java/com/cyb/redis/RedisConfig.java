package com.cyb.redis;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration  
@EnableAutoConfiguration  
@ConfigurationProperties(prefix = "spring.redis")  
public class RedisConfig {  
  
    private static Logger logger = Logger.getLogger(RedisConfig.class);  
      
    private String host;  
  
    private int port;  
  
    private String password;  
  
    private int timeout;  
      
    @Bean  
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig poolConfig = new JedisPoolConfig();  
        poolConfig.setMaxTotal(2);  
        poolConfig.setMaxIdle(1);  
        poolConfig.setMaxWaitMillis(2000);  
        poolConfig.setTestOnBorrow(false);  
        poolConfig.setTestOnReturn(false);  
        return poolConfig;  
    }  
      
    @Bean
    public ShardedJedisPool getShardedJedisPool(){
    	//设置Redis信息  
        JedisShardInfo shardInfo1 = new JedisShardInfo(host, 6379, 500);  
        JedisShardInfo shardInfo2 = new JedisShardInfo(host, 6379, 500);  
        //初始化ShardedJedisPool  
        List<JedisShardInfo> infoList = Arrays.asList(shardInfo1, shardInfo2);  
        ShardedJedisPool sharedJedisPool = new ShardedJedisPool(getRedisConfig(), infoList);  
        return sharedJedisPool;
    }
    @Bean  
    public JedisPool getJedisPool(){  
        JedisPoolConfig config = getRedisConfig();  
        JedisPool pool = new JedisPool(config,host,port,timeout);  
        logger.info("init JredisPool ...");  
        return pool;  
    }  
    
    @Bean  
    public Jedis getJedis(){  
        Jedis pool = new Jedis(host,port);  
        logger.info("init JredisPool ...");  
        return pool;  
    }  
  
  
    public String getHost() {  
        return host;  
    }  
  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    public int getPort() {  
        return port;  
    }  
  
    public void setPort(int port) {  
        this.port = port;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public int getTimeout() {  
        return timeout;  
    }  
  
    public void setTimeout(int timeout) {  
        this.timeout = timeout;  
    }  
}
