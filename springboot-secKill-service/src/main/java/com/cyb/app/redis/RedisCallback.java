package com.cyb.app.redis;
import redis.clients.jedis.Jedis;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public interface RedisCallback<T> {
    public T call(Jedis jedis,Object params);
}
