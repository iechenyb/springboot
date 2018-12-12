package com.kiiik.web.khfl.reportCaculate.bean;

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
 * 日期: 2018-11-29 15:20:37
 */
@DBEntity("t_cf_report_form")
public class ReportFormEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("fund_account")
	@ApiModelProperty(value="资产账号,仅更新或者查询时，传参数！")
	private String fundAccount;
	
			
	@DBColumn("user_name")
	@ApiModelProperty(value="客户姓名,仅更新或者查询时，传参数！")
	private String userName;
	
			
	@DBColumn("circle_type")
	@ApiModelProperty(value="周期类别 1：月度 2：季度 3：半年度 4：年度,仅更新或者查询时，传参数！")
	private String circleType;
	
			
	@DBColumn("start_date")
	@ApiModelProperty(value="开始日期,仅更新或者查询时，传参数！")
	private String startDate;
	
			
	@DBColumn("end_date")
	@ApiModelProperty(value="记录日期,仅更新或者查询时，传参数！")
	private String endDate;
	
			
	@DBColumn("grade")
	@ApiModelProperty(value="等级,仅更新或者查询时，传参数！")
	private String grade;
	
			
	@DBColumn("score")
	@ApiModelProperty(value="总分,仅更新或者查询时，传参数！")
	private Double score;
	
			
	@DBColumn("contract_spirit")
	@ApiModelProperty(value="契约精神,仅更新或者查询时，传参数！")
	private Integer contractSpirit;
	
			
	@DBColumn("match_rate")
	@ApiModelProperty(value="配合率,仅更新或者查询时，传参数！")
	private Double matchRate;
	
			
	@DBColumn("average_risk_rate")
	@ApiModelProperty(value="平均风险率,仅更新或者查询时，传参数！")
	private Double averageRiskRate;
	
			
	@DBColumn("marget_call_rate")
	@ApiModelProperty(value="追保发生率,仅更新或者查询时，传参数！")
	private Double margetCallRate;
	
			
	@DBColumn("forced_closing_rate")
	@ApiModelProperty(value="强平发生率,仅更新或者查询时，传参数！")
	private Double forcedClosingRate;
	
			
	@DBColumn("throughwear_times")
	@ApiModelProperty(value="穿仓次数,仅更新或者查询时，传参数！")
	private Integer throughwearTimes;
	
			
	@DBColumn("decentra_investment")
	@ApiModelProperty(value="分散投资,仅更新或者查询时，传参数！")
	private Double decentraInvestment;
	
			
	@DBColumn("average_risk_growth_rate")
	@ApiModelProperty(value=",仅更新或者查询时，传参数！")
	private Double averageRiskGrowthRate;
	
			
	@DBColumn("market_situation")
	@ApiModelProperty(value="市场行情 1：低风险 2：中风险 3：高风险,仅更新或者查询时，传参数！")
	private Integer marketSituation;
	
			
	@DBColumn("note")
	@ApiModelProperty(value="备注。如果是VIP/休眠/交易未满一个月的则备注，否则为空,仅更新或者查询时，传参数！")
	private String note;
	
	
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
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//获取值方法
	public String getUserName() {
		return userName;
	}
	//设置值方法
	public void setCircleType(String circleType) {
		this.circleType = circleType;
	}
	//获取值方法
	public String getCircleType() {
		return circleType;
	}
	//设置值方法
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	//获取值方法
	public String getStartDate() {
		return startDate;
	}
	//设置值方法
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	//获取值方法
	public String getEndDate() {
		return endDate;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	//设置值方法
	public void setScore(Double score) {
		this.score = score;
	}
	//获取值方法
	public Double getScore() {
		return score;
	}
	//设置值方法
	public void setContractSpirit(Integer contractSpirit) {
		this.contractSpirit = contractSpirit;
	}
	//获取值方法
	public Integer getContractSpirit() {
		return contractSpirit;
	}
	//设置值方法
	public void setMatchRate(Double matchRate) {
		this.matchRate = matchRate;
	}
	//获取值方法
	public Double getMatchRate() {
		return matchRate;
	}
	//设置值方法
	public void setAverageRiskRate(Double averageRiskRate) {
		this.averageRiskRate = averageRiskRate;
	}
	//获取值方法
	public Double getAverageRiskRate() {
		return averageRiskRate;
	}
	//设置值方法
	public void setMargetCallRate(Double margetCallRate) {
		this.margetCallRate = margetCallRate;
	}
	//获取值方法
	public Double getMargetCallRate() {
		return margetCallRate;
	}
	//设置值方法
	public void setForcedClosingRate(Double forcedClosingRate) {
		this.forcedClosingRate = forcedClosingRate;
	}
	//获取值方法
	public Double getForcedClosingRate() {
		return forcedClosingRate;
	}
	//设置值方法
	public void setThroughwearTimes(Integer throughwearTimes) {
		this.throughwearTimes = throughwearTimes;
	}
	//获取值方法
	public Integer getThroughwearTimes() {
		return throughwearTimes;
	}
	//设置值方法
	public void setDecentraInvestment(Double decentraInvestment) {
		this.decentraInvestment = decentraInvestment;
	}
	//获取值方法
	public Double getDecentraInvestment() {
		return decentraInvestment;
	}
	//设置值方法
	public void setAverageRiskGrowthRate(Double averageRiskGrowthRate) {
		this.averageRiskGrowthRate = averageRiskGrowthRate;
	}
	//获取值方法
	public Double getAverageRiskGrowthRate() {
		return averageRiskGrowthRate;
	}
	//设置值方法
	public void setMarketSituation(Integer marketSituation) {
		this.marketSituation = marketSituation;
	}
	//获取值方法
	public Integer getMarketSituation() {
		return marketSituation;
	}
	//设置值方法
	public void setNote(String note) {
		this.note = note;
	}
	//获取值方法
	public String getNote() {
		return note;
	}
}
