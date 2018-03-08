package com.cyb.po;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *作者 : iechenyb<br>
 *类描述: 通知者<br>
 *创建时间: 2017年12月18日
 */
@Entity
@Table(name="web_eye_monitor")
public class NetMonitor {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
	    
	    public String account;//通知对象  邮箱或者是手机号
	    
	    public String type;//email phone

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	    
	 
}
