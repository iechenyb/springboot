package com.cyb.listener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.cyb.contants.PlanContants;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月6日
 */
@WebListener  
public class OnLineBroker implements HttpSessionListener {  
    public AtomicInteger count= new AtomicInteger(0);//记录session的数量  
    //监听session的创建,synchronized 防并发bug  
    public synchronized void sessionCreated(HttpSessionEvent arg0) {  
        System.out.println("【HttpSessionListener监听器】count++  增加");  
        //相同用户名不同浏览器访问算是一个客户
        ServletContext context = arg0.getSession().getServletContext();  
        if (context.getAttribute("count")==null) {
        	context.setAttribute("count", new AtomicInteger(0));  
        }else {  
        	AtomicInteger count = (AtomicInteger) context.getAttribute("count");  
	        context.setAttribute("count",   count.getAndIncrement());  
        }  
    }  
    @Override  
    public synchronized void sessionDestroyed(HttpSessionEvent arg0) {//监听session的撤销  
        System.out.println("【HttpSessionListener监听器】count--  减少");  
        //arg0.getSession().getServletContext().setAttribute("count", count);  
        ServletContext context = arg0.getSession().getServletContext();  
        if (context.getAttribute("count")==null) {  
        	context.setAttribute("count", new AtomicInteger(0));  
        }else {  
        	AtomicInteger count = (AtomicInteger) context.getAttribute("count");  
        	if (count.get()<1) {  
        		count = new AtomicInteger(0);  
        	}
        }  
        context.setAttribute("count", count.getAndDecrement());  
        HttpSession session = arg0.getSession();  
        String name = (String) session.getAttribute("userid");  
        PlanContants.onlineUser.remove(name);
    }  
}
