package com.cyb.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年1月12日
 */
@Component
@Aspect
public class WebControllerAop {
	Log log = LogFactory.getLog(WebControllerAop.class);

	@Pointcut("execution(* com.cyb..controller.*.*(..))")
	public void executeService() {
	}

	@AfterThrowing(value = "executeService()", throwing = "exception")
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		// 目标方法名：
		System.out.println(joinPoint.getSignature().getName());
		if (exception instanceof NullPointerException) {
			System.out.println("发生了空指针异常!!!!!");
		}
	}

	@Around(value = "executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		System.out.println("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
		try {// obj之前可以写目标方法执行前的逻辑
			Object obj = proceedingJoinPoint.proceed();// 调用执行目标方法
			return obj;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return null;
	}
}
