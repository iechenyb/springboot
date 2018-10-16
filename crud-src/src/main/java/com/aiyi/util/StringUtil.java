/**
 * 
 */
package com.aiyi.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 功能：字符串工具类
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class StringUtil {
	
	public static String _HREF_URL_REGEX = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#!]]*";
	public static final String EMPTY_STRING = "";
	private static char chars[] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char IntChars[] = "1234567890".toCharArray();
	/**
	 * 获得int
	 * @param val
	 * @return
	 */
	public static int getInt(String val){
		return getInt(val,-1);
	}
	/**
	 * 获得int
	 * @param val
	 * @return
	 */
	public static int getInt(Object val){
		if(val == null) return -1;
		return getInt(val.toString(),-1);
	}
	/**
	 * 获得int
	 * @param val
	 * @param def
	 * @return
	 */
	public static int getInt(Object val,int def){
		try{
			return Integer.parseInt(val.toString().trim());
		}catch(Exception e){
			return def;
		}
	}
	/**
	 * 获得long
	 * @param val
	 * @return
	 */
	public static long getLong(String val ) {
		
		return getLong(val,-1);
	}
	/**
	 * 获得long
	 * @param val
	 * @param def
	 * @return
	 */
	public static long getLong(String val,long def){
		try{
			return Long.parseLong(val.trim());
		}catch(Exception e){
			return -1;
		}
	}

	/**
	 * 获得double
	 * @param value
	 * @return
	 */
	public static double getDouble(String value){
		return getDouble(value,0.00);
	}
	/**
	 * 获得double
	 * @param value
	 * @param def
	 * @return
	 */
	public static double getDouble(String value,double def){
		try{
			return Double.valueOf(value).doubleValue();
		}catch(Exception e){
			return def;
		}
	}
	
	
	
	/**
	 * 获得BigDecimal
	 * @param str
	 * @return
	 */
	public static BigDecimal getBigDecimal(String str){
		try{
			BigDecimal bd = new BigDecimal(str);
			return bd;
		}catch(Exception e){
			return BigDecimal.valueOf(-1.00);
		}
	}
	
	/**
	 * 获得BigDecimal
	 * @param str
	 * @return
	 */
	public static BigDecimal getBigDecimal(String str,double def){
		try{
			BigDecimal bd = new BigDecimal(str);
			return bd;
		}catch(Exception e){
			return BigDecimal.valueOf(def);
		}
	}
	
	/**
	 * yes/no转换成true/false
	 * @param aValue
	 * @return
	 */
	public static final String getBooleanFromYesNo(String aValue) {
        if (aValue.equalsIgnoreCase("yes")) {
            return "true";
        }

        return "false";
    }
	
	/**
	 * 替换字符串
	 * @param text 原始文本
	 * @param replaced 要替换内容
	 * @param replacement 替换内容
	 * @return
	 */
	public static String replace(String text, String replaced, String replacement) {
        StringBuffer ret = new StringBuffer();
        String temp = text;

        while (temp.indexOf(replaced) > -1) {
            ret.append(temp.substring(0, temp.indexOf(replaced)) + replacement);

            temp = temp.substring(temp.indexOf(replaced) + replaced.length());
        }

        ret.append(temp);

        return ret.toString();
    }
	
	/**
	 * 字符串替换 正反向
	 * @param sIni
	 * @param sFrom
	 * @param sTo
	 * @param caseSensitiveSearch
	 * @return
	 */
	public static String replaceString(String sIni, String sFrom, String sTo, boolean caseSensitiveSearch) {
        int i = 0;
        String s = new String(sIni);
        StringBuffer result = new StringBuffer();

        if (caseSensitiveSearch) {
            i = s.indexOf(sFrom);
        } else {
            i = s.toLowerCase().indexOf(sFrom.toLowerCase());
        }

        while (i != -1) {
            result.append(s.substring(0, i));
            result.append(sTo);

            s = s.substring(i + sFrom.length());

            if (caseSensitiveSearch) {
                i = s.indexOf(sFrom);
            } else {
                i = s.toLowerCase().indexOf(sFrom.toLowerCase());
            }
        }

        result.append(s);

        return result.toString();
    }
	
	
	/**
	 * 删除不能成为文件名的字符
	 * @param fileStr
	 * @return
	 */
	public static String removeIllegalFileChars(String fileStr) {
        String replacedFileStr = fileStr;

        replacedFileStr = replace(replacedFileStr, " ", "_");
        replacedFileStr = replace(replacedFileStr, "&", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, "%", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, ",", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, ";", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, "/", "_");

        return replacedFileStr;
    }
	
	/**
	 * 获得字符串中的指点子字符串
	 * @param strIn
	 * @param start
	 * @param length
	 * @return
	 */
	public static String substring(String strIn, int start, int length){
        String strOut = null;

        if (strIn != null) {
            strOut = EMPTY_STRING;

            if (start < strIn.length() && length > 0) {
                if (start + length < strIn.length() ) {
                    strOut = strIn.substring(start, start + length );
                } else {
                    strOut = strIn.substring(start, strIn.length() );
                }
            }
        }

        return strOut;
    }
	/**
	 * 随机生成length位数字字符串
	 * @param length
	 * @return
	 */
	public static final String getRandomLengthDigit(int length){
		StringBuilder str=new StringBuilder();
		for(int i=0;i<length;i++){
			str.append(IntChars[(int)(Math.random()*10)]);
		}
		return str.toString();
	}
	/**
	 * 随即生成length位字符串
	 * @param length
	 * @return
	 */
	public static final String getRandomLengthString(int length){
        if(length < 1)
            return null;
        char ac[] = new char[length];
        for(int j = 0; j < ac.length; j++)
            ac[j] = chars[new Random().nextInt(71)];

        return new String(ac);
    }
	
	/**
	 * 随即生成i位中文
	 * @param i
	 * @return
	 */
	public static final String getRandomLengthChineseString(int length){
		StringBuilder sb = new StringBuilder();
		for (int i = length; i > 0; i--) {
			sb.append(getRandomChinese());
		}
		return sb.toString();
    }
	
	/**
	 * 随机产生中文,长度范围为start-end
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getRandomLengthChiness(int start, int end) {
		StringBuilder sb = new StringBuilder();
		int length = new Random().nextInt(end + 1);
		if (length < start) {
			return getRandomLengthChiness(start, end);
		} else {
			for (int i = 0; i < length; i++) {
				sb.append(getRandomChinese());
			}
		}
		return sb.toString();
	}
	
	/**
	 * 随机获得中文
	 * @return
	 */
	public static String getRandomChinese() {
		String str = null;
		int highPos, lowPos;
		Random random = new Random();;
		highPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = 161 + Math.abs(random.nextInt(93));
		byte[] b = new byte[2];
		b[0] = (new Integer(highPos)).byteValue();
		b[1] = (new Integer(lowPos)).byteValue();
		try {
			str = new String(b, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	
	/**
	 * 删除所有空格
	 * @param str
	 * @return
	 */
	public static String removeBlanks(String str){
        if(str == null || str.equals(EMPTY_STRING))
            return str;
        char ac[] = str.toCharArray();
        char ac1[] = new char[ac.length];
        int i = 0;
        for(int j = 0; j < ac.length; j++)
            if(!Character.isSpaceChar(ac[j]))
                ac1[i++] = ac[j];

        return new String(ac1, 0, i);
    }
	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp = EMPTY_STRING;
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
	/**
	 * 获得List的字符串连接
	 * @param list
	 * @param split可选连接符
	 * @return
	 */
	public static String getStringFromList(List<String> list,String...split){
		if(list==null){
			return EMPTY_STRING;
		}
		StringBuilder sb=new StringBuilder();
		for(int i=0,j=list.size();i<j;i++){
			sb.append(list.get(i));
			if(split!=null && split.length>0)sb.append(split[0]);
		}
		return sb.toString();
	}

	/**
	 * 连接对象
	 * @param objs
	 * @return
	 */
	public static String concat(Object...objs) {
		
		if(objs==null || objs.length<1)return EMPTY_STRING;
		StringBuffer sb = new StringBuffer();
		for(Object obj : objs){
			sb.append(obj==null?EMPTY_STRING:obj.toString());
		}
		return sb.toString();
	}

	

	
	/**
	 * 自由组合列表
	 * @param list
	 * @return
	 */
	public static List<String> combined(List<List<String>> list){
		if(list.size()==1){
			return list.get(0);
		}else
			if(list.size()==2){
				return combined(list.get(0),list.get(1));
			}
			else{
				List<String> tempList = new ArrayList<String>();
				for(int i=list.size();i>0;i--){
					tempList = combined(list.get(i-1),tempList);
				}
				return tempList;
			}
	}
	
	/**
	 * 自由组合两列表
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> combined(List<String> list1,List<String> list2){
		if(list2.isEmpty())return list1;
		List<String> results = new ArrayList<String>();
		for(String str1:list1){
			for(String str2:list2){
				results.add(str1+","+str2);
			}
		}
		return results;
	}


	/**
	 * 正则替换
	 * @param str 源字符串
	 * @param pattern 要替换的模式
	 * @param replace 替换成的内容
	 * @param connector 连接器
	 * @param def 默认值
	 * @return
	 */
	public static String regex(String str,String pattern,String replace,String connector,String def){
		if(str==null || pattern==null || connector==null)return null;
		Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		int i = 0 ;
		for(i=0;matcher.find();i++){
			if(i>0)sb.append(connector);
			matcher.appendReplacement(sb,replace);
		}
		if(i>0)matcher.appendTail(sb).append(def);else sb.append(str);
		return sb.toString();
	}
	
	
	

	

	/**
	 * 获得指定小数位
	 * @param value
	 * @param fixed
	 * @return
	 */
	public static BigDecimal getFixedBigDecimal(String value, int fixed) {
		
		try{
			BigDecimal result = new BigDecimal(value);
			return result.setScale(fixed, BigDecimal.ROUND_HALF_UP);
		}catch(Exception e){
			
		}
		return BigDecimal.ZERO;
	}

	
	
	
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirstChar(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
            .append(Character.toTitleCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }
	
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirstChar(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
            .append(Character.toLowerCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }
	
}
