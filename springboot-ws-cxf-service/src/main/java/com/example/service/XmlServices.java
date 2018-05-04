package com.example.service;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月3日
 */
@WebService(targetNamespace = "http://service.example.com")
public interface XmlServices {
	
	@WebMethod
    String sayHello(@WebParam(name = "userName") String name);
	
	@WebMethod
	User queryUser(@WebParam(name = "name") String name) ;
}
