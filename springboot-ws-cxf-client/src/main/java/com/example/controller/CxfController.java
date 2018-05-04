package com.example.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月3日
 */

import com.example.service.User;
import com.example.service.UserServices;
@RequestMapping("cxf")
@RestController
public class CxfController {
	Log log = LogFactory.getLog(CxfController.class);
	
	@Autowired
	JaxWsDynamicClientFactory factory;
	
	@Autowired
	UserServices userService;
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: < 20ms  <br>
	 *创建时间: 2017年7月15日hj12
	 *@param name
	 *@return
	 */
	@GetMapping("common")
	@ResponseBody
	public String commReq(String name){
		return "普通请求";
	}
	
	@GetMapping("user")
	@ResponseBody
	public User user(String name){
		return userService.queryUser(name);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: first 2s  other <320ms<br>
	 *创建时间: 2017年7月15日hj12
	 *@param name
	 *@return
	 */
	@GetMapping("sayHello")
	@ResponseBody
	public String sayHello(String name){
		long t1 = System.currentTimeMillis();
		Client client = factory.createClient("http://localhost:8002/services/greetingServices?wsdl");
		long t2 = System.currentTimeMillis();
		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,
		// PASS_WORD));
		System.out.println("耗时 "+(t2-t1));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("sayHello", "Leftso");
			long t3 = System.currentTimeMillis();
			System.out.println("返回数据:" + objects[0]+" 耗时"+(t3-t2));
			return objects[0].toString();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
