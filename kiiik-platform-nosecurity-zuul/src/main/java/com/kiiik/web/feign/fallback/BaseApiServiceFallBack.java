package com.kiiik.web.feign.fallback;
import org.springframework.stereotype.Component;

import com.kiiik.web.feign.client.BaseApiService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月28日
 */
@Component
public class BaseApiServiceFallBack implements BaseApiService{

	@Override
	public String query(String name) {
		// TODO Auto-generated method stub
		return "query";
	}

	@Override
	public String queryDefaultGet(String name) {
		// TODO Auto-generated method stub
		return "queryDefaultGet";
	}

	@Override
	public String queryNoRequestParamAnnotation(String name) {
		// TODO Auto-generated method stub
		return "queryNoRequestParamAnnotation";
	}

	@Override
	public String queryGetMapping(String name) {
		// TODO Auto-generated method stub
		return "queryGetMapping";
	}

	@Override
	public String addPostMapping(String name) {
		// TODO Auto-generated method stub
		return "addPostMapping";
	}

	
	
}