package com.kiiik.vas.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AntPathMatcher;

/**
 * 作者 : iechenyb<br>
 * 类描述:get 请求没有 request body 所以 json请求只能用post<br>
 * 创建时间: 2018年5月15日
 */
public class UriMatcherTest {
	static AntPathMatcher matcher = new AntPathMatcher();
	static List<String> PatternList = new ArrayList<String>();
	static List<String> UriList = new ArrayList<String>();

	public static void main(String[] args) {
		initPattern();
		initUri();
		for (String pattern : PatternList) {
			System.out.println("pattern:" + pattern);
			System.out.println("-------------------------------------------");
			for (String uri : UriList) {
				System.out.println(matcher.match(pattern, uri) + "\t\t\t" + uri);
			}
		}
	}

	public static void initUri() {
		UriList.add("/user/add");
		UriList.add("/user/delete");
		UriList.add("/user/id/1");
		UriList.add("/user/add/main.jsp");
		UriList.add("/any.jsp");
		UriList.add("/first/second/third/fourth/five/any.jsp");
	}

	public static void initPattern() {
		PatternList.add("/*/**");
		PatternList.add("/user/*");//仅仅匹配两层
		PatternList.add("/user/**");//匹配以user为根的无限层级
		PatternList.add("/user/id/**");
		PatternList.add("/user/{id}/{name}");
		PatternList.add("/*.jsp");
		PatternList.add("/*/**/*.jsp");
	}
}
