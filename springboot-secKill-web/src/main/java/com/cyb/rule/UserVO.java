package com.cyb.rule;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: http://blog.didispace.com/cxy-wsm-zml-2/<br>
 * 创建时间: 2018年1月10日
 */
@ApiModel(value = "统一返回对象", description = "统一的返回值定义方式")
public class UserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "姓名", name = "name", example = "字符串内容")
	private String name = "iechenyb";
	
	@ApiModelProperty(value = "年龄", name = "age", example = "年龄大小")
	private String age = "20";
	
	@ApiModelProperty(value = "是否抛出异常", name = "throwEx", example = "1 抛出异常 其他正常执行")
	private int throwEx ;
	
	public UserVO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getThrowEx() {
		return throwEx;
	}

	public void setThrowEx(int throwEx) {
		this.throwEx = throwEx;
	}

}
