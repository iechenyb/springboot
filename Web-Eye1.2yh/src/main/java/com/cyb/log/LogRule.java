package com.cyb.log;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月5日
 */
public interface LogRule {
	public void saveExceptionLog(MyLog log);
	public void saveVistiorLog(MyLog log);
}
