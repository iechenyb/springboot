package com.kiiik.utils;
import com.kiiik.pub.bean.BaseResult;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月14日
 */
public class ValidateRestult extends BaseResult {
	 String code = SUCCESS; 
	 String msg;
	public ValidateRestult(String code_,String msg_){
		code = code_;
		msg = msg_;
	}
	public   ValidateRestult success(){
		code = SUCCESS;
		return this;
	}
	public   ValidateRestult fail(){
		code = FAIL;
		return this;
	}
	public   ValidateRestult msg(String msg_){
		msg = msg_;
		return this;
	}
	public static ValidateRestult getInstance(){
		return new ValidateRestult(FAIL,"");
	}
}
