package com.kiiik.zuul.security;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月11日
 */
public class VasException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(VasException.class);
	public VasException() {
		super();
	}
	public VasException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	public VasException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
	public VasException(String arg0) {
		super(arg0);
		
	}
	public VasException(Throwable arg0) {
		super(arg0);
	}
	
}
