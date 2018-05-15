package com.kiiik.pub.exception;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
public class MyException2 extends VasException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(MyException2.class);
	 public MyException2(String message) {
	        super(message);
	    }
}
