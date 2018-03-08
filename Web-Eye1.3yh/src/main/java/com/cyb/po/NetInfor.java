package com.cyb.po;

import javax.persistence.MappedSuperclass;
/**
 * http://localhost:8080/project/uri
 * telnet localhost 8080
 * ping 127.0.0.1
 * @author DHUser
 *
 */
@MappedSuperclass
public class NetInfor {
	
	//服务名称  比如某某管理系统
	private String name;
	
	//ip
	private String ip;
	
	//端口号
	private String port;
	
	
	private String url;
	
	//域名
	private String domain;
	
	//  port url ip  
	private String type;
	
	private String sysType;//操作系统类型
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

}
