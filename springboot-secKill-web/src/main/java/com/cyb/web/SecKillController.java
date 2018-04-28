package com.cyb.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cyb.rule.ResultBean;
/**
 * 
 * @author iechenyb
 * 根据实际环境选择最佳的服务方
 * http://localhost:8884/service-ribbon/m1?a=3&b=2 通过路由进行负载均衡
 * http://localhost:8884/service-ribbon/m2?a=3&b=2 手动设置调用规则
 */
@RestController
public class SecKillController {
    Log log = LogFactory.getLog(getClass());
    
    String serviceId="MIAOSHA-SERVICE";
    @Autowired
    private RestTemplate restTemplate;
  
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 名称为SERVICE-B的服务有两个，但是服务名称都为SERVICE-B，
     *另有一个Service-B2工程启动时服务名称也为SERVICE-B,默认采用轮询的方式，
     *所以返回值会在serviceb和servcieb2之间来回切换<br>
     *创建时间: 2017年7月15日hj12
     *@param a
     *@param b
     *@return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/buys", method = RequestMethod.GET)
    public  ResultBean<Map<String, Object>> miaosha(String token, long userId, String goodsName, int buys,HttpServletRequest req) {
    	//http://localhost:8881/sk/buy?token=1&userId=1&goodsName=1&buys=1 
    	String uri = "/sk/buy?token="+token+"&userId="+userId+"&goodsName="+goodsName+"&buys="+buys;
        return restTemplate.getForEntity("http://"+serviceId+uri, ResultBean.class).getBody();
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("/initStock")
	public ResultBean<Map<String, Object>> initGoods(String goodsName, int prdNum,HttpServletRequest request) {
    	String uri = "/sk/initStock?goodsName="+goodsName+"&prdNum="+prdNum;
    	return restTemplate.getForEntity("http://"+serviceId+uri, ResultBean.class).getBody();
	}
    
    @SuppressWarnings("unchecked")
	@GetMapping("/readResult")
	public ResultBean<Map<String, Object>> result(String goodsName,HttpServletRequest request) {
    	String uri = "/sk/readResult?goodsName="+goodsName;
    	return restTemplate.getForEntity("http://"+serviceId+uri, ResultBean.class).getBody();
	}
    
}