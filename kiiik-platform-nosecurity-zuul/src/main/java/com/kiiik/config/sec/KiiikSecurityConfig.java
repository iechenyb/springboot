package com.kiiik.config.sec;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class KiiikSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Resource
	 private SessionRegistry sessionRegistry;
	 @Autowired
	 AuthFilterSecurityInterceptor authFilter;
	 @Autowired
	 KiiikCustomizationFilter firstFilter;
	 @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(firstFilter, FilterSecurityInterceptor.class);
        http
        .authorizeRequests()
        .antMatchers("/user/login").permitAll()
        .antMatchers("/user/toLogin").permitAll()
        .antMatchers("/static/**").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/user/getImage").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/bootstrap/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .antMatchers("/favicon.ico").permitAll()
        .anyRequest().authenticated()
        .and().formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
		          .loginPage("/user/toLogin") // usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/") // 设置登录页面
			      .permitAll()
			     .loginProcessingUrl("user/login") // 自定义的登录接口
			      .permitAll()
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                /*.antMatchers("/demo/helloworld").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/demo/test1").access("hasRole('ROLE_TEST1')")*/
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated()
                .and()
                .csrf().disable();          // 关闭csrf防护logout可以使用GET访问;
       /* http.sessionManagement().
        maximumSessions(1).
        expiredSessionStrategy(ajaxSessionInformationExpiredStrategy);*/
      //session并发控制过滤器
       // http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry,sessionInformationExpiredStrategy()),ConcurrentSessionFilter.class);
    } 
	 @Autowired
	 AjaxSessionInformationExpiredStrategy ajaxSessionInformationExpiredStrategy;

    @SuppressWarnings("unused")
	private AjaxSessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
			return ajaxSessionInformationExpiredStrategy;
	}
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

	//password加密兼容问题
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring()
        .antMatchers("/auth/**")
        //版本二的登录页面打开
        .antMatchers("/favicon.ico")
        .antMatchers("/static/**")
        .antMatchers("/exception/**")
        .antMatchers("/static/**")
        .antMatchers("/druid/**","/druid/index.html")
        .antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html","/webjars/**",
                "/swagger-resources/configuration/ui")
        ;
    }
}

