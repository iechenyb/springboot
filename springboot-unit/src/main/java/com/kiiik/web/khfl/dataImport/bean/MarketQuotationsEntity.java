package com.kiiik.web.khfl.dataImport.bean;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

@DBEntity("t_cf_market_quotations")
public class MarketQuotationsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("record_date")
	@ApiModelProperty(value="日期,仅更新或者查询时，传参数！")
	private String recordDate;
	
			
	@DBColumn("risk_level")
	@ApiModelProperty(value="市场风险 1:低风险 2:中风险 3:高风险,仅更新或者查询时，传参数！")
	private String riskLevel;
	
	@DBColumn("reason")
	@ApiModelProperty(value="审核者ID，请求时参数忽略！")
	private String reason;
	
	@DBColumn(value = "is_verify",insertIfNull="0")		
	@ApiModelProperty(value="是否审核通过 0：待审核 1：通过 2：不通过,请求时参数忽略！")
	private String isVerify;
	
			
	@DBColumn("create_time")
	@ApiModelProperty(value="创建时间,请求时参数忽略！")
	private String createTime;
	
			
	@DBColumn("update_time")
	@ApiModelProperty(value="更新时间,请求时参数忽略！")
	private String updateTime;
	
			
	@DBColumn("verify_time")
	@ApiModelProperty(value="审核时间,请求时参数忽略！")
	private String verifyTime;
	
			
	@DBColumn("verify_id")
	@ApiModelProperty(value="审核者ID，请求时参数忽略！")
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
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	//获取值方法
	public String getRiskLevel() {
		return riskLevel;
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
	//设置值方法
	public void setReason(String reason) {
		this.reason = reason;
	}
	//获取值方法
	public String getReason() {
		return reason;
	}
	
	
}
