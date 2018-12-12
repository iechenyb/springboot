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
 * 日期: 2018-12-04 15:57:09
 */
@DBEntity("t_cf_data_cal_log")
public class ClassifyCalLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("operator")
	@ApiModelProperty(value="操作员或者定时任务,仅更新或者查询时，传参数！")
	private String operator;
	
			
	@DBColumn("start_date")
	@ApiModelProperty(value="计算开始日期,仅更新或者查询时，传参数！")
	private String startDate;
	
			
	@DBColumn("end_date")
	@ApiModelProperty(value="计算结束日期,仅更新或者查询时，传参数！")
	private String endDate;
	
	@DBColumn("circle_start_date")
	@ApiModelProperty(value="计算周期开始日期,仅更新或者查询时，传参数！")
	private String circleStartDate;
	
			
	@DBColumn("circle_end_date")
	@ApiModelProperty(value="计算周期结束日期,仅更新或者查询时，传参数！")
	private String circleEndDate;
	
			
	@DBColumn("status")
	@ApiModelProperty(value="执行状态 0 提交任务 1 正在执行 2执行成功 3执行失败,仅更新或者查询时，传参数！")
	private Integer status;
	
			
	@DBColumn("note")
	@ApiModelProperty(value="执行描述,仅更新或者查询时，传参数！")
	private String note;
	
			
	@DBColumn("year")
	@ApiModelProperty(value="年份,仅更新或者查询时，传参数！")
	private Integer year;
	
			
	@DBColumn("value")
	@ApiModelProperty(value="周期值,仅更新或者查询时，传参数！")
	private Integer value;
	
			
	@DBColumn("circle_type")
	@ApiModelProperty(value="周期 0 不固定 1 月 2季度 3半年 4年,仅更新或者查询时，传参数！")
	private Integer circleType;
	
			
	@DBColumn("record_date")
	@ApiModelProperty(value="记录日期,仅更新或者查询时，传参数！")
	private String recordDate;
	
	
	//设置值方法
	public void setId(Integer id) {
		this.id = id;
	}
	//获取值方法
	public Integer getId() {
		return id;
	}
	//设置值方法
	public void setOperator(String operator) {
		this.operator = operator;
	}
	//获取值方法
	public String getOperator() {
		return operator;
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
	//设置值方法
	public void setStatus(Integer status) {
		this.status = status;
	}
	//获取值方法
	public Integer getStatus() {
		return status;
	}
	//设置值方法
	public void setNote(String note) {
		this.note = note;
	}
	//获取值方法
	public String getNote() {
		return note;
	}
	//设置值方法
	public void setYear(Integer year) {
		this.year = year;
	}
	//获取值方法
	public Integer getYear() {
		return year;
	}
	//设置值方法
	public void setValue(Integer value) {
		this.value = value;
	}
	//获取值方法
	public Integer getValue() {
		return value;
	}
	//设置值方法
	public void setCircleType(Integer circleType) {
		this.circleType = circleType;
	}
	//获取值方法
	public Integer getCircleType() {
		return circleType;
	}
	//设置值方法
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	//获取值方法
	public String getRecordDate() {
		return recordDate;
	}
	public String getCircleStartDate() {
		return circleStartDate;
	}
	public void setCircleStartDate(String circleStartDate) {
		this.circleStartDate = circleStartDate;
	}
	public String getCircleEndDate() {
		return circleEndDate;
	}
	public void setCircleEndDate(String circleEndDate) {
		this.circleEndDate = circleEndDate;
	}
	
}
