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
 * 日期: 2018-11-15 16:41:56
 */
@DBEntity("t_cf_day_funds")
public class DayFundsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value="资产账号,仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("record_date")
	@ApiModelProperty(value="日期,仅更新或者查询时，传参数！")
	private Integer recordDate;
	
			
	@DBColumn("khqy")
	@ApiModelProperty(value="客户权益,仅更新或者查询时，传参数！")
	private Double khqy;
	
			
	@DBColumn("fxd")
	@ApiModelProperty(value="风险度,仅更新或者查询时，传参数！")
	private Double fxd;
	
			
	@DBColumn("zjbzj")
	@ApiModelProperty(value="追加保证金,仅更新或者查询时，传参数！")
	private Double zjbzj;
	
	
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

	public Integer getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Integer recordDate) {
		this.recordDate = recordDate;
	}
	//设置值方法
	public void setKhqy(Double khqy) {
		this.khqy = khqy;
	}
	//获取值方法
	public Double getKhqy() {
		return khqy;
	}
	//设置值方法
	public void setFxd(Double fxd) {
		this.fxd = fxd;
	}
	//获取值方法
	public Double getFxd() {
		return fxd;
	}
	//设置值方法
	public void setZjbzj(Double zjbzj) {
		this.zjbzj = zjbzj;
	}
	//获取值方法
	public Double getZjbzj() {
		return zjbzj;
	}
}
