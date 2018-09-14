package com.kiiik.pub.jmx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年7月11日
 */
@ManagedResource(objectName = "bean:name=testBean4", description = "My Managed Bean", log = true, logFile = "jmx.log", currencyTimeLimit = 15, persistPolicy = "OnUpdate", persistPeriod = 200, persistLocation = "foo", persistName = "bar")
@Configuration
@EnableMBeanExport
public class AnnotationTestBean implements IJmxTestBean {
	Log log = LogFactory.getLog(AnnotationTestBean.class);
	private String name;
	private int age;

	@ManagedAttribute(description = "The Age Attribute", currencyTimeLimit = 15)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@ManagedAttribute(description = "The Name Attribute", currencyTimeLimit = 20, defaultValue = "bar", persistPolicy = "OnUpdate")
	public void setName(String name) {
		this.name = name;
	}

	@ManagedAttribute(defaultValue = "foo", persistPeriod = 300)
	public String getName() {
		return name;
	}

	@ManagedOperation(description = "Add two numbers")
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "x", description = "The first number"),
			@ManagedOperationParameter(name = "y", description = "The second number") })
	public int add(int x, int y) {
		return x + y;
	}

	public void dontExposeMe() {
		throw new RuntimeException();
	}

	@Override
	public long myOperation() {
		return 0;
	}
}