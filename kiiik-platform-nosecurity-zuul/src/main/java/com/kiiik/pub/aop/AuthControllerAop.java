package com.kiiik.pub.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.context.TimeContext;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.RequestUtils;
import com.kiiik.web.log.bean.SystemLog;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 */
@Component
@Aspect
@Order(5)
public class AuthControllerAop {
	Log log = LogFactory.getLog(AuthControllerAop.class);

	@Pointcut("execution(* com.kiiik.web.system.controller.*.*(..))")
	public void executeService() {
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

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 异常直接抛出，不在控制逻辑里处理！<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "executeService()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object obj = null;
		long s = System.currentTimeMillis();
		obj = proceedingJoinPoint.proceed();// 调用执行目标方法
		long e = System.currentTimeMillis();
		TimeContext.setTime(e - s);
		recordVisitLog(proceedingJoinPoint);
		return obj;
	}

	@Autowired
	GenericService genericService;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 直接使用转换后的参数信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param proceedingJoinPoint
	 */
	private void recordVisitLog(ProceedingJoinPoint proceedingJoinPoint) {
		Object[] args = proceedingJoinPoint.getArgs();//参数 
		List<Object> args_new= new ArrayList<Object>();
		for(int i=0;i<args.length;i++){
			
			if(!args[i].toString().contains("RequestWrapper")&&
				!args[i].toString().contains("ResponseWrapper")
			){
				args_new.add(args[i]);//去掉包装类参数
			}
		}
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			if (ra != null) {
				ServletRequestAttributes sra = (ServletRequestAttributes) ra;
				HttpServletRequest request = sra.getRequest();
				if (!StringUtils.isEmpty(request.getRemoteUser())) {
					SystemLog log= RequestUtils.getSystemLog(request,false);
					if(args!=null){
						log.setParam(JSON.toJSONString(args_new));//将request和response去掉
					}
					log.setModule(KiiikContants.ZUULNAME);
					genericService.insertDBEntity(log);
				}
			}
		} catch (Exception e) {
			// 日志异常容错，不进行记录
			e.printStackTrace();
		}
	}
	

	@SuppressWarnings("unused")
	private void recordVisitLog() {
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			if (ra != null) {
				ServletRequestAttributes sra = (ServletRequestAttributes) ra;
				HttpServletRequest request = sra.getRequest();
				if (!StringUtils.isEmpty(request.getRemoteUser())) {
					genericService.insertDBEntity(RequestUtils.getSystemLog(request,false));
				}
			}
		} catch (Exception e) {
			// 日志异常容错，不进行记录
			e.printStackTrace();
		}
	}

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
