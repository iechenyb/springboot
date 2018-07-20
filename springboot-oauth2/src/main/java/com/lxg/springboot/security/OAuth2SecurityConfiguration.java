package com.lxg.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.lxg.springboot.config.MyUserDetailsService;

/**
 * security配置
 * 
 * @author lxg
 *
 * 2017年2月17日上午11:13:55
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private RedisConnectionFactory redisConnection;
	
	@Autowired
	private MyFilterSecurityInterceptor mySecurityFilter;

	@Bean
	public MyUserDetailsService myUserDetailsService(){
		return new MyUserDetailsService();
	}
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth
			.userDetailsService(myUserDetailsService());
			//.passwordEncoder(new Md5PasswordEncoder())
    }
	@Autowired
	CustomAccessDecisionManager decisionManager;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//.addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)
		.formLogin().loginProcessingUrl("/login").loginPage("/toLogin")// login为默认的登录逻辑
		.defaultSuccessUrl("/oauth/confirm_access")
		.failureForwardUrl("/refuse")
		.permitAll().and()
		.anonymous().and()
	  	.authorizeRequests()
	  	//.accessDecisionManager(decisionManager)
	  	.antMatchers("/oauth/token","/oauth/check_token","/oauth/authorize","/login/","/userLogin").permitAll();
	  //	.anyRequest().authenticated();
		
    }
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    // @formatter:off
    endpoints.authenticationManager(authenticationManagerBean())
    .userDetailsService(myUserDetailsService());
    // @formatter:on
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnection);
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}
	
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	
}
