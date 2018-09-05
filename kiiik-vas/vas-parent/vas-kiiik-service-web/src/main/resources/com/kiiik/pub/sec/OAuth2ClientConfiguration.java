package com.kiiik.pub.sec;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年7月20日
 */
//@Configuration
public class OAuth2ClientConfiguration {
	Log log = LogFactory.getLog(OAuth2ClientConfiguration.class);

	@Bean
	public OAuth2ClientContextFilter oauth2ClientContextFilter() {
		OAuth2ClientContextFilter filter = new OAuth2ClientContextFilter();
		return filter;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	protected AccessTokenRequest accessTokenRequest(@Value("#{request.parameterMap}") Map<String, String[]> parameters,
			@Value("#{request.getAttribute('currentUri')}") String currentUri) {
		DefaultAccessTokenRequest request = new DefaultAccessTokenRequest(parameters);
		request.setCurrentUri(currentUri);
		return request;
	}

	@Configuration
	protected static class OAuth2ClientContextConfiguration {

		@Resource
		@Qualifier("accessTokenRequest")
		private AccessTokenRequest accessTokenRequest;

		@Bean
		@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
		public OAuth2ClientContext oauth2ClientContext() {
			return new DefaultOAuth2ClientContext(accessTokenRequest);
		}

	}
}
