package com.example.config;

import java.net.SocketTimeoutException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import com.example.service.GreetingServices;
import com.example.service.User;
import com.example.service.UserServices;
import com.example.service.XmlServices;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月3日
 */
public class CxfClient {
	public static void main(String[] args) {
		/*xml();
		noLogin();*/
		/*user();
		needPassword();*/
		qNameTest();
	}
//通过配置文件  配置ws
	public static void xml(){
		XmlServices service = (XmlServices) getWsBean("xmlService?wsdl",XmlServices.class);
		System.out.println("通过xml配置服务："+service.sayHello("dddddd"));
	}
	
	public static Object getWsBean(String address,Class<?> clazz){
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();  
        factoryBean.setAddress("http://localhost:8002/services/userServices?wsdl");  
        factoryBean.getInInterceptors().add(new ClientLoginInterceptor("cyb", "111"));
        factoryBean.setServiceClass(clazz);
        return factoryBean.create();
	}
	
	//https://www.cnblogs.com/davidwang456/archive/2013/03/17/2964084.html
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 不可用<br>
	 *创建时间: 2017年7月15日hj12
	 */
	public static void qNameTest(){
		    QName SERVICE_NAME  = new QName("http://localhost:8002/services/", "userServices");
	        Service service = Service.create(SERVICE_NAME);
	        UserServices hw = service.getPort(UserServices.class);
	        System.out.println(hw.sayHello("11111"));
	}
	
	//需要密码,返回复杂对象，透明解析
	public static void user(){
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();  
        factoryBean.setAddress("http://localhost:8002/services/userServices?wsdl");  
        factoryBean.setServiceClass(UserServices.class);
        factoryBean.setPassword("cyb");
        factoryBean.setUsername("111");
        Object obj = factoryBean.create();  
        UserServices service = (UserServices) obj;  
        try {  
            User user = service.queryUser("iechenyb");
            System.out.println("res: " + user.getUserName()+"=="+user.getMapData()+"==="+user.getListData());  
        } catch(Exception e) {  
            if (e instanceof WebServiceException   
                    && e.getCause() instanceof SocketTimeoutException) {  
                System.err.println("This is timeout exception.");  
            } else {  
                e.printStackTrace();  
            }  
        }  
	}
	//免密码
	public static void noLogin(){
		GreetingServices service = (GreetingServices)getWsBean("http://localhost:8002/services/greetingServices?wsdl", GreetingServices.class);
        try {  
            User user = service.queryUser("iechenyb");//返回值为对象
            System.out.println("返回user对象: " + user.getUserName()+"=="+user.getUserAge());  
            System.out.println("普通的字符串返回："+service.sayHello("common str!"));
        } catch(Exception e) {  
            if (e instanceof WebServiceException   
                    && e.getCause() instanceof SocketTimeoutException) {  
                System.err.println("This is timeout exception.");  
            } else {  
                e.printStackTrace();  
            }  
        }  
	}
	//需要用户名密码，已调试通
	public static void needPassword() {
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://localhost:8002/services/userServices?wsdl");
		// 需要密码的情况需要加上用户名和密码
		client.getOutInterceptors().add(new ClientLoginInterceptor("cyb","111"));
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
			//objects = client.invoke("sayHello", "Leftso");//返回字符串
			objects = client.invoke("queryUser", "chenyuanbao");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}
}
