package com.kiiik.pub.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.khfl.reportCaculate.constants.ClientDataConstants;
/**
 * @author iechenyb
 * @time 2018年9月05日 12:58:46
 * 启动监听
 */
@Component
public class CenterAuthApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	Environment env;
	
	@Autowired
	GenericService genericService;
	
	@Autowired
	ClientDataConstants clientDataConstants;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		System.out.println("-------------start---------------");
		//初始化所有有效客户（未销户）
		clientDataConstants.initValidClient();
		System.out.println("-------------所有未销户客户信息 <资产账号,中文姓名>---------------"+ClientDataConstants.ALL_VALId_CLIENT_INFO.size());
		System.out.println("-------------所有未销户客户 ---------------"+ClientDataConstants.ALL_VALId_CLIENT.size());
		//缓存所有VIP客户
		clientDataConstants.initVipClient();
		System.out.println("-------------所有vip客户---------------"+ClientDataConstants.ALL_VIP_CLIENT.size());
		//缓存所有休眠客户
		clientDataConstants.initFreezeClient();
		System.out.println("-------------所有休眠客户---------------"+ClientDataConstants.ALL_FREEZE_CLIENT.size());
		//缓存所有未满一个自然月的客户
		
		
		
	}
}
