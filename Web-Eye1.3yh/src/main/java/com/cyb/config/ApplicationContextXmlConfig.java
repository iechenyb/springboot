package com.cyb.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
/**
 *classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}
 * file路径： locations ={"file:d:/test/application-bean1.xml"};
 */
@Configuration
@ImportResource(locations={"classpath:application-bean.xml"
		,"classpath:application-tansaction.xml"
		//,"classpath:application-sessionfactory.xml"
		})
public class ApplicationContextXmlConfig {
 
}