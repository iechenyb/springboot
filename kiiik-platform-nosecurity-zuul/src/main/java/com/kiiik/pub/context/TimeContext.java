package com.kiiik.pub.context;
import org.springframework.util.StringUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public class TimeContext {
	
	private static ThreadLocal<Long> timeHolder = new ThreadLocal<Long>();
	
	public static void setTime(long time){
		timeHolder.set(time);
	}
	
	public static long getTime(){
		if(StringUtils.isEmpty(timeHolder.get())){
			return 0L;
		}
		long time = timeHolder.get();
		timeHolder.remove();
		return time;
	}
	
}
