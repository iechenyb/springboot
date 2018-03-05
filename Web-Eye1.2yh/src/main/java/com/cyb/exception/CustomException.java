package com.cyb.exception;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月23日
 */
public class CustomException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(CustomException.class);
}
