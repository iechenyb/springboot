package com.cyb.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers("/users/toLogin").permitAll()
                .antMatchers("/users/toRedirect").permitAll()
                .antMatchers( "/login.jsp").permitAll()
                .antMatchers( "/users/exit").permitAll()
                .antMatchers("/sw/**").permitAll()
                .antMatchers("/net/**").permitAll()
                .antMatchers("/common/**").permitAll()
              //  .antMatchers("/druid/**","/druid/index.html").permitAll()
                //role should not start with 'ROLE_' since it is automatically inserted. Got 'ROLE_ADMIN'
                .antMatchers("/visitor1/home").hasAnyRole("USER","ADMIN")
                .antMatchers("/visitor1/admin").hasRole("ADMIN")
                .antMatchers("/visitor1/user").hasRole("USER")
                .anyRequest().authenticated()
               // .antMatchers("/druid/**","/druid/index.html").permitAll()
                ;
                /*
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/toRedirect").permitAll() ;
		       /* http.authorizeRequests()
		        .antMatchers("/home").permitAll()
		        //其他地址的访问均需验证权限
		        .anyRequest().authenticated()
		        .and()
		        .formLogin()
		        .loginPage("/login")  //指定登录页是"/login"
		        .defaultSuccessUrl("/list")  //登录成功后默认跳转到"list"
		        .permitAll()
		        .and()
		        .logout()
		        .logoutSuccessUrl("/home")  //退出登录后的默认url是"/home"
		        .permitAll();*/
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring()
        .antMatchers("/lhmj/**")
        .antMatchers("/css/**")
        .antMatchers("/js/**")
        .antMatchers("/img/**")
        .antMatchers("/css/**")
        .antMatchers("/login.jsp")
        .antMatchers("/druid/**","/druid/index.html")
        .antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html","/webjars/**",
                "/swagger-resources/configuration/ui")
        ;
    }
    
}
