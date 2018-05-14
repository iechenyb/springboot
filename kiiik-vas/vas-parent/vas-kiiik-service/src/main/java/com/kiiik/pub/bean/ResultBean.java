package com.kiiik.pub.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: http://blog.didispace.com/cxy-wsm-zml-2/<br>
 * 创建时间: 2018年1月10日
 */
@ApiModel(value="统一返回对象",description="统一的返回值定义方式")
public class ResultBean<T> extends BaseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int NO_PERMISSION = 2;
	
	@ApiModelProperty(value="执行结果",name="msg",example="执行成功！")
	private String msg = "success";
	
	@ApiModelProperty(value="执行状态",name="code",example="0 失败 1成功 ")
	private int code = SUCCESS;
	
	@ApiModelProperty(value="数据体",name="data",example="任意类型数据集合")
	private T data;

	public ResultBean() {
		super();
	}

	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = FAIL;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean success() {
		this.code = SUCCESS;
		return this;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean success(String msg) {
		this.code = SUCCESS;
		this.msg = msg;
		return this;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean data(T data) {
		this.code = SUCCESS;
		this.data = data;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean fail() {
		this.code = FAIL;
		return this;
	}
	@SuppressWarnings("rawtypes")
	public ResultBean fail(String msg) {
		this.code = FAIL;
		this.msg = msg;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean fail(Throwable e) {
		this.code = FAIL;
		this.msg = e.toString();
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public  ResultBean<T> msg(String msg) {
		this.msg = msg;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getSuccess() {
		return SUCCESS;
	}

	public static int getFail() {
		return FAIL;
	}

	public static int getNoPermission() {
		return NO_PERMISSION;
	}

}
