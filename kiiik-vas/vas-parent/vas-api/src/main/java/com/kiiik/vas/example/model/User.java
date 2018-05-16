package com.kiiik.vas.example.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
@ApiModel(value="用户信息",description="户信息描述")
public class User{
	@ApiModelProperty(value="姓名",name="name",example="iechenyb")
	private String name;
	@ApiModelProperty(value="密码",name="passowrd",example="123456")
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
