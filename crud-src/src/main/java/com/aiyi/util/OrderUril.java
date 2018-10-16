package com.aiyi.util;

import java.io.Serializable;

public class OrderUril implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3174122552086978599L;
	private static int num = 0;
	
	public static int getIntegerOrder(){
		return new Integer(DateUtil.formatPramm("yyMMdd") + getNum());
	}

	private static int getNum() {
		// TODO Auto-generated method stub
		return ++num;
	}
}
