package com.cyb;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.h2.H2Manager;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
//// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
@RestController
public class MyBootStarter  {
//extends SpringBootServletInitializer
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(MyBootStarter.class);  
    }  */
	public  static ConfigurableApplicationContext context;
	
	public static ConfigurableApplicationContext getContext() {
		return context;
	}

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
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				  ErrorPage error401Page = new ErrorPage(HttpStatus.FORBIDDEN, "/exception/403.jsp");
		          ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/exception/404.jsp");
		          ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/exception/500.jsp");
		          container.addErrorPages(error401Page, error404Page, error500Page);
			}
	    };
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
