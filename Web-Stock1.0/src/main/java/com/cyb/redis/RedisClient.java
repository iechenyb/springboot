package com.cyb.redis;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
@Component
public class RedisClient {
	Log logger = LogFactory.getLog(RedisClient.class);
	private static JedisPool pool = null;
    private static String IP_ADDRESS = null;

    @PostConstruct
    public void initRedisCOnfig() {
        try {
            logger.info("------------- redis pool init start------------- ");

            Properties props = new Properties();
            props.load(RedisClient.class.getClassLoader().getResourceAsStream("redis.properties"));
            IP_ADDRESS = props.getProperty("redis.ip");
            // 创建jedis池配置实例
            JedisPoolConfig config = new JedisPoolConfig();

            // 设置池配置项值
            config.setTestWhileIdle(false);
            config.setMaxTotal(Integer.valueOf(props.getProperty("redis.pool.maxTotal")));
            config.setMaxIdle(Integer.valueOf(props.getProperty("redis.pool.maxIdle")));
            config.setMaxWaitMillis(Long.valueOf(props.getProperty("redis.pool.maxWaitMillis")));
            config.setTestOnBorrow(Boolean.valueOf(props.getProperty("redis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(props.getProperty("redis.pool.testOnReturn")));

            pool = new JedisPool(config, IP_ADDRESS, Integer.valueOf(props.getProperty("redis.port")));

            boolean connected = isConnected();
            if(!connected){
                logger.error("redis 初始化出错 缓存服务器连接不上！ ");
                throw new Exception("IP:"+IP_ADDRESS+", redis服务器不可以连接~~~，请检查配置 与redis 服务器");
            }

            logger.info("------------- redis pool init end------------- ");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Error("IP:" + IP_ADDRESS + ",设置redis服务器出错", e);
        }
    }

    public boolean isConnected() {
        return getRedis().isConnected();
    }

    public void destory() {
        pool.destroy();
    }

    public Jedis getRedis() {
        Jedis jedis = pool.getResource();
        jedis.select(0);
        return jedis;
    }

    public Jedis getRedis(int index) {
        Jedis jedis = pool.getResource();
        jedis.select(index);
        return jedis;
    }

    public void returnRedis(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public void returnBrokeRedis(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

}
