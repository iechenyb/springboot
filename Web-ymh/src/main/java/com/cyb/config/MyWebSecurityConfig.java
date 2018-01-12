package com.cyb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
/**
 *作者 : iechenyb<br>
 *类描述:hasrole不生效原因
 * http://blog.csdn.net/sinat_28454173/article/details/52312828 <br>
 *创建时间: 2017年12月13日
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MyWebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	       //关闭csrf 防止循环定向
        	    http.cors().
        	    and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()//.hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers("/users/toLogin").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/logout").permitAll()
                .antMatchers("/users/toRedirect").permitAll()
                .antMatchers( "/login.jsp").permitAll()
                .antMatchers( "/users/exit").permitAll()
                .antMatchers("/sw/**").permitAll()
                .antMatchers("/plan/**").hasAnyRole("USER","ADMIN")//.permitAll()
                .antMatchers("/net/**").permitAll()
                .antMatchers("/common/**").permitAll()
                .antMatchers("/visitor1/home").hasAnyRole("USER","ADMIN")
                .antMatchers("/visitor1/admin").hasRole("ADMIN")
                .antMatchers("/visitor1/user").hasRole("USER")
                //.antMatchers("/visitor1/user").hasIpAddress("127.0.0.1")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
                //开启cookie保存用户数据
                .and().rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                .key("iechenyb")
                .and().formLogin().loginPage("/users/toLogin").failureUrl("/login?error").permitAll()
                //设置注销成功后跳转页面，默认是跳转到登录页面
                /*.and().logout() //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/users/logout"). logoutSuccessUrl("/logout?exit").permitAll()*/
                ;
        	   //http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    /*@Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    
    @Bean
    @Primary
    public DefaultWebInvocationPrivilegeEvaluator customWebInvocationPrivilegeEvaluator() {
        return new DefaultWebInvocationPrivilegeEvaluator(myFilterSecurityInterceptor);
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring()
        .antMatchers("/lhmj/**")  .antMatchers("/static/**")
        .antMatchers("/css/**").antMatchers("/phone/**")
        .antMatchers("/js/**").antMatchers("/exception/**")
        .antMatchers("/img/**")
        .antMatchers("/css/**")
        .antMatchers("/login.jsp")
        .antMatchers("/druid/**","/druid/index.html")
        .antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html","/webjars/**",
                "/swagger-resources/configuration/ui")
        ;
        //定义具体的url访问权限
       /* web.securityInterceptor(myFilterSecurityInterceptor);
        web.privilegeEvaluator(customWebInvocationPrivilegeEvaluator());*/
    }
    
}
