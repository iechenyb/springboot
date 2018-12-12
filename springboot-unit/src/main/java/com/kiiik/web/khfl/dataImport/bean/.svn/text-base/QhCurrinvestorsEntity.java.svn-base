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
 * 日期: 2018-11-15 16:43:26
 */
@DBEntity("t_cf_qh_currinvestors")
public class QhCurrinvestorsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
    
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value="资产账号,仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("investor_name")
	@ApiModelProperty(value="投资者姓名,仅更新或者查询时，传参数！")
	private String investorName;
	
			
	@DBColumn("is_active")
	@ApiModelProperty(value="销户状态 0：销户 1：未销户,仅更新或者查询时，传参数！")
	private String isActive;
	
	
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
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	//获取值方法
	public String getInvestorName() {
		return investorName;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
