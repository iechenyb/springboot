package com.cyb.utils;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyb.aop.WebControllerAop;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月25日
 */
public class AopUtils {
	protected static Logger getLoggger(final JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();
        if (target != null) {
            return LoggerFactory.getLogger(target.getClass());
        }
        return LoggerFactory.getLogger(WebControllerAop.class);
    }
	public static Log getLog(final JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();
        if (target != null) {
            return LogFactory.getLog(target.getClass());
        }
        return LogFactory.getLog(WebControllerAop.class);
    }
	
	public static Method getMethod(final JoinPoint joinPoint) {
		Method currentMethod;
		try {
			Signature sig = joinPoint.getSignature();
	        MethodSignature msig = null;
	        if (!(sig instanceof MethodSignature)) {
	            throw new IllegalArgumentException("该注解只能用于方法");
	        }
	        msig = (MethodSignature) sig;
	        Object target = joinPoint.getTarget();
			currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
        return currentMethod;
	}
}
