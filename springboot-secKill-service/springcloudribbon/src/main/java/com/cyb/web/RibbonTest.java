package com.cyb.web;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.rule.MyRule;
import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年2月14日
 */
public class RibbonTest {
	Log log = LogFactory.getLog(RibbonTest.class);
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		ConfigurationManager
		.getConfigInstance()
		.setProperty("my-client.ribbon.listOfServers", "180.169.108.228:8080,180.169.108.228:8080");
		ConfigurationManager
		.getConfigInstance()
		.setProperty("my-client.ribbon.NFLoadBalancerRuleClassName", MyRule.class.getName());
		
		RestClient client =(RestClient)ClientFactory.getNamedClient("my-client");
		HttpRequest request = HttpRequest.newBuilder().uri("/VASDataCenter/qutoes/infor.html").build();
		for(int i=0;i<10;i++){
			HttpResponse  res = client.executeWithLoadBalancer(request);
			String json = res.getEntity(String.class);
			System.out.println(json);
		}
	}
}
