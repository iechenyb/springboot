package com.cyb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cyb.config.cas.CasAccessDecisionManager;
import com.cyb.config.cas.CasFilterSecurityInterceptor;
import com.cyb.h2.H2Manager;
import com.cyb.log.LogRule;
import com.cyb.log.MyLog;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@SuppressWarnings("deprecation")
//// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
@RestController
@EnableAsync
//@EnableDiscoveryClient
public class MyBootStarter extends WebMvcConfigurerAdapter {
//extends SpringBootServletInitializer
	
	@Autowired
	LogRule logRule;
	
	@PostConstruct
	public void getLogRule(){
		logRule.saveExceptionLog(new MyLog());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(MyBootStarter.class);  
    }  */
	/*public  static ConfigurableApplicationContext context;
	
	public static ConfigurableApplicationContext getContext() {
		return context;
	}*/

	public static void main(String[] args) {
		 H2Manager.start();
		SpringApplication.run(MyBootStarter.class, args);
	}

	 //其中 dataSource 框架会自动为我们注入
    @Bean
    public PlatformTransactionManager txManagerDataSource(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    /**
     * 注入sessionfatory
     * @return
     */
	@SuppressWarnings("deprecation")
	@Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

	/*@Override
	public void run(String... arg0) throws Exception {
		System.err.println("服务调用者------>>启动完毕");
	}*/
	
	/**
     * 自定义异常页
     * 
     */
	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
	    	//jar包使用
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				 ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/exception/403.jsp");
		          ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/exception/404.jsp");
		          ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/exception/500.jsp");
		          container.addErrorPages(error403Page, error404Page, error500Page);
			}
	    };
	}
	@Resource
	private CasAccessDecisionManager casAccessDecisionManager;

	@Bean
	public CasFilterSecurityInterceptor getXXX() {
		CasFilterSecurityInterceptor x = new CasFilterSecurityInterceptor();
		System.out.println("CasAccessDecisionManager:"+casAccessDecisionManager);
		x.setCasAccessDecisionManager(casAccessDecisionManager);
		return x;
	}
	/*@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

       return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
            container.addErrorPages(error401Page, error404Page, error500Page);
       });
    }*/
    /**/
}
