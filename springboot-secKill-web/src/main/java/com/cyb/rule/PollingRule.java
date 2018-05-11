package com.cyb.rule;

import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import ch.qos.logback.core.net.SyslogOutputStream;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年2月13日
 */
public class PollingRule implements IRule {
	Log log = LogFactory.getLog(PollingRule.class);
	
	private ILoadBalancer lb;
    long num =0;
	public Server choose(Object arg0) {
		List<Server> servers = lb.getAllServers();
		/*Random r = new Random();
		int rnum = r.nextInt(10);
		if (rnum > 7) {//随机数大于7是用8081，其余用8082
			return getServerByPort(servers, 8081);
		}
		return getServerByPort(servers, 8082);*/
		num++;
		System.out.println("当前访问次数："+num);
		if(num%2==0){//自定义轮询规则
			return getServerByPort(servers, 8882);
		}else{
			return getServerByPort(servers, 8881);
		}
		
	}

	public Server getServerByPort(List<Server> servers, int port) {
		for (Server s : servers) {
			if (s.getPort() == port) {
				return s;
			}
		}
		return null;
	}

	public ILoadBalancer getLoadBalancer() {
		return lb;
	}

	public void setLoadBalancer(ILoadBalancer arg0) {
		this.lb = arg0;
	}
}
