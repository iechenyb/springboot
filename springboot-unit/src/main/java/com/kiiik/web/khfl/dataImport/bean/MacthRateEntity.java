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
 * 日期: 2018-11-14 10:57:19
 */
@DBEntity("t_cf_client_match_rate")
public class MacthRateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("record_date")
	@ApiModelProperty(value="日期，强平通知日期,仅更新或者查询时，传参数！")
	private String recordDate;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value="资产账号,仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("process_result")
	@ApiModelProperty(value="处理结果 0：不配合   1：配合,仅更新或者查询时，传参数！")
	private String processResult;
	
	
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
	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	//获取值方法
	public String getProcessResult() {
		return processResult;
	}
}
