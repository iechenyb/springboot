package com.aiyi.core.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 内部自动化事物控制器
 * @author 郭胜凯
 * @time 2016年5月11日上午10:02:15
 * @email 719348277@qq.com
 */
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component
@Aspect
public class Log {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("@annotation(com.aiyi.core.annotation.po.Log)")
	public void logMethod(){}
	
	@Before("logMethod()")
	public void openTra(JoinPoint joinPoint) throws ClassNotFoundException{

		String targetName = joinPoint.getTarget().getClass().getName();

		String name = joinPoint.getSignature().getName();

		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);

		Method[] methods = targetClass.getMethods();

		String methodName = null;

		for (Method method : methods) {
			if (method.getName().equals(name)) {
				@SuppressWarnings("rawtypes")
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {

					methodName = method.getAnnotation(com.aiyi.core.annotation.po.Log.class).action();

					if (methodName.equals("null")) {
						methodName = name;
					}

					break;
				}
			}
		}

		logger.info("/-----------" + methodName + "进栈/"+ "\r\n\r\n args:" + JSON.toJSONString(arguments) + "\r\n");
		
	}
	
	@AfterReturning("logMethod()")
	public void cmmintTra(JoinPoint joinPoint) throws ClassNotFoundException{
		
		String targetName = joinPoint.getTarget().getClass().getName();

		String name = joinPoint.getSignature().getName();

		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);

		Method[] methods = targetClass.getMethods();

		String methodName = null;

		for (Method method : methods) {
			if (method.getName().equals(name)) {
				@SuppressWarnings("rawtypes")
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {

					methodName = method.getAnnotation(com.aiyi.core.annotation.po.Log.class).action();

					if (methodName.equals("null")) {
						methodName = name;
					}

					break;
				}
			}
		}
		
		logger.info("/-----------" + methodName + "出栈/"+ "\r\n\r\n args:" + JSON.toJSONString(arguments) + "\r\n");
				
				
		
	}
}
