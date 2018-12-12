package com.kiiik.pub.oauth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.state.DefaultStateKeyGenerator;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月20日
 */
/*@Component*/
public class AccessTokenUtils {
	Log logger = LogFactory.getLog(AccessTokenUtils.class);
	@Autowired  
    private OAuth2RestTemplate restTemplate;  
      
    @Autowired  
    private Oauth2Config oauth2Config;  
      
      
    /** 
     * 获取oauth2的授权令牌access_token 
     * @return 
     */  
    public  String getAccessToken(){  
        logger.info("获取oauth2的授权令牌access_token start ...");  
        OAuth2ClientContext oAuth2ClientContext = restTemplate.getOAuth2ClientContext();  
        oAuth2ClientContext.getAccessTokenRequest().setAuthorizationCode(null);//清空授权code  
        String stateKey = oAuth2ClientContext.getAccessTokenRequest().getStateKey();  
        Object preservedState = oAuth2ClientContext.getAccessTokenRequest().getPreservedState();  
        if(StringUtils.isEmpty(stateKey))  
            stateKey = new DefaultStateKeyGenerator().generateKey(oauth2Config.resourceDetails());  
        if(preservedState == null )  
            preservedState = "http://www.baidu.com";//VipConstant.redirtUrl;  
          
        logger.info("statekey：" + stateKey + " ; preservedState : " + preservedState);  
        oAuth2ClientContext.setPreservedState(oAuth2ClientContext.getAccessTokenRequest().getStateKey(), oAuth2ClientContext.getAccessTokenRequest().getPreservedState());  
        OAuth2AccessToken oAuth2AccessToken = this.restTemplate.getAccessToken();  
        String access_token = oAuth2AccessToken.getValue();  
        logger.info("获取oauth2的授权令牌access_token end ；access_token = " +  access_token + "；失效时间 = " + oAuth2AccessToken.getExpiration() + " ；剩余失效时间：" + oAuth2AccessToken.getExpiresIn() );  
        return access_token;  
    }  
      
}
