package com.cyb.config;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月7日
 */
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
 
//声明本类是一个配置类
@Configuration
//自动加载ch2.aware包下面的内容
@ComponentScan("aware")
public class AwareConfig {
 
}
