package com.cyb.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * http://localhost:8080/project/uri
 * telnet localhost 8080
 * ping 127.0.0.1
 * @author DHUser
 *
 */
@Table(name = "web_eye_class")
@Entity
public class NetClass extends NetInfor{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

}
