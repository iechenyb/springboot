package com.kiiik.zuul.web.log.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月17日
 *只有操作正常的请求才会记录日志
 */
@Entity
@Table(name="system_log")
public class SystemLog {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  public Integer id;
	  
	  public String clientIp;//客户端ip
	  public String visitorTime;//访问时间
	  public String uri;//请求路径
	  public String operator;//操作员 //操作员职工号 必须唯一！！！！
	  public String module;//模块名称 module为uri的前缀  比如 uri /system/user 则 module为/system 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getVisitorTime() {
		return visitorTime;
	}
	public void setVisitorTime(String visitorTime) {
		this.visitorTime = visitorTime;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("操作员["+operator+"]");
		sb.append("在["+visitorTime+"]使用IP["+clientIp+"]访问了");
		sb.append("模块["+module+"]的");
		sb.append("请求地址["+uri+"]。");
		return sb.toString();
	}  
	  
}
