package com.cyb.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "web_eye_result")
@Entity
public class NetResult  extends NetInfor{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
		
	//状态  0 成功  1 失败
	private int zt;
	
	//存储错误信息
	private String infor;
	
	//存储时间
	private String time;
	
	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getZt() {
		return zt;
	}

	public void setZt(int zt) {
		this.zt = zt;
	}

}
