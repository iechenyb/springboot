package com.cyb.utils;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
public class SpringUtils {
	Log log = LogFactory.getLog(SpringUtils.class);
	public static String ProjectPathAtMiddleWare="";
	static ServletContext context ;
	public static synchronized String getWebPath(){
		if(ProjectPathAtMiddleWare.isEmpty()){
			synchronized (ProjectPathAtMiddleWare) {
				ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
				ProjectPathAtMiddleWare = context.getRealPath("/");
				//System.out.println("getContextPath:"+context.getContextPath());
			}
		}
		 return ProjectPathAtMiddleWare;
	}
	/** 
     * 通过WebApplicationContextUtils 得到Spring容器的实例。根据bean的名称返回bean的实例。 
     * @param servletContext  ：ServletContext上下文。 
     * @param beanName  :要取得的Spring容器中Bean的名称。 
     * @return 返回Bean的实例。 
     */  
	public static Object getObjectFromApplication(ServletContext servletContext,String beanName){  
        //通过WebApplicationContextUtils 得到Spring容器的实例。  
        ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        //返回Bean的实例。  
        return application.getBean(beanName);  
    } 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getObjectFromApplication(ServletContext servletContext,Class beanName){  
        //通过WebApplicationContextUtils 得到Spring容器的实例。  
        ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        //返回Bean的实例。  
        return application.getBean(beanName);  
    } 
}
