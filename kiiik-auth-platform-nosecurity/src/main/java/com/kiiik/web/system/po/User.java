package com.kiiik.web.system.po;
import com.kiiik.pub.mybatis.annotation.DBColumn;
import com.kiiik.pub.mybatis.annotation.DBEntity;
import com.kiiik.pub.mybatis.annotation.KeyColumn;
/**
 *作者 : iechenyb<br>
 *类描述: bean的字段基础类型都用包装类定义<br>
 *创建时间: 2018年10月18日
 */
@DBEntity("t_sys_user")
public class User {
	/**
	 * @作者:iechenyb</br>
	 * @功能描述：</br>
	 * @创建时间：2016年11月1日下午4:56:32</br>
	 */
	@KeyColumn(useGeneratedKeys=true)
	@DBColumn(value = "id",insertIfNull="default")	
	private Integer id;  
	
	@DBColumn("username")
    private String userName;  
	
	@DBColumn("empno")
    private String empNo;  
	
	@DBColumn("idcard")
    private String idCard; 
	
	@DBColumn("email")
    private String email; 
	
	@DBColumn("phone")
    private String phone; 
	
	@DBColumn("password")
    private String password; 
	
    
    @DBColumn("lastLoginTime")
    public Long lastLoginTime;
    @DBColumn("lastLoginIp")
    public String lastLoginIp;
    @DBColumn("loginIp")
    public String loginIp;
    @DBColumn("loginSum")
    public Integer loginSum;
    @DBColumn("isEffect")
    public Integer isEffect;
    @DBColumn("sex")
    public Integer sex;
    @DBColumn("operateID")
    public String operateID;
    public User() {  
        super();  
    }  
 
   
    public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	
    
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
   
	public Long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Integer getLoginSum() {
		return loginSum;
	}
	public void setLoginSum(Integer loginSum) {
		this.loginSum = loginSum;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public String getOperateID() {
		return operateID;
	}
	public void setOperateID(String operateID) {
		this.operateID = operateID;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Integer getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public String getLoginIp() {
		return loginIp;
	}


	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmpNo() {
		return empNo;
	}


	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}


	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	public void setSex(Integer sex) {
		this.sex = sex;
	}  
	
}
