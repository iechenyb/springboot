package com.cyb.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.utils.AopUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年1月12日
 * 在切入点前的操作，按order的值由小到大执行
   在切入点后的操作，按order的值由大到小执行
 */
@Component
@Aspect
@Order(5)
public class WebControllerAop {
	Log log = LogFactory.getLog(WebControllerAop.class);

	@Pointcut("execution(* com.cyb..controller.*.*(..))")
	public void executeService() {
	}

	@Before("executeService()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        log.info("WebLogAspect.doBefore()");
        /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature
                ().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        //获取所有参数方法一：
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            System.out.println(paraName + ": " + request.getParameter(paraName));
        }*/
    }

	//@AfterThrowing(value = "executeService()", throwing = "exception")
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		// 目标方法名：
		System.out.println(joinPoint.getSignature().getName());
		if (exception instanceof NullPointerException) {
			System.out.println("发生了空指针异常!!!!!");
		}
	}
	
	public Object doAfterThrowingAdvice(Throwable exception,Class<?> retType) {
		// 目标方法名：
		if (exception instanceof NullPointerException) {
			System.out.println("发生了空指针异常!!!!!");
		}
		
		if(retType==null){
			return null;
		}else if(ResultBean.class.toString().equals(retType.toString())){
			//如果是500或者404直接跳转到全局定义的页面
			return new ResultBean<Object>(exception);
		}else  if(ModelAndView.class.toString().equals(retType.toString())){
			//如果是500或者404直接跳转到全局定义的页面
			ModelAndView view = new ModelAndView();
			view.addObject("msg", exception.getMessage());
			view.setViewName("/exception/500");
			return view;
		}else{
			return null;
		}
	}
	
	@Around(value = "executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
		//System.out.println("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
		Object obj = null;
		/*MethodSignature methodSignature =null;
		Class<?> returnType = proceedingJoinPoint.getTarget().getClass();
		Class<?> retCls=null;
		if(proceedingJoinPoint.getSignature()!=null){
			methodSignature =(MethodSignature) proceedingJoinPoint.getSignature();
			retCls = methodSignature.getReturnType();
		}*/
		Method curMethod = AopUtils.getMethod(proceedingJoinPoint);
		Log log_ = AopUtils.getLog(proceedingJoinPoint);//获取目标类curMethod.getDeclaringClass()
		//当前方法的类型和方法所在的类的类型
		System.out.println(curMethod.getClass()+"----"+curMethod.getDeclaringClass());
		 //System.out.println(retCls.getSimpleName()+"方法的返回值类型"+returnType.getClass());
		try {// obj之前可以写目标方法执行前的逻辑
			 log_.info(curMethod.getName()+"执行开始");
			 obj = proceedingJoinPoint.proceed();// 调用执行目标方法
			 log_.info(curMethod.getName()+"执行结束");
			 return obj;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return doAfterThrowingAdvice(throwable,curMethod.getReturnType());
		}
		
	}
	
}
