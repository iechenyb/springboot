package com.cyb.validate.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月16日
 */
public class ValidBean {
	@NotNull(message = "名字不能为空")
	@Length(min=2,max=20)
	@IsMobile
	private String name;

	@Min(value = 18, message = "年龄必须大于18")
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
