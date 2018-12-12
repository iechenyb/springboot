package com.kiiik.web.khfl.dataImport.bean;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据库实体Bean
 * 
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-27 16:56:01
 */
@DBEntity("t_cf_validclient_startdate")
public class ValidclientStartdateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value=",仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("start_date")
	@ApiModelProperty(value="交易开始日期,仅更新或者查询时，传参数！")
	private String startDate;
	
	
	//设置值方法
	public void setId(Integer id) {
		this.id = id;
	}
	//获取值方法
	public Integer getId() {
		return id;
	}
	//设置值方法
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	//获取值方法
	public String getFundAccount() {
		return fundAccount;
	}
	//设置值方法
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	//获取值方法
	public String getStartDate() {
		return startDate;
	}
}
