package com.cyb.aop;

import java.io.Serializable;

/**
 * 作者 : iechenyb<br>
 * 类描述: http://blog.didispace.com/cxy-wsm-zml-2/<br>
 * 创建时间: 2018年1月10日
 */
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 1;

	public static final int FAIL = 0;

	public static final int NO_PERMISSION = 2;

	private String msg = "success";

	private int code = SUCCESS;

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
	public ResultBean data(T data) {
		this.code = SUCCESS;
		this.data = data;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean fail() {
		this.code = FAIL;
		this.data = null;
		return this;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean fail(Throwable e) {
		this.code = FAIL;
		this.data = null;
		this.msg = e.toString();
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
