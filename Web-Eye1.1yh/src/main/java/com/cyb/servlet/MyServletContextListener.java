package com.cyb.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.cyb.contants.PlanContants;
import com.cyb.h2.H2Manager;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PlanContants.context = sce.getServletContext().getRealPath("/");
        System.out.println("ServletContex初始化"+PlanContants.context);
        PlanContants.context = "d:/data/plan/";
        H2Manager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContex销毁");
        H2Manager.stop();
    }

}