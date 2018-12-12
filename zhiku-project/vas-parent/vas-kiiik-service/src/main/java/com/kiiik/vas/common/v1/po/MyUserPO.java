package com.kiiik.vas.common.v1.po;
import com.kiiik.vas.common.v1.anno.DBColumn;
import com.kiiik.vas.common.v1.anno.DBEntity;
import com.kiiik.vas.common.v1.anno.KeyColumn;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月11日
 */

@DBEntity("tb_user")
public class MyUserPO {
 
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	private Integer id;
	
	@DBColumn("account") 
	private String account;
	
	@DBColumn("name") 
	private String name;
	
	@DBColumn(value="password",insertIfNull="'456789'")	
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
