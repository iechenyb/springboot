package com.kiiik.config.sec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.ResponseUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月3日
 */
@Component
public class KiiikCustomizationFilter implements Filter {
	Log log = LogFactory.getLog(KiiikCustomizationFilter.class);
	List<String> paths= new ArrayList<String>();
	AntPathMatcher antPathMatcher = new AntPathMatcher();
	@Autowired
	Environment env;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String pathsstrs ="/**/v2/api-docs/**,/**/configuration/ui/**,/**/swagger-resources/**,/**/configuration/security/**,/swagger-ui.html,/**webjars/**,/**/swagger-resources/configuration/ui/**";
		paths.add("/user/login");
		paths.add("/user/logout");
		paths.add("/**/*.jpg");
		paths.add("/**/*.css");
		paths.add("/user/getImage");
		if(!KiiikContants.PROD.equals(env.getProperty("spring.profiles.active"))){
			String[] pathArr=pathsstrs.split(",");
			for(int i=0;i<pathArr.length;i++){
				paths.add(pathArr[i]);
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//过滤器掉静态资源  
		Object auth = ((HttpServletRequest)request).getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    	if(auth==null&&!isAccess( ((HttpServletRequest)request).getRequestURI())){
	        ResultBean<String> result = new ResultBean<String>();
	        result.sessionTimeOut("会话过期，请重新登陆！");
	        ResponseUtils.writeResult((HttpServletResponse)response, result);
	        return ;
    	}
    	chain.doFilter(request, response);
	}

	private boolean isAccess(String uri) {
		for(String path:paths){
			if(antPathMatcher.match(path, uri)){//准入
				return true;//不需要session也能访问
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		
	}
}
