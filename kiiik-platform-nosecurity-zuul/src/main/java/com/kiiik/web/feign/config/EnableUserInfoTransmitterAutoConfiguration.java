package com.kiiik.web.feign.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kiiik.web.feign.interceptor.TransmitUserInfoFeighClientIntercepter;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月3日
 */
@Configuration
public class EnableUserInfoTransmitterAutoConfiguration {
	Log log = LogFactory.getLog(EnableUserInfoTransmitterAutoConfiguration.class);
	public EnableUserInfoTransmitterAutoConfiguration() {
    }

    @Bean
    public TransmitUserInfoFeighClientIntercepter transmitUserInfo2FeighHttpHeader(){
       return new TransmitUserInfoFeighClientIntercepter();
    }

    @Bean
    public TransmitUserInfoFilter transmitUserInfoFromHttpHeader(){
        return new TransmitUserInfoFilter();
    }
}
