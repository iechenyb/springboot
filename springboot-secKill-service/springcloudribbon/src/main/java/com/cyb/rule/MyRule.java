package com.cyb.rule;

import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年2月13日
 */
public class MyRule implements IRule {
	Log log = LogFactory.getLog(MyRule.class);
	private ILoadBalancer lb;

	public Server choose(Object arg0) {
		Random r = new Random();
		int rnum = r.nextInt(10);
		List<Server> servers = lb.getAllServers();
		if (rnum > 7) {//随机数大于7是用8081，其余用8082
			return getServerByPort(servers, 8081);
		}
		return getServerByPort(servers, 8082);
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
