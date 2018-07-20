package com.lxg.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		/*
		 * http.csrf().disable(). anonymous().disable()
		 * .requestMatchers().anyRequest()
		 * .antMatchers("/user/").and().authorizeRequests().and()
		 * .exceptionHandling() .accessDeniedHandler(new
		 * OAuth2AccessDeniedHandler()).accessDeniedPage("/refuse");
		 */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().requestMatchers()
				.antMatchers("/user/**").antMatchers("/other/**").and().authorizeRequests() //需要授权
				.antMatchers("/admin/**").authenticated();//可以直接访问
	}

}