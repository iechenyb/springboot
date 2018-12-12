package com.kiiik.zuul.web.log.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kiiik.zuul.web.log.repository.SystemLogRepository;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 */
public class SystemLogService {
	Log log = LogFactory.getLog(SystemLogService.class);
	@Autowired
	SystemLogRepository logRes;
}
