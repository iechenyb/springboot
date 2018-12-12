package com.kiiik.pub.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *作者 : iechenyb<br>
 *类描述: 变量名不能全大写，使用驼峰命名<br>
 *创建时间: 2018年5月25日20:47:10
 */
@ApiModel(description="swagger命名规范")
public class BaseRQ {
	@ApiModelProperty(value="功能号",name="F",example="getUser")
	private String f;//纯大写不能生成
	
	@ApiModelProperty(value="请求唯一标记",name="ID",example="99")
	private String id;
	
	@ApiModelProperty(value="aaaCdDe",example="aaaCdDe")
	private String aaaCdDe;//小驼峰命名
	
	@ApiModelProperty(value="bbbBbbCdDe",example="bbbBbbCdDe")
	private String bbbBbbCdDe;//小驼峰命名
	
	@ApiModelProperty(value="userName",example="userName")
	private String userName;//小驼峰命名
	
	@ApiModelProperty(value="qDate",notes="qDate",example="qDate")
	private String qDate;//小驼峰命名
	
	@ApiModelProperty(value="ADate",notes="ADate",example="ADate")
	private String ADate;//大驼峰命名-常用与类名

	@ApiModelProperty(value="AaaBbbCcc",example="AaaBbbCcc")
	private String AaaBbbCcc;//大驼峰命名-常用与类名
	
	@ApiModelProperty(value="ABCD",example="ABCD")
	private String ABCD;//纯大写命名（不符合驼峰）
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getABCD() {
		return ABCD;
	}
	public void setABCD(String aBCD) {
		ABCD = aBCD;
	}
	public String getqDate() {
		return qDate;
	}
	public void setqDate(String qDate) {
		this.qDate = qDate;
	}
	
	public String getADate() {
		return ADate;
	}
	public void setADate(String aDate) {
		ADate = aDate;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAaaCdDe() {
		return aaaCdDe;
	}
	public void setAaaCdDe(String aaaCdDe) {
		this.aaaCdDe = aaaCdDe;
	}
	public String getBbbBbbCdDe() {
		return bbbBbbCdDe;
	}
	public void setBbbBbbCdDe(String bbbBbbCdDe) {
		this.bbbBbbCdDe = bbbBbbCdDe;
	}
	
}
