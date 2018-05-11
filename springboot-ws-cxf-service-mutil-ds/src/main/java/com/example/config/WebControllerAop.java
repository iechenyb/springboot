package com.example.config;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年1月12日 在切入点前的操作，按order的值由小到大执行 在切入点后的操作，按order的值由大到小执行
 */
@Component
@Aspect
@Order(5)
public class WebControllerAop {
	Log log = LogFactory.getLog(WebControllerAop.class);
	@Pointcut("execution(* com.example.service.impl.*.*(..))")
	public void executeService() {
	}
	@Around(value = "executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object obj = null;
		try {// obj之前可以写目标方法执行前的逻辑
			long s =System.currentTimeMillis();
			obj = proceedingJoinPoint.proceed();// 调用执行目标方法
			long e =System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName()+" 执行时间："+(e-s));
			return obj;
		} catch (Exception throwable) {
			StringBuilder sb = new StringBuilder();
			sb.append(proceedingJoinPoint.getTarget().getClass().getName() + " : "
					+ Arrays.toString(proceedingJoinPoint.getArgs()) + " in "
					+ proceedingJoinPoint.getSignature().getName() + "()");
			throwable.printStackTrace();
			return "null";
		}

	}

}
