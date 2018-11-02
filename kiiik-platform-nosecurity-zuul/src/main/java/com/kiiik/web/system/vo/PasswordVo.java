package com.kiiik.web.system.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月1日
 */
public class PasswordVo{
	@NotBlank(message="旧密码不能为空！")
	String oldPassword;
	@NotBlank(message="新密码不能为空！")
	String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}