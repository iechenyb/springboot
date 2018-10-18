package com.kiiik.web.system.po;

import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
@DBEntity("t_sys_role")
public class Role {
	/**
	 * @作者:iechenyb</br>
	 * @功能描述：</br>
	 * @创建时间：2016年11月1日下午4:57:10</br>
	 */
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	private Integer id; 
	
	@DBColumn("rolename")
    private String roleName;  
	
	@DBColumn("description")
    private String description;
    
    
    public Role() {  
        super();  
    }  
    

    public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getRoleName() {  
        return roleName;  
    }  
  
    public void setRoleName(String roleName) {  
        this.roleName = roleName;  
    }  
  
   
    public String getDescription() {  
        return description;  
    }  
  
    public void setDescription(String description) {  
        this.description = description;  
    }
	
}
