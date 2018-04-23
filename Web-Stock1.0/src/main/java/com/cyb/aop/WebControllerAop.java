package com.cyb.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.context.TimeContext;
import com.cyb.date.DateUtil;
import com.cyb.log.LogRule;
import com.cyb.log.MyLog;
import com.cyb.utils.AopUtils;

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
	
	@Autowired
	LogRule logRule;

	// 如果有些controller方法不是浏览器发起，二是controller回调的，request可能为空！
	@Pointcut("execution(* com.cyb..controller.*.*(..))")
	public void executeService() {
	}

	// 获取的方法无法正常获取注解方法
	@SuppressWarnings("rawtypes")
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

	@Before("executeService()")
	public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		// 接收到请求，记录请求内容
		log.info("WebLogAspect.doBefore()");
		// Method m = getMethod(joinPoint);
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		System.out.println(isRequestFromUrl(method) + "#########" + getMethodAnnotation(joinPoint, GetMapping.class));
		/*
		 * GetMapping rm = (GetMapping) joinPoint.getSignature()
		 * .getDeclaringType().getAnnotation(GetMapping.class);
		 * System.out.println(rm.name()+"."+joinPoint.getTarget().getClass().
		 * getName()+"."+joinPoint.getSignature().getName()+
		 * "是否包含requestmapping注解 ："+joinPoint.getSignature()
		 * .getDeclaringType().isAnnotationPresent(GetMapping.class));
		 */
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Object username = "";
		if(request.getSession()!=null){
			username = request.getSession().getAttribute("userid");
		}
		// 记录下请求内容
		log.info("URL : " + request.getRequestURL().toString());
		log.info("HTTP_METHOD : " + request.getMethod());
		log.info("IP : " + request.getRemoteAddr());
		log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
		MyLog log = new MyLog();
		log.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName()+":"+Arrays.toString(joinPoint.getArgs()));
		log.setTime(DateUtil.timeToMilis());
		log.setUserName(username==null?"":username.toString());
		logRule.saveVistiorLog(log);//记录审计日志
		/*
		 * //获取所有参数方法一： Enumeration<String> enu = request.getParameterNames();
		 * while (enu.hasMoreElements()) { String paraName = enu.nextElement();
		 * System.out.println(paraName + ": " + request.getParameter(paraName));
		 * }
		 */
	}

	// @AfterThrowing(value = "executeService()", throwing = "exception")
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		// 目标方法名：
		System.out.println(joinPoint.getSignature().getName());
		if (exception instanceof NullPointerException) {
			System.out.println("发生了空指针异常!!!!!");
		}
	}

	public Object doAfterThrowingAdvice(Throwable exception, Class<?> retType) {
		// 目标方法名：
		if (exception instanceof NullPointerException) {
			NullPointerException e = (NullPointerException) exception;
			System.out.println("发生了空指针异常!!!!!" + e.getMessage());
		} else {
			System.out.println("发生了异常！" + exception.getMessage());
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

	@SuppressWarnings("rawtypes")
	@Around(value = "executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// System.out.println("环绕通知的目标方法名：" +
		// proceedingJoinPoint.getSignature().getName());
		MyLog log = new MyLog();
		Object obj = null;
		/*
		 * MethodSignature methodSignature =null; Class<?> returnType =
		 * proceedingJoinPoint.getTarget().getClass(); Class<?> retCls=null;
		 * if(proceedingJoinPoint.getSignature()!=null){ methodSignature
		 * =(MethodSignature) proceedingJoinPoint.getSignature(); retCls =
		 * methodSignature.getReturnType(); }
		 */
		Method curMethod = AopUtils.getMethod(proceedingJoinPoint);
		curMethod.isAnnotationPresent(RequestMapping.class);// 判断
		Log log_ = AopUtils.getLog(proceedingJoinPoint);// 获取目标类curMethod.getDeclaringClass()
		// 当前方法的类型和方法所在的类的类型
		System.out.println(curMethod.getClass() + "----" + curMethod.getDeclaringClass());
		// System.out.println(retCls.getSimpleName()+"方法的返回值类型"+returnType.getClass());
		try {// obj之前可以写目标方法执行前的逻辑
			log_.info(curMethod.getName() + "执行开始");
			long s =System.currentTimeMillis();
			obj = proceedingJoinPoint.proceed();// 调用执行目标方法
			log_.info(curMethod.getName() + "执行结束");
			long e =System.currentTimeMillis();
			TimeContext.setTime(e-s);
			System.out.println(Thread.currentThread().getName()+" 执行时间："+(e-s));
			return obj;
		} catch (Exception throwable) {
			StringBuilder sb = new StringBuilder();
			sb.append(proceedingJoinPoint.getTarget().getClass().getName() + " : "
					+ Arrays.toString(proceedingJoinPoint.getArgs()) + " in "
					+ proceedingJoinPoint.getSignature().getName() + "()");
			throwable.printStackTrace();
			
			Class[] params = AopUtils.getParameterTypes(proceedingJoinPoint);
			String types = "";// 注意空判断
			for (int i = 0; i < params.length; i++) {
				types = types + params[i].getName() + ",";
			}
			// curMethod.getDeclaringClass()+"."+curMethod.getName()+"("+types.substring(0,
			// types.length()-1)+")"
			log.setMethod(sb.toString());
			log.setException(throwable.getLocalizedMessage());
			log.setTime(DateUtil.timeToMilis());
			logRule.saveExceptionLog(log);
			return doAfterThrowingAdvice(throwable, curMethod.getReturnType());
		}

	}

	private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(clazz);
	}

	private <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> clazz) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(clazz);
	}

}
