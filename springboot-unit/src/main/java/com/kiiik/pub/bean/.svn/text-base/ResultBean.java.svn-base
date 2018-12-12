package com.kiiik.pub.bean;
import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 创建时间: 2018年5月10日
 */
@ApiModel(value="统一返回对象",description="统一的返回值定义方式")
public class ResultBean<T> extends BaseResult implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="服务器响应描述",name="msg",example="执行成功！")
	private String es = "ok";
	
	@ApiModelProperty(value="执行状态",name="ec",example="0 成功 1失败 ")
	private String ec = SUCCESS;
	
	@ApiModelProperty(value="数据体",name="d",example="任意类型数据集合")
	private T d;
	
	public ResultBean() {
		super();
	}

	public ResultBean(T data) {
		super();
		this.d = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.ec = FAIL;
		this.es = e.toString();
	}

	public ResultBean<T> success() {
		this.ec = SUCCESS;
		return this;
	}
	public ResultBean<T> success(String msg) {
		this.ec = SUCCESS;
		this.es = msg;
		return this;
	}
	public ResultBean<T> data(T data) {
		this.d = data;
		this.ec = SUCCESS;
		return this;
	}

	public ResultBean<T> fail() {
		this.ec = FAIL;
		return this;
	}
	public ResultBean<T> fail(String msg) {
		this.ec = FAIL;
		this.es = msg;
		return this;
	}
	public ResultBean<T> fail(String ec,String msg) {
		this.ec = ec;
		this.es = msg;
		return this;
	}

	public ResultBean<T> fail(Throwable e) {
		this.ec = FAIL;
		this.es = e.toString();
		return this;
	}
	
	public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public  ResultBean<T> msg(String msg) {
		this.es = msg;
		return this;
	}

	public static String getSuccess() {
		return SUCCESS;
	}

	public static String getFail() {
		return FAIL;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public T getD() {
		return d;
	}

	public void setD(T d) {
		this.d = d;
	}
	
	public boolean isFail(){
		return FAIL.equals(this.ec);
	}
	
	public boolean isSuccess(){
		return SUCCESS.equals(this.ec);
	}
}
