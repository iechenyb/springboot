package com.kiiik.config.security;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kiiik.config.jwt.JwtAuthenticationTokenFilter;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:14:07
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ApplicationWebSecurityConfig  extends WebSecurityConfigurerAdapter {
	Log log = LogFactory.getLog(ApplicationWebSecurityConfig.class);
	
	@Autowired
	AuthFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.headers().frameOptions().disable();
        // CsrfConfigurer
        //http.csrf().disable();
        // ExpressionInterceptUrlRegistry
        /*http.authorizeRequests().anyRequest()
        .authenticated().anyRequest()
        .fullyAuthenticated();*/
        // acm cas策略
        // 对logout请求放行
        //http.logout().permitAll();
        //http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
		/*http
			.authorizeRequests()
			.antMatchers("/")
			//.authenticated()
			.and()
			.authorizeRequests()
			.antMatchers("/user/login","user/toLogin","user/infor")
			.permitAll()
			.and()
			.formLogin()
			.loginPage("/user/toLogin");*/
       /* http.cors().
	    and().csrf().disable()
	    .authorizeRequests()
        .antMatchers("/user/toLogin").permitAll()
        .antMatchers("/user/login").permitAll()
        .antMatchers("/users/logout").permitAll()
        .antMatchers("/users/exit").permitAll()
        .antMatchers("/users/infor").permitAll()
        .anyRequest().authenticated()
       // .and().formLogin().loginPage("/login")
        //设置默认登录成功跳转页面
        //.defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
        //开启cookie保存用户数据
        .and().rememberMe()
        //设置cookie有效期
        .tokenValiditySeconds(60 * 60 * 24 * 7)
        //设置cookie的私钥
        .key("iechenyb")
        .and().formLogin().loginPage("/users/toLogin")
        .loginProcessingUrl("/user/login")
       // .failureUrl("/login?error").permitAll()
        //设置注销成功后跳转页面，默认是跳转到登录页面
        .and().logout() //默认注销行为为logout，可以通过下面的方式来修改
        .logoutUrl("/users/logout"). logoutSuccessUrl("/logout?exit").permitAll()
        ;*/
	    http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	    http.authorizeRequests()
	    .antMatchers("/user/toLogin","/user/login","/user/infor").permitAll()
	    .anyRequest().authenticated();
	    http.formLogin()          // 定义当需要用户登录时候，转到的登录页面。
	      .loginPage("/user/toLogin")      // 设置登录页面
	      .permitAll()
	      .loginProcessingUrl("/user/login") // 自定义的登录接口
	      .permitAll()
	      .successHandler(
	    		  new AuthenctiationSuccessHandler()
	    		  /*new AuthenticationSuccessHandler() {
	            @Override
	            public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
	                    throws IOException, ServletException {
	                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	                if (principal != null && principal instanceof UserDetails) {
	                    UserDetails user = (UserDetails) principal;
	                    System.out.println("loginUser:"+user.getUsername());
	                    //维护在session中
	                    arg0.getSession().setAttribute("userDetail", user);
	                    arg1.sendRedirect("/");
	                } 
	            }
	        }*/)
	      .and()
	      .authorizeRequests()    // 定义哪些URL需要被保护、哪些不需要被保护
	      .antMatchers("/user/toLogin").permitAll()   // 设置所有人都可以访问登录页面
	      .antMatchers("/auth/**").permitAll()
	      .anyRequest()        // 任何请求,登录后可以访问
	      .authenticated()
	      .and()
	      .csrf().disable();     // 关闭csrf防护
	    // 添加JWT filter
      http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
      
	}
	@Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
	@Bean
    @Primary
    public DefaultWebInvocationPrivilegeEvaluator customWebInvocationPrivilegeEvaluator() {
        return new DefaultWebInvocationPrivilegeEvaluator(myFilterSecurityInterceptor);
    }
	@Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring()
        .antMatchers("/user/login")
        .antMatchers("/user/toLogin")
        .antMatchers("/auth/**")
        //版本二的登录页面打开
        .antMatchers("/js/**")
        .antMatchers("/exception/**")
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
	@Autowired
	AuthUserDetailsService myUserDetailsService;
	@Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
	 @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
        .withUser("admin").password("admin").roles("ADD","DELETE")*/
	//	auth.userDetailsService(myUserDetailsService);
	 auth.authenticationProvider(customAuthenticationProvider);
    }
}
