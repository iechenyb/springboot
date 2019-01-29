package com.kiiik.web.feign.config;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月3日
 */
import org.springframework.context.annotation.Import;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EnableUserInfoTransmitterAutoConfiguration.class})
public @interface EnableUserInfoTransmitter {
}