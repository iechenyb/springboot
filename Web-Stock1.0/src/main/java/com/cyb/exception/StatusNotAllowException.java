package com.cyb.exception;
import org.springframework.security.core.AuthenticationException;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月19日
 */
public class StatusNotAllowException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public StatusNotAllowException(String msg) {
		super(msg);
	}
	/**
	 * Constructs a {@code UsernameNotFoundException} with the specified message and root
	 * cause.
	 *
	 * @param msg the detail message.
	 * @param t root cause
	 */
	public StatusNotAllowException(String msg, Throwable t) {
		super(msg, t);
	}
}
