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
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle "+request.getRequestURL());
		HttpSession session = request.getSession();
		String url_key = "DDOS:"+session.getId()+":"+request.getRequestURI();
		int index=3;
		/*if(request.getSession().getAttribute("user")!=null){
			UserContext.setUser((ValidBean)request.getSession().getAttribute("user"));
			System.out.println("进入前置处理器 name="+UserContext.getUserBean().getName()+" age="+UserContext.getUserBean().getAge());
		}*/
		if(handler instanceof HandlerMethod){
			if(redisTempalte.exists(index,url_key)){
				int times = Integer.valueOf(redisTempalte.get(index, url_key));
				if(times>5){//一分钟之内达到6次即拒绝访问
					//访问次数过于频繁，异常提示
					throw new Exception("访问过于频繁！");//抛出异常，统一处理
				}else{
					redisTempalte.incr(index, url_key);
				}
			}else{
				redisTempalte.set(index, url_key,"0");//默认有效期1分钟
			}
			/*HandlerMethod hm = (HandlerMethod) handler;
			AccessLimit al = hm.getMethodAnnotation(AccessLimit.class);
			if(al==null){
				return true;
			}*/
			/*if(request.getRequestURI().contains("common/login")){//登录直接过
				return true;
			}
			Object obj = request.getSession().getAttribute("user");
			MyUser user = null;
			if(obj != null){
				user = (MyUser) obj;
			}
			System.out.println("设置的模拟用户信息"+user.getUser_id());
			//获取用户并保存
			UserContext.setUser(user);//后续方法都能获取当前对象
			 */			/*
			 * 
			设置用户某请求的访问量，进行访问限制
			int seconds  = al.seconds();
			int maxCount = al.maxCount();
			boolean needLogin = al.needLogin();
			
			String key = user.getUser_id()+"_"+request.getRequestURI();		
			if(needLogin){
				if(user==null){
					render(response);
				}else{
					
				}
			}*/
		}
		return true;
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
