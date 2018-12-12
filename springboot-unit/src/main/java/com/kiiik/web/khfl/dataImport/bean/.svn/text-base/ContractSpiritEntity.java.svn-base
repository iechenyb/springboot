package com.kiiik.web.khfl.dataImport.bean;

import java.io.Serializable;
import java.util.Date;

import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据库实体Bean
 * 
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-12 10:07:27
 */
@DBEntity("t_cf_contract_spirit")
public class ContractSpiritEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("record_date")
	@ApiModelProperty(value="日期,仅更新或者查询时，传参数！")
	private String recordDate;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value="资产账号,仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("contract_spirit")
	@ApiModelProperty(value="契约精神 1：具备 0：不具备,仅更新或者查询时，传参数！")
	private String contractSpirit;
	
			
	@DBColumn("reason")
	@ApiModelProperty(value="依据,仅更新或者查询时，传参数！")
	private String reason;
	
	@DBColumn(value = "is_verify",insertIfNull="0")		
	@ApiModelProperty(value="是否审核通过 ,忽略！")
	private String isVerify;
	
			
	@DBColumn("create_time")
	@ApiModelProperty(value="创建时间,忽略！")
	private String createTime;
	
			
	@DBColumn("update_time")
	@ApiModelProperty(value="更新时间,忽略！")
	private String updateTime;
	
			
	@DBColumn("verify_time")
	@ApiModelProperty(value="审核时间,忽略！")
	private String verifyTime;
	
			
	@DBColumn("verify_id")
	@ApiModelProperty(value="审核人ID,忽略！")
	private String verifyId;
	
	
	//设置值方法
	public void setId(Integer id) {
		this.id = id;
	}
	//获取值方法
	public Integer getId() {
		return id;
	}
	//设置值方法
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	//获取值方法
	public String getRecordDate() {
		return recordDate;
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
	public void setContractSpirit(String contractSpirit) {
		this.contractSpirit = contractSpirit;
	}
	//获取值方法
	public String getContractSpirit() {
		return contractSpirit;
	}
	//设置值方法
	public void setReason(String reason) {
		this.reason = reason;
	}
	//获取值方法
	public String getReason() {
		return reason;
	}
	//设置值方法
	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}
	//获取值方法
	public String getIsVerify() {
		return isVerify;
	}
	//设置值方法
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	//获取值方法
	public String getCreateTime() {
		return createTime;
	}
	//设置值方法
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	//获取值方法
	public String getUpdateTime() {
		return updateTime;
	}
	//设置值方法
	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}
	//获取值方法
	public String getVerifyTime() {
		return verifyTime;
	}
	//设置值方法
	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}
	//获取值方法
	public String getVerifyId() {
		return verifyId;
	}
}
