package com.kiiik.web.khfl.reportCaculate.bean;

import java.io.Serializable;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据库实体Bean
 * 
 * 作者: iechenyb 邮件: zzuchenyb@sina.com 日期: 2018-12-04 15:57:09
 */
@DBEntity("t_cf_day_lj")
public class KhflMidDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys = true)
	@DBColumn(value = "id", insertIfNull = "default")
	@ApiModelProperty(value = "主键，请求时参数忽略")
	private Integer id;

	@DBColumn("fund_account")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private String fundAccount;

	@DBColumn("record_date")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private String recordDate;

	@DBColumn("contract_nums")
	@ApiModelProperty(value = "客户每天的持仓数据，不同合约,仅更新或者查询时，传参数！")
	private Integer contractNums;

	@DBColumn("contract_nums_total")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Integer contractNumsTotal;

	@DBColumn("fxd")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Double fxd;

	@DBColumn("fxd_total")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Double fxdTotal;

	@DBColumn("cc_nums")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Integer ccNums;

	@DBColumn("cc_nums_total")
	@ApiModelProperty(value = "穿仓天数累计值,仅更新或者查询时，传参数！")
	private Integer ccNumsTotal;

	@DBColumn("qp_nums")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Integer qpNums;

	@DBColumn("qp_days_total")
	@ApiModelProperty(value = "强平天数累计值,仅更新或者查询时，传参数！")
	private Integer qpDaysTotal;

	@DBColumn("zb_nums")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Integer zbNums;

	@DBColumn("zb_days_total")
	@ApiModelProperty(value = "追加保证金天数,仅更新或者查询时，传参数！")
	private Integer zbDaysTotal;

	@DBColumn("phqp_nums")
	@ApiModelProperty(value = ",仅更新或者查询时，传参数！")
	private Integer phqpNums;

	@DBColumn("phqp_days_total")
	@ApiModelProperty(value = "配合强平天数,仅更新或者查询时，传参数！")
	private Integer phqpDaysTotal;

	@DBColumn("has_trade")
	@ApiModelProperty(value = "是否有交易,仅更新或者查询时，传参数！")
	private Integer hasTrade;

	@DBColumn("trade_days_total")
	@ApiModelProperty(value = "交易日累计,仅更新或者查询时，传参数！")
	private Integer tradeDaysTotal;

	// 设置值方法
	public void setId(Integer id) {
		this.id = id;
	}

	// 获取值方法
	public Integer getId() {
		return id;
	}

	// 设置值方法
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	// 获取值方法
	public String getFundAccount() {
		return fundAccount;
	}

	// 设置值方法
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	// 获取值方法
	public String getRecordDate() {
		return recordDate;
	}

	// 设置值方法
	public void setContractNums(Integer contractNums) {
		this.contractNums = contractNums;
	}

	// 获取值方法
	public Integer getContractNums() {
		return contractNums;
	}

	// 设置值方法
	public void setContractNumsTotal(Integer contractNumsTotal) {
		this.contractNumsTotal = contractNumsTotal;
	}

	// 获取值方法
	public Integer getContractNumsTotal() {
		return contractNumsTotal;
	}

	// 设置值方法
	public void setFxd(Double fxd) {
		this.fxd = fxd;
	}

	// 获取值方法
	public Double getFxd() {
		return fxd;
	}

	// 设置值方法
	public void setFxdTotal(Double fxdTotal) {
		this.fxdTotal = fxdTotal;
	}

	// 获取值方法
	public Double getFxdTotal() {
		return fxdTotal;
	}

	// 设置值方法
	public void setCcNums(Integer ccNums) {
		this.ccNums = ccNums;
	}

	// 获取值方法
	public Integer getCcNums() {
		return ccNums;
	}

	// 设置值方法
	public void setCcNumsTotal(Integer ccNumsTotal) {
		this.ccNumsTotal = ccNumsTotal;
	}

	// 获取值方法
	public Integer getCcNumsTotal() {
		return ccNumsTotal;
	}

	// 设置值方法
	public void setQpNums(Integer qpNums) {
		this.qpNums = qpNums;
	}

	// 获取值方法
	public Integer getQpNums() {
		return qpNums;
	}

	// 设置值方法
	public void setQpDaysTotal(Integer qpDaysTotal) {
		this.qpDaysTotal = qpDaysTotal;
	}

	// 获取值方法
	public Integer getQpDaysTotal() {
		return qpDaysTotal;
	}

	// 设置值方法
	public void setZbNums(Integer zbNums) {
		this.zbNums = zbNums;
	}

	// 获取值方法
	public Integer getZbNums() {
		return zbNums;
	}

	// 设置值方法
	public void setZbDaysTotal(Integer zbDaysTotal) {
		this.zbDaysTotal = zbDaysTotal;
	}

	// 获取值方法
	public Integer getZbDaysTotal() {
		return zbDaysTotal;
	}

	// 设置值方法
	public void setPhqpNums(Integer phqpNums) {
		this.phqpNums = phqpNums;
	}

	// 获取值方法
	public Integer getPhqpNums() {
		return phqpNums;
	}

	// 设置值方法
	public void setPhqpDaysTotal(Integer phqpDaysTotal) {
		this.phqpDaysTotal = phqpDaysTotal;
	}

	// 获取值方法
	public Integer getPhqpDaysTotal() {
		return phqpDaysTotal;
	}

	// 设置值方法
	public void setHasTrade(Integer hasTrade) {
		this.hasTrade = hasTrade;
	}

	// 获取值方法
	public Integer getHasTrade() {
		return hasTrade;
	}

	// 设置值方法
	public void setTradeDaysTotal(Integer tradeDaysTotal) {
		this.tradeDaysTotal = tradeDaysTotal;
	}

	// 获取值方法
	public Integer getTradeDaysTotal() {
		return tradeDaysTotal;
	}
}
