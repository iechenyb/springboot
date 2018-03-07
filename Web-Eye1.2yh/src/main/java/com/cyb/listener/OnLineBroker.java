package com.cyb.listener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.cyb.contants.PlanContants;
import com.cyb.date.DateUtil;
import com.cyb.log.LogRule;
import com.cyb.utils.SpringUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月6日
 */
@WebListener  
public class OnLineBroker implements HttpSessionListener {  
    public static AtomicInteger count= new AtomicInteger(0);//记录session的数量  
    public static ThreadLocal<AtomicInteger> counter = new ThreadLocal<AtomicInteger>(){
    	protected AtomicInteger initialValue() {
            return new AtomicInteger(0);
        };
    };
    //监听session的创建,synchronized 防并发bug  
    public synchronized void sessionCreated(HttpSessionEvent arg0) {  
        System.out.println("【HttpSessionListener监听器】count++  增加");  
        //相同用户名不同浏览器访问算是一个客户
        HttpSession session = arg0.getSession();  
        counter.get().incrementAndGet();
        if (session.getAttribute("count")==null) {
        	session.setAttribute("count", new AtomicInteger(0));  
        }else {  
        	AtomicInteger count = (AtomicInteger) session.getAttribute("count");
        	count.getAndIncrement();  
        	session.setAttribute("count",   count);  
        }  
    }  
    @Override  
    public synchronized void sessionDestroyed(HttpSessionEvent arg0) {//监听session的撤销  
        System.out.println("【HttpSessionListener监听器】count--  减少");  
        counter.get().decrementAndGet();
        HttpSession session = arg0.getSession();  
        if (session.getAttribute("count")==null) {  
        	session.setAttribute("count", new AtomicInteger(0));  
        }else {  
        	AtomicInteger count = (AtomicInteger) session.getAttribute("count");  
        	if (count.get()<1) {  
        		count = new AtomicInteger(0);  
        	} else{
        		count.getAndDecrement();
        	}
        } 
        LogRule logRule = (LogRule) 
        		SpringUtils.getObjectFromApplication(arg0.getSession().getServletContext(), LogRule.class);  
        String name = "游客";
        if( session.getAttribute("userid")!=null){
         name = (String) session.getAttribute("userid");  
        }
        PlanContants.onlineUser.remove(name);
        logRule.saveSessionLog(DateUtil.timeToMilis()+"	"+arg0.getSession().getId()+" ["+name+"] 退出系统！");
    }  
    
}
