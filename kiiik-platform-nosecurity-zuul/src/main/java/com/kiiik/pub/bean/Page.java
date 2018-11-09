package com.kiiik.pub.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年10月18日
 */
@ApiModel("分页信息")
public class Page {
	@ApiModelProperty("第n页")
	@Min(1)
	@NotNull(message="不可以为空")
	private Integer pageNum;
	
	@ApiModelProperty("每页记录数")
	@NotNull(message="不可以为空")
	@Min(1)
	private Integer pageSize;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		if(pageNum==null){
			this.pageNum = 1;
		}
	}

	public Integer getPageSize() {
		if(pageSize==null){
			return 20;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
