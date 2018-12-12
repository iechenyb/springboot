package com.kiiik.utils;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月25日
 */
public class AopUtils {
	
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
	@SuppressWarnings("rawtypes")
	public static Class[] getParameterTypes(final JoinPoint joinPoint) {
		try {
			Signature sig = joinPoint.getSignature();
	        MethodSignature msig = null;
	        if (!(sig instanceof MethodSignature)) {
	            throw new IllegalArgumentException("该注解只能用于方法");
	        }
	        msig = (MethodSignature) sig;
	        return msig.getParameterTypes();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
