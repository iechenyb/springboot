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
 * 日期: 2018-11-23 09:10:14
 */
@DBEntity("t_cf_param_setting")
public class ParamSettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")
	@ApiModelProperty(value="主键，请求时参数忽略")
	private Integer id;
			
	@DBColumn("param_name")
	@ApiModelProperty(value="指标名称,仅更新或者查询时，传参数！")
	private String paramName;
	
	
	@DBColumn("param_identy")
	@ApiModelProperty(value="指标标识,仅更新或者查询时，传参数！")
	private String paramIdenty;
			
	
	@DBColumn("weight")
	@ApiModelProperty(value="权重,仅更新或者查询时，传参数！")
	private Integer weight;
	
			
	@DBColumn("memo")
	@ApiModelProperty(value="备注说明,仅更新或者查询时，传参数！")
	private String memo;
	
	@DBColumn("create_time")
	@ApiModelProperty(value="创建时间,忽略！")
	private String createTime;
	
			
	@DBColumn("update_time")
	@ApiModelProperty(value="更新时间,忽略！")
	private String updateTime;
	
	
	//设置值方法
	public void setId(Integer id) {
		this.id = id;
	}
	//获取值方法
	public Integer getId() {
		return id;
	}
	//设置值方法
	public void setParamIdenty(String paramIdenty) {
		this.paramIdenty = paramIdenty;
	}
	//获取值方法
	public String getParamIdenty() {
		return paramIdenty;
	}
	//设置值方法
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	//获取值方法
	public String getParamName() {
		return paramName;
	}
	//设置值方法
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	//获取值方法
	public Integer getWeight() {
		return weight;
	}
	//设置值方法
	public void setMemo(String memo) {
		this.memo = memo;
	}
	//获取值方法
	public String getMemo() {
		return memo;
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
}
