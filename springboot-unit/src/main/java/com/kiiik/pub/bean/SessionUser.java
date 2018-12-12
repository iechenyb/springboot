package com.kiiik.pub.bean;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月18日
 */
public class SessionUser {

	private String userName;
	private String empNo;
	private String userId;

	public SessionUser(String userName,String empNo,String userId){
		this.userName = userName;
		this.empNo = empNo;
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
