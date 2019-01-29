package com.kiiik.pub.filter;

import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.fastjson.JSON;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.RequestUtils;
import com.kiiik.utils.req.RequestParamAnalysis;
import com.kiiik.web.feign.context.UserInfoContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;



public class AccessFilter extends ZuulFilter {
	
	GenericService genericService;
	RequestParamAnalysis analysis;
	
    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        return true;
    }
/**
 * 子服务和本服务是否都拦截？？？
 */
    @Override
    public Object run() {
    	System.out.println("accessfilter....");
        RequestContext.getCurrentContext()
        .addZuulRequestHeader(UserInfoContext.KEY_USERINFO_IN_HTTP_HEADER,
        		JSON.toJSONString(
        				RequestUtils.getSessionUser(
        						SecurityContextHolder.getContext().getAuthentication())));
        if(RequestUtils.needSaveLog(RequestContext.getCurrentContext().getRequest())){
        	genericService.insertDBEntity(RequestUtils.getSystemLog(
        			RequestContext.getCurrentContext().getRequest(),true));
        }
        return null;
    }

	public GenericService getGenericService() {
		return genericService;
	}

	public void setGenericService(GenericService genericService) {
		this.genericService = genericService;
	}

	public RequestParamAnalysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(RequestParamAnalysis analysis) {
		this.analysis = analysis;
	}

}
