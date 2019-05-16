package com.cyb.web;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年2月1日
 */
public class Test {
	Log log = LogFactory.getLog(Test.class);
	 public static void main(String[] args) {
	        String str1 = "I";
	        str1 += "love"+"java";       // 1)
	        str1 = str1+"love"+"java";      //2)
	         int i=0;
	         i++;
	         ++i;
	    }
}
