package com.kiiik.vas.example.bean;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月16日
 */
public class Person {
	private int number;
	private String id;
	private String name;
	public Person(String id,String name){
		this.id = id;
		this.name = name;
		this.number = Integer.valueOf(id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
