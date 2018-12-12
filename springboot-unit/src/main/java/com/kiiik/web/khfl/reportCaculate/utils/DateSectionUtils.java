package com.kiiik.web.khfl.reportCaculate.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
import com.kiiik.web.khfl.reportCaculate.vo.CycleCalVO;


public class DateSectionUtils {
	private static ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>();
	private static ThreadLocal<SimpleDateFormat> dateFormLocal = new ThreadLocal<SimpleDateFormat>();
	public static CalDate calSection(CycleCalVO vo) throws NumberFormatException, Exception{
		return calDateSection(vo.getType(),Integer.valueOf(vo.getYear()),vo.getVal());
	} 
	public static String getDateLastDay(String year, String month) {
		calendarThreadLocal.set(Calendar.getInstance());
		Calendar calendar = calendarThreadLocal.get();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(calendarThreadLocal.get().getTime());
	}
	public static String getDateFirstDay(String year, String month) {
		calendarThreadLocal.set(Calendar.getInstance());
		Calendar calendar = calendarThreadLocal.get();
		calendar.set(Calendar.YEAR, Integer.parseInt(year));
		calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(calendarThreadLocal.get().getTime());
	}
	public static String getDateFirstDay(String yyyymmdd) {
		calendarThreadLocal.set(Calendar.getInstance());
		Calendar calendar = calendarThreadLocal.get();
		calendar.set(Calendar.YEAR, Integer.parseInt(yyyymmdd.substring(0,4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(yyyymmdd.substring(4,6))-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(calendarThreadLocal.get().getTime());
	}
	final static int MONTH=1;
	final static int SEASON=2;
	final static int HALFYEAR=3;
	final static int YEAR=4;
	/*
	 * type=0 月 1季度2半年 3年份
	 * year 年份
	 * value 值 
	 *  月份 1-12
	 *  季度 1-3
	 *  半年 1 2
	 *  年份 空
	 */
	public static CalDate calDateSection(int type,int year,String value) throws Exception{
		String start;
		String end;
		switch(type){
		case MONTH:
			 start = getDateFirstDay(String.valueOf(year), value);
			 end = getDateLastDay(String.valueOf(year), value);
			break;
		case SEASON:
			    int startMonth = 3*(Integer.valueOf(value)-1)+1;
			    int endMonth = 3*Integer.valueOf(value);
			    start = getDateFirstDay(String.valueOf(year), String.valueOf(startMonth));
				end = getDateLastDay(String.valueOf(year), String.valueOf(endMonth));
			 break;
		case HALFYEAR:
			 start = getDateFirstDay(String.valueOf(year), value.equals("1") ? "1":"7");
			 end = getDateLastDay(String.valueOf(year), value.equals("1") ? "6":"12");
			 break;
		case YEAR:
			 start = getDateFirstDay(String.valueOf(year), "1");
			 end = getDateLastDay(String.valueOf(year), "12");
			 break;
		default:
			start = "";
			end = "";
		}
		return new CalDate(start,end);
	}
	/**
	 * 根据当前周期参数计算上个周期
	 * @throws Exception 
	 */
	public static  CalDate lastCycyleDate(CycleCalVO vo) throws Exception{
		//当前周期最后一天
		String curCycleLastDay = null ;
		switch(vo.getType()){
		case MONTH:
			 curCycleLastDay = getDateLastDay(vo.getYear(), vo.getVal());
			 return lastCycleSection(curCycleLastDay,vo);
		case SEASON:
			  int endMonth = 3*Integer.valueOf(vo.getVal());//1 2 3 4 季度
			  curCycleLastDay = getDateLastDay(vo.getYear(), String.valueOf(endMonth));
		      return lastCycleSection(curCycleLastDay,vo);
		case HALFYEAR:
			curCycleLastDay = getDateLastDay(vo.getYear(), vo.getVal().equals("1") ? "6":"12");
			 return lastCycleSection(curCycleLastDay,vo);
		case YEAR:
			curCycleLastDay = getDateLastDay(vo.getYear(), "12");
			 return lastCycleSection(curCycleLastDay,vo);
		default:
		}
		return new CalDate("","");
	} 
	
	public static Calendar calendar(String yyyymmddd) throws Exception{
		try {
			calendarThreadLocal.set(Calendar.getInstance());
			if(yyyymmddd.length()!=8){
				throw new Exception("日期必须为8位！");
			}else{
				yyyymmddd = yyyymmddd+"000000";
			}
			
			int year = Integer.valueOf(yyyymmddd.substring(0,4));
			int month = Integer.valueOf(yyyymmddd.substring(4,6));
			int day = Integer.valueOf(yyyymmddd.substring(6,8));
			int hour,min,sec;
			hour = min = sec =0;
			calendarThreadLocal.get().set(year, month-1,day,hour,min,sec);
			return calendarThreadLocal.get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			calendarThreadLocal.remove();
		}
	}
	
	public static  long days(String start,String end) throws Exception{
		long times = calendar(end).getTimeInMillis()-calendar(start).getTimeInMillis();
		long days = times/(1000*3600*24);
		return days;
	}
	//根据当前周期末日期计算上一个周期
	public static CalDate lastCycleSection(String yyyymmdd,CycleCalVO vo) throws Exception{
		dateFormLocal.set(new SimpleDateFormat("yyyyMMdd"));
		String lastCycleEndDate = "" ;
		String lastCycleStartDate = "";
		Calendar cal = null ;
		switch(vo.getType()){
		case MONTH:
			cal = calendar(yyyymmdd);
			cal.add(Calendar.MONTH, -1);//上月
			lastCycleEndDate = dateFormLocal.get().format(cal.getTime());
			cal = calendar(lastCycleEndDate);
			//cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, 1); 
			lastCycleStartDate = dateFormLocal.get().format(cal.getTime());
			break;
		case SEASON:
			cal = calendar(yyyymmdd);
			cal.add(Calendar.MONTH, -3);//上个季度
			lastCycleEndDate = dateFormLocal.get().format(cal.getTime());
			cal = calendar(lastCycleEndDate);
			cal.add(Calendar.MONTH, -2);
			cal.set(Calendar.DAY_OF_MONTH, 1); 
			lastCycleStartDate = dateFormLocal.get().format(cal.getTime());
			break;
		case HALFYEAR:
			cal = calendar(yyyymmdd);
			cal.add(Calendar.MONTH, -6);//上半年
			lastCycleEndDate = dateFormLocal.get().format(cal.getTime());
			cal = calendar(lastCycleEndDate);
			cal.add(Calendar.MONTH, -5);
			cal.set(Calendar.DAY_OF_MONTH, 1); 
			lastCycleStartDate = dateFormLocal.get().format(cal.getTime());
			break;
		case YEAR:
			cal = calendar(yyyymmdd);
			cal.add(Calendar.MONTH, -12);//上一年
			lastCycleEndDate = dateFormLocal.get().format(cal.getTime());
			cal = calendar(lastCycleEndDate);
			cal.add(Calendar.MONTH, -11);
			cal.set(Calendar.DAY_OF_MONTH, 1); 
			lastCycleStartDate = dateFormLocal.get().format(cal.getTime());
			break;
		default:
		}
		dateFormLocal.remove();
		return new CalDate(lastCycleStartDate,lastCycleEndDate);
	}
}
