package com.cyb.web;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.rule.MyRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
/**
 *作者 : iechenyb<br>
 *类描述: 负载均衡的基本原理<br>
 *创建时间: 2018年2月13日
 */
public class LBMain {
	Log log = LogFactory.getLog(LBMain.class);
	public static void main(String[] args) {
		//ILoadBalancer lb = new BaseLoadBalancer();
		BaseLoadBalancer lb = new BaseLoadBalancer();
		List<Server> servers = new ArrayList<Server>();
		servers.add(new Server("localhost",8081));
		servers.add(new Server("localhost",8082));
		servers.add(new Server("localhost",8083));
		lb.setRule(new MyRule());
		lb.addServers(servers);
		for(int i=0;i<10;i++){
			//默认轮询
			Server s = lb.chooseServer(null);
			System.out.println(s);
		}
	}
}
