package com.cyb.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "web_yh_jh_type")
@Entity
public class PlanType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String jhlx;//一级分类 pk10 等等
	public String jhbh;
	public String url;
	public String jhmc;//计划名称

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJhmc() {
		return jhmc;
	}

	public void setJhmc(String jhmc) {
		this.jhmc = jhmc;
	}

}
