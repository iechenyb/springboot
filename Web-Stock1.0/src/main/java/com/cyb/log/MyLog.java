package com.cyb.log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月5日
 */
public class MyLog {
	Log log = LogFactory.getLog(MyLog.class);
	String time;//执行时间
	String method;//执行方法
	String userName;//某某人
	String exception;//报的异常类型
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String toString(){
		return time+","+userName+" execute "+method+" occur exception is "+exception;
	}
	public String format(){
		return time+","+userName+" execute "+method;
	}
}
