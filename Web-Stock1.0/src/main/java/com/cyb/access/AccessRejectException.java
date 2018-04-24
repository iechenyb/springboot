package com.cyb.access;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月23日
 */
public class AccessRejectException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(AccessRejectException.class);
	public AccessRejectException() {
		super();
	}
	public AccessRejectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}
	public AccessRejectException(String message, Throwable cause) {
		super(message, cause);
		
	}
	public AccessRejectException(String message) {
		super(message);
		
	}
	public AccessRejectException(Throwable cause) {
		super(cause);
		
	}
	
}
