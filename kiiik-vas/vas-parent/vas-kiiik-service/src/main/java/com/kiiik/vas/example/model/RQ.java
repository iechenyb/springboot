package com.kiiik.vas.example.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
@ApiModel(value="规模走势参数定义",description="规模走势参数定义")
public class RQ  extends BaseRQ{
	//extends BaseRQ
	@ApiModelProperty(value="品种信息",name="pc",example="[ag,cu]",notes="注意事项")
	private List<String> pc;//品种信息
	
	@ApiModelProperty(value="查询天数",name="v",example="大于0的整数")
	@NotNull
	@Size(min=2)
	private String v;//查询天数

	public List<String> getPc() {
		return pc;
	}

	public void setPc(List<String> pc) {
		this.pc = pc;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}
}
