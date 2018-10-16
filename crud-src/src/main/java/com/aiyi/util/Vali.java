package com.aiyi.util;

import java.sql.Date;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证封装类
 * @author 郭胜凯
 * @time 2016年3月15日下午3:12:51
 * @email 719348277@qq.com
 */
public class Vali {

	/**
	 * 是否是数字型字符串
	 * @param text
	 * @return
	 */
	public static boolean isNumber(String text){
		return text.matches("[0-9]+");
	}
	
	/**
	 * 是否为整数格式
	 * @param obj
	 * @return
	 */
	public static boolean isInteger(Object obj){
		if (null == obj) {
			return false;
		}
		
		if (obj instanceof String) {
			try {
				Integer.valueOf((String)obj);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return obj instanceof Integer;
		
	}
	
	/**
	 * 是否为中文
	 * @param text
	 * @return
	 */
	public static boolean isChinese(String text){
		char[] ch = text.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				return true;
			}
		}
		return false;

	}
	/**
	 * 是否存在中文
	 * @param text
	 * @return
	 */
	public static boolean hasChinese(String text){
		char[] charArray = text.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证纯文本【不含任何符号，包括中文】
	 * @param text
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isPassword(String text, int minLength, int maxLength){
		
		if (!check(text, minLength, maxLength)) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("[A-Za-z]+[0-9]");
		Matcher matcher = pattern.matcher(text);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为网址
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url){
		
		if (null == url) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
		Matcher matcher = pattern.matcher(url);
		
		return matcher.matches();
	}
	
	/**
	 * 是否为指定长度的邮箱
	 * @param email
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isEmail(String email, int minLength, int maxLength){
		if (!check(email, minLength, maxLength)) {
			return false;
		}
		return isEmail(email);
	}
	
	/**
	 * 简单校验字符串
	 * @param text
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean check(String text, int minLength, int maxLength){
		if (null == text || text.length() < minLength) {
			return false;
		}
		return text.length() <= maxLength;
	}
	
	
	/**
	 * 是否为邮箱地址格式
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		
		if (null == email || "".equals(email)) {
			return false;
		}
		
		Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = emailPattern.matcher(email);

		return matcher.matches();

	}
	
	/**
	 * 是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj){
		if (null == obj) {
			return true;
		}
		if (obj instanceof Integer) {
			return 0 == (int) obj;
			
		}else if (obj instanceof Date) {
			return ((Date) obj).getTime() == 0;
			
		}else if (obj instanceof Long) {
			return 0 == (long) obj;
			
		}else if (obj instanceof String) {
			return 0 == ((String)obj).length();
			
		}else if (obj instanceof Boolean) {
			return !((boolean) obj);
			
		}else if (obj instanceof byte[]) {
			return 0 == ((byte[]) obj).length;
			
		}else if (obj instanceof Double) {
			return 0 == (double) obj;
			
		}else if (obj instanceof Float) {
			return 0 == (float) obj;
			
		}else if (obj instanceof Short) {
			return 0 == (short) obj;
			
		}else if (obj instanceof Time) {
			return 0 == ((Time) obj).getTime();
			
		}
		return false;
	}

}
