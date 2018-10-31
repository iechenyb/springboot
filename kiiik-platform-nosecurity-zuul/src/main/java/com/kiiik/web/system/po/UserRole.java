package com.kiiik.web.system.po;

import java.io.Serializable;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@DBEntity("t_sys_user_role")
@ApiModel("用户角色关系")
public class UserRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @作者:iechenyb</br>
	 * @功能描述：</br>
	 * @创建时间：2016年11月3日下午1:31:07</br>
	 */
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	@ApiModelProperty(value="主键")
	private String id;  
	@DBColumn("userid")
	@ApiModelProperty("用户主键")
	private String userId; 
	@DBColumn("roleid")
	@ApiModelProperty("角色主键")
	private String roleId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	} 
	
}
