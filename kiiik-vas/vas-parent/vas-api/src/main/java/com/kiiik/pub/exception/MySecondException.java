package com.kiiik.pub.exception;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月11日
 */
public class MySecondException extends Throwable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(MySecondException.class);
	public MySecondException() {
		super();
		
	}
	public MySecondException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	public MySecondException(String message, Throwable cause) {
		super(message, cause);
		
	}
	public MySecondException(String message) {
		super(message);
		
	}
	public MySecondException(Throwable cause) {
		super(cause);
	}
	
}
