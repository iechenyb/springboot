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
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.kiiik.pub.bean.R;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.ResponseUtils;
import com.kiiik.web.property.KiiikProperties;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月3日
 */
@Component
public class KiiikCustomizationFilter implements Filter {
	Log log = LogFactory.getLog(KiiikCustomizationFilter.class);
	public static List<String> paths= new ArrayList<String>();
	AntPathMatcher antPathMatcher = new AntPathMatcher();
	boolean checkSession = true;//前后端分离，未登录时重定向（关键问题解决思路）
	
	@Autowired
	KiiikProperties kiiik;
	static String[] pathsstrs =new String[]{"/v2/api-docs","/v2/api-docs","/swagger-resources",
			"/**/v2/api-docs/**",
			"/**/configuration/ui/**","/**/swagger-resources/**","/**/configuration/security/**",
			"/swagger-ui.html","/**/webjars/**/*.*","/**/swagger-resources/configuration/ui/**"};
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		paths.add("/user/login");
		paths.add("/user/logout");
		paths.add("/rsa/**");
		paths.add("/");
		paths.add("/favicon.ico");
		paths.add("/**/*.jpg");//:
		paths.add("/**/*.css");
		paths.add("/user/getImage");
		paths.add("/we/**");
		paths.add("/fonts/**");
		paths.add("/css/**");
		paths.add("/js/**");
		paths.add("/img/**");
		paths.add("/index.html");
		//屏蔽swagger-ui接口页面
		if(!KiiikContants.PROD.equals(kiiik.environment)){
			for(int i=0;i<pathsstrs.length;i++){
				paths.add(pathsstrs[i]);
			}
		}
		//开发环境免登陆
        if(KiiikContants.DEV.equals(kiiik.environment)){
        	for(int i=0;i<KiiikContants.reqs.length;i++){
        		paths.add("/"+KiiikContants.reqs[i]+"/**");
        	}
        }
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//过滤器掉静态资源  
		
		if(checkSession){
			Object auth = ((HttpServletRequest)request).getSession().getAttribute(KiiikContants.SPRING_CONTEXT_KEY);
	    	if(auth==null&&!isAccess( ((HttpServletRequest)request).getRequestURI())){
		        R<String> result = new R<String>();
		        result.sessionTimeOut("会话过期，请重新登陆!");
		        ResponseUtils.writeResult((HttpServletResponse)response, result);
		        return ;
	    	}
    	}
    	chain.doFilter(request, response);
	}

	private boolean isAccess(String uri) {
		for(String path:paths){
			if(antPathMatcher.match(path, uri)){//准入
				System.err.println("match:"+path);
				return true;//不需要session也能访问
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		
	}
}
