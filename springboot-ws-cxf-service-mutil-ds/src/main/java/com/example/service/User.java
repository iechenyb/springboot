package com.example.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.*; 

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月3日
 */
@XmlRootElement(name = "UserInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "userName", "userAge","mapData","listData" })
public class User implements Serializable {
	/**
	 * 
	 */
	private String userName;
	private Integer userAge;
	private Map<String,Object> mapData;
	private List<Object> listData;
	private static final long serialVersionUID = 1L;

	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public Map<String, Object> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public List<Object> getListData() {
		return listData;
	}

	public void setListData(List<Object> listData) {
		this.listData = listData;
	}

}
