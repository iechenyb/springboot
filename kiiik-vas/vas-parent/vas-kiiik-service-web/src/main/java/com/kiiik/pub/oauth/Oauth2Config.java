package com.kiiik.pub.oauth;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.state.DefaultStateKeyGenerator;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.kiiik.pub.aop.VasControllerAop;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月20日
 */

/*@EnableOAuth2Client
@Configuration
@Component*/
public class Oauth2Config{
	
	static Log logger = LogFactory.getLog(VasControllerAop.class);
	private static String location = "classpath:config/*/oauth.properties";
	
	private static Map<String,String> oauthInfo = new HashMap<String,String>();
	
	@Autowired
	private OAuth2ClientContext oauth2Context;
	
	/**
	 * 获取配置文件信息
	 */
	static{
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources;
		try {
			resources = patternResolver.getResources(location);
			location = resources[0].getFile().getAbsolutePath();
			logger.info("location" + location);
			Properties props = new Properties();  
	        try {  
	        	if(location.contains("dev")){
	        		props = PropertiesLoaderUtils.loadAllProperties("config/dev/oauth.properties");
	        	}else if(location.contains("test")){
	        		props = PropertiesLoaderUtils.loadAllProperties("config/test/oauth.properties");
	        	}else if(location.contains("production")){
	        		props = PropertiesLoaderUtils.loadAllProperties("config/production/oauth.properties");
	        	}
	            for(Object key:props.keySet()){  
	            	logger.warn(key + " : " +  (String)props.get(key));
	            	oauthInfo.put((String) key, (String)props.get(key));
	            }  
	        } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	        }  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
	
	@Bean
	public AccessTokenRequest accessTokenRequest(){
		AccessTokenRequest defaultAccessTokenRequest = new DefaultAccessTokenRequest();
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> headerList=new ArrayList<String>();
		headerList.add("Basic " + oauthInfo.get("public_key"));
        headers.put("Authorization", headerList);
        defaultAccessTokenRequest.setHeaders(headers);
        defaultAccessTokenRequest.setCurrentUri(oauthInfo.get("redirect_uri"));
	    return defaultAccessTokenRequest;
	}
	
	@Bean
	public AuthorizationCodeResourceDetails resourceDetails(){
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setAccessTokenUri(oauthInfo.get("oauth_url") + oauthInfo.get("request_and_refresh_token"));
		resource.setClientId(oauthInfo.get("client_id"));
        resource.setGrantType("authorization_code");
        resource.setUserAuthorizationUri(oauthInfo.get("oauth_url") + oauthInfo.get("request_code_url"));
        resource.setScope(Arrays.asList("app"));
        resource.setPreEstablishedRedirectUri(oauthInfo.get("redirect_uri"));
	    return resource;
	}
	
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(){
        accessTokenRequest().setPreservedState(oauthInfo.get("redirect_uri"));
        accessTokenRequest().setStateKey(new DefaultStateKeyGenerator().generateKey(resourceDetails()));
        
        AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
        provider.setAuthenticationHandler(new ClientAuthenticationHandler() {
			@Override
			public void authenticateTokenRequest(
					OAuth2ProtectedResourceDetails resource,
					MultiValueMap<String, String> form, HttpHeaders headers) {
				headers.set("Authorization", "Basic " + oauthInfo.get("private_key") );
			}
		});
        AccessTokenProviderChain providerChain = new AccessTokenProviderChain(Arrays.asList(provider));
        //oauth2Context.setPreservedState(accessTokenRequest().getStateKey(),accessTokenRequest().getPreservedState());
        OAuth2RestTemplate template=new OAuth2RestTemplate(resourceDetails(),oauth2Context);
        template.setAccessTokenProvider(providerChain);
        return template;
	}
 
	
}