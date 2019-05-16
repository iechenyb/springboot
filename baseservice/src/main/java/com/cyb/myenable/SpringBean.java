package com.cyb.myenable;
import java.beans.PropertyDescriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年2月1日
 *Bean的完整生命周期经历了各种方法调用，这些方法可以划分为以下几类：

1、Bean自身的方法　　：　　这个包括了Bean本身调用的方法和通过配置文件中<bean>的init-method和destroy-method指定的方法

2、Bean级生命周期接口方法　　：　　这个包括了BeanNameAware、BeanFactoryAware、InitializingBean和DiposableBean这些接口的方法

3、容器级生命周期接口方法　　：　　这个包括了InstantiationAwareBeanPostProcessor 和 BeanPostProcessor 这两个接口实现，一般称它们的实现类为“后处理器”。

4、工厂后处理器接口方法　　：　　这个包括了AspectJWeavingEnabler, ConfigurationClassPostProcessor, CustomAutowireConfigurer等等非常有用的工厂后处理器　　
接口的方法。工厂后处理器也是容器级的。在应用上下文装配配置文件之后立即调用。
 */
public class SpringBean  
	implements 
	BeanNameAware,//获取bean在容器中的名字
	BeanFactoryAware,//理解
	InitializingBean,
	DisposableBean,
	InstantiationAwareBeanPostProcessor,
	BeanPostProcessor//理解 
	{
	Log log = LogFactory.getLog(SpringBean.class);
	public int a=initA();
	
	public SpringBean(){
		log.info("构造器在执行！");
	}
	
	private int initA() {
		log.info("初始化基本属性");
		return 0;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("postProcessBeforeInitialization:"+bean);
		return bean;//理解 after 属性设置和new AA();
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("postProcessAfterInitialization："+bean);
		return bean;//理解 after 属性初始化和new AA();
	}

	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		log.info("postProcessBeforeInstantiation:"+beanClass);
		return null;
	}

	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		log.info("postProcessAfterInstantiation:"+bean);
		return false;
	}

	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
			String beanName) throws BeansException {
		log.info(pvs.getPropertyValues().length);
		return pvs;
	}
	  // 这是InitializingBean接口方法
	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet");
	}
	// 这是BeanFactoryAware接口方法
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		//可以将beanFactory拿到当前类使用
		//beanFactory.getBean("aaa");
		log.info("setBeanFactory:"+beanFactory);
	}

	public void setBeanName(String name) {
		log.info("BeanNameAware:"+name);
	}

	public void destroy() throws Exception {
		log.info("DisposableBean 容器关闭的时候执行！");
	}
	// 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestory() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }
}
