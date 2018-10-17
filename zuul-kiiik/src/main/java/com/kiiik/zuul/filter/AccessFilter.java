package com.kiiik.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.fastjson.JSON;
import com.kiiik.zuul.utils.RequestUtils;
import com.kiiik.zuul.web.log.repository.SystemLogRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessFilter extends ZuulFilter {
	
	SystemLogRepository systemLogRepository;
	
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

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println("用户信息："+request.getRemoteUser());
        request.setAttribute("name", "chenyb");
        ctx.addZuulRequestHeader("Cookie", "SESSION=" + request.getSession().getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestContext requestContext = RequestContext.getCurrentContext();
        String userInfor = JSON.toJSONString(authentication.getPrincipal());
        System.out.println(userInfor+"\n"+JSON.parseObject(userInfor).get("username"));
        requestContext.addZuulRequestHeader("X-AUTH-ID",authentication.getPrincipal().toString());
        //System.out.println(request.getSession().getId()+",send {} request to {}"+ request.getMethod()+ request.getRequestURL().toString());         //获取传来的参数accessToken
        System.out.println(RequestUtils.getSystemLog(request));
        systemLogRepository.save(RequestUtils.getSystemLog(request));
        return null;
    }

	public SystemLogRepository getSystemLogRepository() {
		return systemLogRepository;
	}

	public void setSystemLogRepository(SystemLogRepository systemLogRepository) {
		this.systemLogRepository = systemLogRepository;
	}

}
