package com.cyb.po;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 航班信息  两个操作合成一个操作<br>
 *创建时间: 2018年4月3日
 */
@Entity
@Table(name="tb_flight")
public class Flight {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public  Long id;
	public String name ;//航班名称
	public String time;//航班时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
