package com.example.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月4日
 */
@Configuration
@ImportResource(locations={"classpath:cxf.xml"})
public class XmlClass {
	Log log = LogFactory.getLog(XmlClass.class);
}
