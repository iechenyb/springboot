package com.cyb.condition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 不要service标签，根据condition创建<br>
 *创建时间: 2018年3月7日
 */
//@Service
public class LinuxConditionService implements ConditionService{
	Log log = LogFactory.getLog(LinuxConditionService.class);

	@Override
	public String show() {
		return "linux service!";
	}
}
