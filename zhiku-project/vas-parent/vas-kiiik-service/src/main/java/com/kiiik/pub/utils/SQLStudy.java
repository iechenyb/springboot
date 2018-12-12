package com.kiiik.pub.utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.SQL;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月10日
 */
public class SQLStudy {
	Log log = LogFactory.getLog(SQLStudy.class);
	public static void main(String[] args) {
		org.apache.ibatis.jdbc.SQL a= new SQL();
		String a1 = a.DELETE_FROM("aaa").AND().WHERE("name=1").AND().WHERE("age=1").toString();
		System.out.println(a1);
		a = new SQL();
		a1 = a.SELECT("a,b,c,d").FROM("tb_user")
				.ORDER_BY("aaaa asc","ccc asc")
				.ORDER_BY("bbb desc").toString();
		System.out.println(a1);
		new SQL() {
            {
                SELECT("tutor_id as tutorId, name, email");
                FROM("tutors");
                WHERE("tutor_id=" + "1122");
            }
        }.toString();
        a = new SQL();
        a1 = a.ORDER_BY("aaa").toString();
        System.out.println(a1);
	}
}
