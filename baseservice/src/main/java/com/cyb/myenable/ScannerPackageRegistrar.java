package com.cyb.myenable;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年2月1日
 */
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ScannerPackageRegistrar implements ImportBeanDefinitionRegistrar {
	Log log = LogFactory.getLog(ScannerPackageRegistrar.class);

	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
			BeanDefinitionRegistry beanDefinitionRegistry) {
		System.out.println(
				"packages value::" + annotationMetadata.getAnnotationAttributes(EnableScanner.class.getName()));

		String[] arrs = (String[]) annotationMetadata.getAnnotationAttributes(EnableScanner.class.getName())
				.get("packages");
		List<String> packages = Arrays.asList(arrs);
		System.out.println(packages);

		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(MyBeanDefinitionProcessor.class);
		bdb.addPropertyValue("packages", packages);

		beanDefinitionRegistry.registerBeanDefinition(MyBeanDefinitionProcessor.class.getName(),
				bdb.getBeanDefinition());
	}
}
