package com.cyb.utils;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
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
}
