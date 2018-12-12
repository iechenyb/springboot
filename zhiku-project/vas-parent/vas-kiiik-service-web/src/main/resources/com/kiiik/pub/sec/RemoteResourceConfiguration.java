package com.kiiik.pub.sec;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年7月20日
 */
//@Configuration
//@EnableOAuth2Client
public class RemoteResourceConfiguration {
	Log log = LogFactory.getLog(RemoteResourceConfiguration.class);

	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
		return new OAuth2RestTemplate(aaa(), oauth2ClientContext);// OAuth2ProtectedResourceDetails
	}

	public OAuth2ProtectedResourceDetails aaa() {
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();

		resource.setAuthenticationScheme(AuthenticationScheme.form);

		resource.setClientAuthenticationScheme(AuthenticationScheme.form);

		resource.setClientId("my-trusted-client");

		resource.setClientSecret("secret");

		resource.setUserAuthorizationUri("https://open.weixin.qq.com/connect/oauth2/authorize");

		resource.setAccessTokenUri("https://api.weixin.qq.com/sns/oauth2/access_token");

		resource.setScope(Arrays.asList("snsapi_userinfo"));
		return resource;
	}
}
