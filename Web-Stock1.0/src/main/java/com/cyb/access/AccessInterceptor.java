package com.cyb.access;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cyb.po.MyUser;
import com.cyb.redis.RedisTempalte;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter{
	Log log = LogFactory.getLog(AccessInterceptor.class);
	
	@Autowired
	RedisTempalte redisTempalte;
	
	@Autowired
	Environment env;
	
	int index=3;
	
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle "+request.getRequestURL());
		HttpSession session = request.getSession();
		String url_key = "DDOS:"+session.getId()+":"+request.getRequestURI();
		
		/*if(request.getSession().getAttribute("user")!=null){
			UserContext.setUser((ValidBean)request.getSession().getAttribute("user"));
			System.out.println("进入前置处理器 name="+UserContext.getUserBean().getName()+" age="+UserContext.getUserBean().getAge());
		}*/
		MyUser user = null;//登录用户，测试环境默认不登录
		if(handler instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod) handler;
			if(method.hasMethodAnnotation(AccessLimit.class)){//普通注解
				//获取访问限制注解
				AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
				int seconds  = accessLimit.seconds();
				int maxCount = accessLimit.maxCount();
				boolean needLogin = accessLimit.needLogin();
				accessCheck(seconds,maxCount,needLogin,url_key);
			}else if(method.hasMethodAnnotation(AccessLimitEl.class)){//读取el
				AccessLimitEl accessLimit = method.getMethodAnnotation(AccessLimitEl.class);
				int seconds  = Integer.valueOf(env.getProperty(accessLimit.seconds()));
				int maxCount = Integer.valueOf(env.getProperty(accessLimit.maxCount()));
				boolean needLogin = Boolean.valueOf(env.getProperty(accessLimit.needLogin()));
				accessCheck(seconds,maxCount,needLogin,url_key);
			}else{
				 return true;//没有访问限制注解，则放行
			}
		}
		return true;
	}
	
	//设置用户某请求的访问量，进行访问限制
	private boolean accessCheck(int seconds,int maxCount,boolean needLogin,String url_key) throws Exception{
		if(needLogin){
			throw new Exception("你尚未登录，请登录！");
		}else{//次数限制测试
			if(redisTempalte.exists(index,url_key)){
				int times = Integer.valueOf(redisTempalte.get(index, url_key));
				if(times>=maxCount){//一分钟之内达到6次即拒绝访问
					//访问次数过于频繁，异常提示
					throw new AccessRejectException("访问过于频繁！");//抛出异常，统一处理
				}else{
					redisTempalte.incr(index, url_key);
					return true;
				}
			}else{
				redisTempalte.set(index, url_key,"0",seconds);//默认有效期1分钟
				return true;
			}
		}
	}
	@SuppressWarnings("unused")
	private static boolean isStaticResources(String uri) {
	    Pattern pattern = Pattern.compile("http://(?!(\\.jpg|\\.png)).+?(\\.jpg|\\.png)");
	    Matcher matcher = pattern.matcher(uri);
	    return matcher.find();
	}
	public void render(HttpServletResponse resp){
		try {
			OutputStream o = resp.getOutputStream();
			o.write(new String("需要登录！").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle "+request.getRequestURL());
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion "+request.getRequestURL());
		super.afterCompletion(request, response, handler, ex);
	}
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("afterConcurrentHandlingStarted "+request.getRequestURL());
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
}
