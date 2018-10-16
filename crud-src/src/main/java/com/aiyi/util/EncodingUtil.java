package com.aiyi.util;

import java.io.UnsupportedEncodingException;

/**
 * 编码工具类
 * @author 郭胜凯
 * @time 2016年4月15日上午9:28:55
 * @email 719348277@qq.com
 */
public class EncodingUtil {

	/**
	 * 常用的几个编码，Tomcat默认编码
	 */
	public static final String ENCODING_IOS8859 = "ISO-8859-1";
	
	public static final String ENCODING_ASCII = "US-ASCII";
	
	/**
	 * 常用的几个编码，GBK编码
	 */
	public static final String ENCODING_GBK = "GBK";
	
	/**
	 * 常用的几个编码，GB2312编码
	 */
	public static final String ENCODING_GB2312 = "GB2312";
	
	/**
	 * 常用的几个编码，国际通用编码
	 */
	public static final String ENCODING_UTF8 = "UTF-8";
	
	/**
	 * 过滤并转义Html元素
	 * @param text 带有HTML标记的文本
	 * @return 转义HTML标记后的文本
	 */
	public static String filterHTML(String text){
		if(text == null)
			return text;        //这里先健壮一下，如果用户没有赋值的话，这样的话，就不会进行下面的代码了，返回value或者null都一样
		
		StringBuffer sb = new StringBuffer();
		
		char[] c = new char[text.length()];        
		text.getChars(0, text.length(), c, 0);
		for(int i = 0;i < c.length; i++){
			if(c[i]=='<')
				sb.append("&lt;");        //"<"转义后是&lt;
			if(c[i]=='>')
				sb.append("&gt;");        //">"转义后是&gt;
			if(c[i]=='&')
				sb.append("&amp;");       //"%"转义后是&amp;
			if(c[i]=='"')
				sb.append("&quot;");      //"""转义后是&quot;
			else{
				sb.append(c[i]);          //到这里可以确定不是要转义的html标记了，所以直接存进字符串缓冲区
			}
		}
		return sb.toString();        	  //html标记转义完成后，得到字符串缓冲区中的字符串
		
	}
	
	/**
	 * 将文本转换为UTF-8编码
	 * @param text 预备转换的文本
	 * @param encoding 文本的原编码
	 * @return 转换编码后的文本
	 */
	public static String toUtf8(String text, String encoding){
		return setEncoding(text, encoding, ENCODING_UTF8);
	}
	
	/**
	 * 将指定文本转换为UTF-8
	 * @param text 预备转换的文本
	 * @return 转换编码后的文本
	 */
	public static String toUtf8(String text){
		//return setEncoding(text, getEncoding(text), ENCODING_UTF8);
			return toUtf8(text, getEncoding(text));
	}
	
	/**
	 * 还原字符串的编码
	 * @param text 带还原的字符串
	 * @return 还原后的字符串
	 */
	public static String encodingStr(String text){
		if(text == null)
			return text;
		try {
			
			int ylength = text.getBytes().length;
			int javalength = text.getBytes("ISO-8859-1").length;
			if (ylength == javalength) {
				return new String(text.getBytes("ISO-8859-1"));
			}else{
				return new String(text.getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    //get的编码在这里是iso-8859-1，所以用这个编码把他还原成字节集
		return text;
	}
	
	/**
	 * 转换文本编码
	 * @param text 等待转换的文本
	 * @param encoding 文本编码
	 * @param doEncoding 目标编码
	 * @return 转换编码后的文本
	 */
	public static String setEncoding(String text, String encoding, String doEncoding){
		if(text == null)
			return text;
		if (encoding.equals(doEncoding)) {
			return text;
		}
		
		byte[] b;
		try {
			b = text.getBytes(encoding);
			return new String(b,doEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    //get的编码在这里是iso-8859-1，所以用这个编码把他还原成字节集
		return null;
	}
	
	/**
	 * 获取文本编码
	 * @param str 待获取编码的文本
	 * @return 文本的编码名称
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		encode = "US-ASCII";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		new RuntimeException("未能正确获取文本编码格式").printStackTrace();
		return "";
	} 

}
