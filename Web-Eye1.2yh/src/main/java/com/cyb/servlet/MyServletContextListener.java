package com.cyb.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cyb.config.SystemConfigSettings;
import com.cyb.contants.PlanContants;
import com.cyb.h2.H2Manager;
import com.cyb.utils.SpringUtils;

@WebListener
public class MyServletContextListener implements ServletContextListener {
	/* 
	 * @Autowired
	  private IAuthService authService;
	  不同的创建容器，不能正常注入，报空指针异常！
	  */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //可以直接使用工厂对象
        WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())
        .getAutowireCapableBeanFactory().autowireBean(this);
        //PlanContants.context = sce.getServletContext().getRealPath("/");
        SystemConfigSettings setting = (SystemConfigSettings) SpringUtils.getObjectFromApplication(sce.getServletContext(), SystemConfigSettings.class);
        PlanContants.context = setting.getContext();
        System.out.println("realPath:/ is "+PlanContants.context);
        H2Manager.initServer();
        H2Manager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContex销毁");
        H2Manager.stop();
    }
}