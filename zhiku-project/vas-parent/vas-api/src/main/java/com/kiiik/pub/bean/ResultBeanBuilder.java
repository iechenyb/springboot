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
public class ResultBeanBuilder<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  final int SUCCESS = 1;

	public  final int FAIL = 0;

	public  final int NO_PERMISSION = 2;
	
	@ApiModelProperty(value="执行结果",name="msg",example="执行成功！")
	private  String msg = "success";
	
	@ApiModelProperty(value="执行状态",name="code",example="0 失败 1成功 ")
	private  int code = SUCCESS;
	
	@ApiModelProperty(value="数据体",name="data",example="任意类型数据集合")
	private T data;

	public ResultBeanBuilder() {
		super();
	}
	public ResultBeanBuilder(int code_,String msg_) {
		super();
		code = code_;
		msg = msg_;
	}

	public ResultBeanBuilder(T data) {
		super();
		this.data = data;
	}

	public ResultBeanBuilder(Throwable e) {
		super();
		msg = e.toString();
		code = FAIL;
	}

	public  void success() {
		code = SUCCESS;
	}
	public  void success(String msg_) {
		code = SUCCESS;
		msg = msg_;
	}
	public  ResultBeanBuilder<T> data(T data_) {
		code = SUCCESS;
		data = data_;
		return this;
	}

	public  ResultBeanBuilder<T> fail() {
	   code = FAIL;
	   return this;
	}
	public  ResultBeanBuilder<T> fail(String msg_) {
		code = FAIL;
		msg = msg_;
		return this;
	}

	public  ResultBeanBuilder<T> fail(Throwable e) {
		code = FAIL;
		msg = e.toString();
		return this;
	}

	public  ResultBeanBuilder<T> setMsg(String msg_) {
		msg = msg_;
		return this;
	}
	public   ResultBeanBuilder<T> msg(String msg_) {
		msg = msg_;
		return this;
	}

	public int getCode() {
		return code;
	}

	public  ResultBeanBuilder<T> setCode(int code_) {
		code = code_;
		return this;
	}

	public T getData() {
		return data;
	}

	public ResultBeanBuilder<T> setData(T data) {
		this.data = data;
		return this;
	}
    //设置完信息和状态之后，设置
	public ResultBeanBuilder<T> build(){
		return new ResultBeanBuilder<T>(code,msg)
				.setData(data);
	}
	//不能定义成静态的变量
	public  ResultBeanBuilder<T> getInstance(){
		return new ResultBeanBuilder<T>().msg("");
	}
	public  void main(String[] args) {
		new ResultBeanBuilder<String>()
		.msg("haha")
		.fail()
		.build();
	}
}
