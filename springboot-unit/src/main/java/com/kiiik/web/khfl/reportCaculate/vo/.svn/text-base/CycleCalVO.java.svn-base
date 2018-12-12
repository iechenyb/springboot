package com.kiiik.web.khfl.reportCaculate.vo;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("固定周期计算请求参数")
public class CycleCalVO {
	@ApiModelProperty("参数类型  月-1 季-2  半年-3  年-4")
	private int type;
	
	@ApiModelProperty("年份 如2018")
	@NotBlank
	private String year;
	
	@ApiModelProperty("具体的周期单位  月1-12  季度（1,2,3,4） 半年（1,2） 年（空）")
    private String val;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
    
}
