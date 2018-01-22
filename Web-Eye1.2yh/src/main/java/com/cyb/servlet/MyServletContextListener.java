package com.cyb.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cyb.contants.PlanContants;
import com.cyb.h2.H2Manager;

@WebListener
public class MyServletContextListener implements ServletContextListener {
	/* @Autowired
	  private IAuthService authService;*/
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //PlanContants.context = sce.getServletContext().getRealPath("/");
        //System.out.println("ServletContex初始化"+PlanContants.context);
        //System.out.println("realPath:blank is "+sce.getServletContext().getRealPath(""));
        //System.out.println("realPath:/ is "+PlanContants.context);
        PlanContants.context = "c:/data/plan/";
        //可以直接使用工厂对象
        WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())
        .getAutowireCapableBeanFactory().autowireBean(this);
        H2Manager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContex销毁");
        H2Manager.stop();
    }

}