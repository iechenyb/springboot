package com.kiiik.web.feign.config;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.utils.RequestUtils;
import com.kiiik.web.feign.context.UserInfoContext;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月3日
 */
public class TransmitUserInfoFilter  implements Filter {
	Log log = LogFactory.getLog(TransmitUserInfoFilter.class);
	 public TransmitUserInfoFilter() {
	    }
	    public void init(FilterConfig filterConfig) throws ServletException {
	    }
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	       this.initUserInfo((HttpServletRequest)request);
	       chain.doFilter(request,response);
	    }

	    private void initUserInfo(HttpServletRequest request){
	       /* String userJson = request.getHeader(UserInfoContext.KEY_USERINFO_IN_HTTP_HEADER);
	        if (!StringUtils.isEmpty(userJson)) {
	            try {
	                userJson = URLDecoder.decode(userJson,"UTF-8");
	                SessionUser userInfo = (SessionUser) JSON.parseObject(userJson,SessionUser.class);
	                //将UserInfo放入上下文中
	                System.out.println("TransmitUserInfoFilter "+userJson);
	                UserInfoContext.setUser(userInfo);
	            } catch (UnsupportedEncodingException e) {
	               log.error("init userInfo error",e);
	            }
	        }
	        TransmitUserInfoFilter:http-nio-80-exec-7-----com.kiiik.pub.bean.SessionUser@3ef9fa75
			RequestInterceptor....hystrix-BASE-SERVICE-1-----null
	        */
	    	try{
		    	System.out.println("TransmitUserInfoFilter:"+Thread.currentThread().getName()+"-----"+RequestUtils.getSessionUser(request));
		    	UserInfoContext.setUser(RequestUtils.getSessionUser(request));//
	    	}catch(Exception e){
	    		
	    	}
	    }

	    @Override
	    public void destroy() {
	    }
}
