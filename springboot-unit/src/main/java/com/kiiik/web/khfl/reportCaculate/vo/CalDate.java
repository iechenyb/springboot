package com.kiiik.web.khfl.reportCaculate.vo;

import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("非固定周期计算时间参数")
public class CalDate{
	
	@ApiModelProperty("计算起始日期，yyyymmdd")
	String beginDate;
	
	@ApiModelProperty("计算结束日期，yyyymmdd")
	String endDate;
	public CalDate(){}
	
	public boolean check(){
		return StringUtils.isEmpty(beginDate)||StringUtils.isEmpty(endDate);
	}
	public CalDate(String start,String end){
		this.beginDate = start;
		this.endDate = end;
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String toString(){
		return this.beginDate+"->"+endDate;
	}
	
}
