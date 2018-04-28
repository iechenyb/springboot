package com.cyb.access;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
import org.springframework.web.client.RestTemplate;

import com.cyb.po.MyUser;
import com.cyb.validate.bean.ValidBean;
public class UserContext {
	Log log = LogFactory.getLog(UserContext.class);
	private static ThreadLocal<MyUser> userHolder = new ThreadLocal<MyUser>();
	private static ThreadLocal<ValidBean> beanHolder = new ThreadLocal<ValidBean>();
	public static void setUser(MyUser user){
		userHolder.set(user);
	}
	public static MyUser getUser(){
		return userHolder.get();
	}
	
	public static void setUser(ValidBean user){
		beanHolder.set(user);
	}
	
	public static ValidBean getUserBean(){
		return beanHolder.get();
	}
}
