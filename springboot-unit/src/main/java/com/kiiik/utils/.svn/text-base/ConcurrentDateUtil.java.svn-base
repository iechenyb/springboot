package com.kiiik.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年6月27日上午10:59:34
 */
public class ConcurrentDateUtil {
	public static final String PATTEN_OF_DATE= "yyyyMMddHHmmss";
	public static final String PATTEN_OF_DAY = "yyyyMMdd";
	public static final String PATTEN_OF_DAY2 = "yyyy/MM/dd";
	private static ThreadLocal<DateFormat> formattor = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};

	public static Date parse(String dateStr) {
		try {
			formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
			return formattor.get().parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}finally{
			formattor.remove();
		}
	}

	public static String format(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
		String dateString = formattor.get().format(date);
		formattor.remove();
		return dateString;
	}
	public static String mills() {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmss.SSS"));
		String dateString = formattor.get().format(new Date());
		formattor.remove();
		return dateString;
	}
	
	 public static String format(String pattern, Date date) {
		 formattor.set(new SimpleDateFormat(pattern));
		 String dateString = formattor.get().format(date);
	     formattor.remove();
		 return dateString;
	 }
	 
	 public static Date parse(String pattern, String dateStr) {
		 
		 try {
				formattor.set(new SimpleDateFormat(pattern));
				return formattor.get().parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}finally{
				formattor.remove();
			}
	    }
	
	public static Long date2long14(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMddHHmmss"));
		String dateString = formattor.get().format(date);
		Long time =  Long.valueOf(dateString);
		formattor.remove();
		return time;
	}
	public static Long date2long14(){
		return date2long14(new Date());
	}
	
	public static Long date2long8(Date date) {
		formattor.set(new SimpleDateFormat("yyyyMMdd"));
		String dateString = formattor.get().format(date);
		Long time =  Long.valueOf(dateString);
		formattor.remove();
		return time;
	}
	public static Long date2long8(){
		return date2long8(new Date());
	}
	
	public static String getLastMonthDate(String inDate){//date格式yyyymmdd
		Date date = parse(PATTEN_OF_DAY,inDate);
		String dateString;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		//calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();
		dateString = format(PATTEN_OF_DAY,date);
		return dateString;
	}
	
	public  static void main(String args[]){
		String l = "20180111";
		System.out.println(getLastMonthDate(l));
	}
	
}
