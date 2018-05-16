package com.kiiik.vas.example.model;

import io.swagger.annotations.ApiModelProperty;

/**
 *作者 : iechenyb<br>
 *类描述: 变量名不能全大写，使用驼峰命名<br>
 *创建时间: 2018年5月15日
 */
public class BaseRQ {
	@ApiModelProperty(value="功能号",name="F",example="getUser")
	private String f;//纯大写不能生成
	@ApiModelProperty(value="请求唯一标记",name="ID",example="99")
	private String id;
	
	private String abCdDe;//驼峰命名
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbCdDe() {
		return abCdDe;
	}
	public void setAbCdDe(String abCdDe) {
		this.abCdDe = abCdDe;
	}
	
}
