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
 * 日期: 2018-12-03 15:26:15
 */
@DBEntity("t_cf_data_import_log")
public class DataImportLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
	
			
	@DBColumn("operator")
	@ApiModelProperty(value="操作员,仅更新或者查询时，传参数！")
	private String operator;
	
			
	@DBColumn("select_start_date")
	@ApiModelProperty(value="选择导入开始日期,仅更新或者查询时，传参数！")
	private String selectStartDate;
	
			
	@DBColumn("select_end_date")
	@ApiModelProperty(value="选择导入的结束日期,仅更新或者查询时，传参数！")
	private String selectEndDate;
	
			
	@DBColumn("import_data_type")
	@ApiModelProperty(value="导入数据类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期,仅更新或者查询时，传参数！")
	private Integer importDataType;
	
			
	@DBColumn("import_status")
	@ApiModelProperty(value="导入状态 0：正在导入 1：导入完成 2：导入失败,仅更新或者查询时，传参数！")
	private Integer importStatus;
	
			
	@DBColumn("import_mode")
	@ApiModelProperty(value="导入方式 0：自动  1：手动,仅更新或者查询时，传参数！")
	private Integer importMode;
	
			
	@DBColumn("import_data_numbers")
	@ApiModelProperty(value="导入的记录条数,仅更新或者查询时，传参数！")
	private Integer importDataNumbers;
	
			
	@DBColumn("operation_start_time")
	@ApiModelProperty(value="操作开始时间,仅更新或者查询时，传参数！")
	private String operationStartTime;
	
			
	@DBColumn("operation_end_time")
	@ApiModelProperty(value="操作结束时间,仅更新或者查询时，传参数！")
	private String operationEndTime;
	
	
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
	public void setImportDataType(Integer importDataType) {
		this.importDataType = importDataType;
	}
	//获取值方法
	public Integer getImportDataType() {
		return importDataType;
	}
	//设置值方法
	public void setImportStatus(Integer importStatus) {
		this.importStatus = importStatus;
	}
	//获取值方法
	public Integer getImportStatus() {
		return importStatus;
	}
	//设置值方法
	public void setImportMode(Integer importMode) {
		this.importMode = importMode;
	}
	//获取值方法
	public Integer getImportMode() {
		return importMode;
	}
	//设置值方法
	public void setImportDataNumbers(Integer importDataNumbers) {
		this.importDataNumbers = importDataNumbers;
	}
	//获取值方法
	public Integer getImportDataNumbers() {
		return importDataNumbers;
	}
	public String getSelectStartDate() {
		return selectStartDate;
	}
	public void setSelectStartDate(String selectStartDate) {
		this.selectStartDate = selectStartDate;
	}
	public String getSelectEndDate() {
		return selectEndDate;
	}
	public void setSelectEndDate(String selectEndDate) {
		this.selectEndDate = selectEndDate;
	}
	public String getOperationStartTime() {
		return operationStartTime;
	}
	public void setOperationStartTime(String operationStartTime) {
		this.operationStartTime = operationStartTime;
	}
	public String getOperationEndTime() {
		return operationEndTime;
	}
	public void setOperationEndTime(String operationEndTime) {
		this.operationEndTime = operationEndTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
