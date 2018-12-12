package com.kiiik.zuul.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.zuul.pub.bean.ResultBean;
import com.kiiik.zuul.web.log.repository.SystemLogRepository;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 */
/*@Configuration
@Aspect
@Order(5)*/
public class ControllerAop {
	Log log = LogFactory.getLog(ControllerAop.class);
    //@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PostMapping) or @annotation(org.springframework.web.bind.annotation.GetMapping)") 
	@Pointcut("execution(* com.kiiik.zuul.web.auth.controller.UserController.*(..))")//com.kiiik.zuul.web.auth.controller
	public void method() {
		
	}

	// 获取的方法无法正常获取注解方法
	@SuppressWarnings({ "rawtypes", "unused" })
	Method getMethod(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		// 拦截的实体类
		Object target = joinPoint.getTarget();
		// 拦截的方法名称
		String methodName = joinPoint.getSignature().getName();
		// 拦截的方法参数
		Object[] argsa = joinPoint.getArgs();
		// 拦截的放参数类型
		Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
		Method method = target.getClass().getMethod(methodName, parameterTypes);
		return method;
	}

	// 获取的方法无法正常获取注解方法
	Method getMethod2(JoinPoint joinPoint)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
		Field proxy = methodPoint.getClass().getDeclaredField("methodInvocation");
		proxy.setAccessible(true);
		ReflectiveMethodInvocation j = (ReflectiveMethodInvocation) proxy.get(methodPoint);
		Method method = j.getMethod();
		return method;
	}

	boolean isRequestFromUrl(Method m) {
		return m.isAnnotationPresent(RequestMapping.class) || m.isAnnotationPresent(GetMapping.class)
				|| m.isAnnotationPresent(PostMapping.class) || m.isAnnotationPresent(PutMapping.class);
	}


	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		// 目标方法名：
		log.info(joinPoint.getSignature().getName());
		if (exception instanceof NullPointerException) {
			log.info("发生了空指针异常!!!!!");
		}
	}

	public Object doAfterThrowingAdvice(Throwable exception, Class<?> retType) {
		// 目标方法名：
		if (exception instanceof NullPointerException) {
			NullPointerException e = (NullPointerException) exception;
			log.error("发生了空指针异常!!!!!" + e.getMessage());
		} else {
			log.error("发生了异常！" + exception.getMessage());
		}

		if (retType == null) {
			return null;// 空方法
		} else if (ResultBean.class.toString().equals(retType.toString())) {
			// 如果是500或者404直接跳转到全局定义的页面
			return new ResultBean<Object>(exception);
		} else if (ModelAndView.class.toString().equals(retType.toString())) {
			// 如果是500或者404直接跳转到全局定义的页面
			ModelAndView view = new ModelAndView();
			view.addObject("msg", exception.getMessage());
			view.setViewName("/exception/500");
			return view;
		} else {// 其他返回类型"error"
			try {
				return retType.newInstance();// 异常时，返回一个指定类型的空对象
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	@Autowired
	SystemLogRepository logRes;
	   
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 异常直接抛出，不在控制逻辑里处理！<br>
     *创建时间: 2017年7月15日hj12
     *@param proceedingJoinPoint
     *@return
     *@throws Throwable
     */
	/*@Around(value = "method()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		 try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			 if(ra!=null){
			    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			    HttpServletRequest request = sra.getRequest();
				if(!StringUtils.isEmpty(request.getRemoteUser())){
					logRes.save(RequestUtils.getSystemLog(request));
				}
			}
		} catch (Exception e) {
			//日志异常容错，不进行记录
		}
		System.out.println("aaaaa");
		return  proceedingJoinPoint.proceed();// 调用执行目标方法
	}
	*/
	

	@SuppressWarnings("unused")
	private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(clazz);
	}

	@SuppressWarnings("unused")
	private <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> clazz) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(clazz);
	}

}
