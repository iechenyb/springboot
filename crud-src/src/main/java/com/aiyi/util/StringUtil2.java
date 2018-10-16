package com.aiyi.util;

/**
 * 字符串操作工具类整理
 * @author 郭胜凯
 * @time 2016年6月3日下午2:21:40
 * @email 719348277@qq.com
 *
 */
public class StringUtil2 {

	/**
	 * 获取字符串摘要
	 * @param text 富文本字符串
	 * @param length 摘要长度
	 * @param omit 超出部分省略符
	 * @return
	 */
	public static String getPaper(String text, int length, String omit){
		text = text.replaceAll("</?[^>]+>", ""); 
		text = text.replaceAll("<a>\\s*|t|r|n</a>", "");
		
		if (text.length() > length) {
			return text.substring(0, length - omit.length()) + omit;
		}
		return text;
	}
}
