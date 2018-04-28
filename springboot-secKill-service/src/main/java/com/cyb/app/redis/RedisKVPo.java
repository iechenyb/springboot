package com.cyb.app.redis;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public class RedisKVPo {
	Log log = LogFactory.getLog(RedisKVPo.class);
	private String k;
    private String v;

    public RedisKVPo(){}

    public RedisKVPo(String k, String v) {
        this.k = k;
        this.v = v;
    }
    public String getK() {
        return k;
    }
    public void setK(String k) {
        this.k = k;
    }
    public String getV() {
        return v;
    }
    public void setV(String v) {
        this.v = v;
    }
}
