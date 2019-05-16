package com.cyb.myenable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年2月1日
 */
@Configuration
@EnableScanner(packages={"com.cyb.myeanable"})
public class ScannerConfiguration {
	Log log = LogFactory.getLog(ScannerConfiguration.class);
	/*@Bean
	public SpringBean springBean(){
		return new SpringBean();
	}*/
}
