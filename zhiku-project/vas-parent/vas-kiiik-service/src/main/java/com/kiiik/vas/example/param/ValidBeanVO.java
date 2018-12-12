package com.kiiik.vas.example.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月16日
 */
@ApiModel(value="验证表单对象测试",description="验证表单对象测试")
public class ValidBeanVO {
	
	@NotNull(message = "名字不能为空")
	@Length(min=2,max=20)
	@ApiModelProperty(value="姓名",name="name",example="张三")
	private String name;

	@Min(value = 18, message = "年龄必须大于18")
	@ApiModelProperty(value="年龄",name="age",example="必须大于18！")
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
