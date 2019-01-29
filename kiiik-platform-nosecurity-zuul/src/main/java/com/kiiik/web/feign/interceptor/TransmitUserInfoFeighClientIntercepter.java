package com.kiiik.web.feign.interceptor;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.kiiik.pub.bean.SessionUser;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.RequestUtils;
import com.kiiik.web.feign.context.UserInfoContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>gn
 *创建时间: 2019年1月3日
 *feign调用session丢失解决方案 https://blog.csdn.net/zl1zl2zl3/article/details/79084368
 */
public class TransmitUserInfoFeighClientIntercepter implements RequestInterceptor {
	Log log = LogFactory.getLog(TransmitUserInfoFeighClientIntercepter.class);

	    public TransmitUserInfoFeighClientIntercepter() {
	    }

	    @SuppressWarnings("deprecation")
		@Override
	    public void apply(RequestTemplate requestTemplate) {
	    	System.out.println(UserInfoContext.getUser());
            HttpServletRequest request = getRequest();//null
            if(request!=null){
	            System.out.println("request"+request);
	            Object securityContext = request.getSession().getAttribute(KiiikContants.SPRING_CONTEXT_KEY);
	            System.out.println("登录信息："+securityContext);
	            if(securityContext!=null){
	            	securityContext = (SecurityContext)securityContext;
	            	//从应用上下文中取出user信息，放入Feign的请求头中
			        SessionUser user = RequestUtils.getSessionUser(request);//UserInfoContext.getUser();
			        //Request request = requestTemplate.request();
			        System.out.println("RequestInterceptor...."+Thread.currentThread().getName()+"-----"+user);
			        requestTemplate.header(UserInfoContext.KEY_USERINFO_IN_HTTP_HEADER, URLEncoder.encode(JSON.toJSONString(user)));
	            }
            }
	    }
	    
	    HttpServletRequest  getRequest(){
	    	try{
	    	ServletRequestAttributes attributes = (ServletRequestAttributes) 
	    			RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            return request;
            }catch(Exception e){
            	return null;
            }
	    }
}
