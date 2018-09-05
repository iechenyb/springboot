package com.kiiik.vas.example.model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.pub.bean.ResultBean;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月5日
 */
public class Test {
	Log log = LogFactory.getLog(Test.class);
	public static void main(String[] args) {
		ResultBean<String> r1 = new ResultBean<>();
		r1.data("456");
	}
}
