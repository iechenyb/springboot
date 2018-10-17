package com.kiiik.zuul.utils;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 */
import org.thymeleaf.util.DateUtils;

import com.kiiik.zuul.web.log.bean.SystemLog;
public class RequestUtils {
	Log log = LogFactory.getLog(RequestUtils.class);
	public static SystemLog getSystemLog(HttpServletRequest request){
		SystemLog log = new SystemLog();
		log.setOperator(request.getRemoteUser());
		log.setUri(request.getRequestURI());
		log.setModule("预留");
		log.setClientIp(request.getRemoteHost());
		log.setVisitorTime(DateUtils.formatISO(new Date()));
		return log;
	}
}
