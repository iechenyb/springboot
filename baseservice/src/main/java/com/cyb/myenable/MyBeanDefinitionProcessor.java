package com.cyb.myenable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
 *作者 : iechenyb<br>
 *类描述: 所有的bean定义时添加前后处理逻辑<br>
 *创建时间: 2019年2月1日
 */
public class MyBeanDefinitionProcessor implements BeanPostProcessor{
	Log log = LogFactory.getLog(MyBeanDefinitionProcessor.class);
	private List<String> packages;
	 
    public List<String> getPackages() {
        return packages;
    }
 
    public void setPackages(List<String> packages) {
        this.packages = packages;
    }
    //before new()之前调用
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (String pkg : packages) {
            if(bean.getClass().getName().startsWith(pkg)){
                System.out.println("before instance bean:"+bean.getClass().getName());
            }
        }
        return bean;
    }
    //after new()之后调用
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		for (String pkg : packages) {
            if(bean.getClass().getName().startsWith(pkg)){
                System.out.println("after instance bean:"+bean.getClass().getName());
            }
        }
		return bean;
	}
}
/**
 * 
 * 
	before instance bean:com.cyb.adapter.KiiikWebMvcConfigurer$$EnhancerBySpringCGLIB$$5fdac82e
	after instance bean:com.cyb.adapter.KiiikWebMvcConfigurer$$EnhancerBySpringCGLIB$$5fdac82e
	ErrorControllerAdvice基础属性初始化！
	ErrorControllerAdvice调用构造器执行初始化！
	before instance bean:com.cyb.advice.ErrorControllerAdvice
	after instance bean:com.cyb.advice.ErrorControllerAdvice
*/
