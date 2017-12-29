package com.cyb.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "web_yh_jh")
@Entity
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String jhlx;
	public String jhbh;
	public String xh;
	//存储时间
	private String time;//用于查看
	@Column(length=2000)
	public String content;

	public String getJhlx() {
		return jhlx;
	}

	public void setJhlx(String jhlx) {
		this.jhlx = jhlx;
	}

	public String getJhbh() {
		return jhbh;
	}

	public void setJhbh(String jhbh) {
		this.jhbh = jhbh;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
