package com.cyb.app.sk.po;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 秒杀用户信息
 * @author DHUser
 */
@Entity  
@Table(name="t_sk_user")  
public class User {  
    private Long id ;  
    private String username;  
    private Date birthday;  
    private String sex ;  
    private String address;  
    private String password;
    private String token;
    
    @Id  
    @GeneratedValue(strategy=GenerationType.AUTO)  
    @Column(name="id")  
    public Long getId() {  
        return id;  
    }  
    public void setId(Long id) {  
        this.id = id;  
    }  
    @Column(name="username")  
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String username) {  
        this.username = username;  
    }  
    @Column(name="birthday")  
    public Date getBirthday() {  
        return birthday;  
    }  
    public void setBirthday(Date birthday) {  
        this.birthday = birthday;  
    }  
    @Column(name="sex")  
    public String getSex() {  
        return sex;  
    }  
    public void setSex(String sex) {  
        this.sex = sex;  
    }  
    @Column(name="adress")  
    public String getAddress() {  
        return address;  
    }  
    public void setAddress(String address) {  
        this.address = address;  
    }  
    @Override  
    public String toString() {  
        return "User [id=" + id + ", username=" + username + ", birthday="  
                + birthday + ", sex=" + sex + ", address=" + address + "]";  
    }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}  
  
}  