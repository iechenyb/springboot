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
@DBEntity("t_cf_day_position_detail")
public class DayPositionDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value=",仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("record_date")
	@ApiModelProperty(value="日期,仅更新或者查询时，传参数！")
	private Integer recordDate;
	
			
	@DBColumn("contract")
	@ApiModelProperty(value="合约代码,仅更新或者查询时，传参数！")
	private String contract;
	
			
	@DBColumn("buy_positions")
	@ApiModelProperty(value="买持仓,仅更新或者查询时，传参数！")
	private Double buyPositions;
	
			
	@DBColumn("sell_positions")
	@ApiModelProperty(value="卖持仓,仅更新或者查询时，传参数！")
	private Double sellPositions;
	
	
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
	public void setContract(String contract) {
		this.contract = contract;
	}
	//获取值方法
	public String getContract() {
		return contract;
	}
	//设置值方法
	public void setBuyPositions(Double buyPositions) {
		this.buyPositions = buyPositions;
	}
	//获取值方法
	public Double getBuyPositions() {
		return buyPositions;
	}
	//设置值方法
	public void setSellPositions(Double sellPositions) {
		this.sellPositions = sellPositions;
	}
	//获取值方法
	public Double getSellPositions() {
		return sellPositions;
	}
}
