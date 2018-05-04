package com.example.config;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月4日
 */
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.JAXWSMethodInvoker;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.BeanInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;  
  
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage>{  
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);  
    private SAAJInInterceptor saa = new SAAJInInterceptor();  
  
    private static final String USER_NAME = "cyb";  
    private static final String USER_PASSWORD = "111";  
  
    public AuthInterceptor() {  
        super(Phase.PRE_PROTOCOL);  
        getAfter().add(SAAJInInterceptor.class.getName());  
    }  
  
    @Override  
    public void handleMessage(SoapMessage message) throws Fault {  
        SOAPMessage mess = message.getContent(SOAPMessage.class);  
     // 获取到当前服务的接口名称,这里如果不需要也可以不要，我这里纯属是为了解决我们自己的业务问题
        Exchange exchange = message.getExchange();  
        Service service = exchange.get(Service.class);
        Class<?>[] interfaces = 
        		((JAXWSMethodInvoker)service.getInvoker()).getServiceObject(exchange).getClass().getInterfaces();    
        if (mess == null) {  
            saa.handleMessage(message);  
            mess = message.getContent(SOAPMessage.class);  
        }  
        SOAPHeader head = null;  
        try {  
            head = mess.getSOAPHeader();  
        } catch (Exception e) {  
            logger.error("getSOAPHeader error: {}",e.getMessage(),e);  
        }  
        if (head == null) {  
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));  
        }  
  
        NodeList users = head.getElementsByTagName("username");  
        NodeList passwords = head.getElementsByTagName("password");  
        if (users.getLength() < 1) {  
            throw new Fault(new IllegalArgumentException("找不到用户信息"));  
        }  
        if (passwords.getLength() < 1) {  
            throw new Fault(new IllegalArgumentException("找不到密码信息"));  
        }  
  
        String userName = users.item(0).getTextContent().trim();  
        String password = passwords.item(0).getTextContent().trim(); 
        validateAuth(interfaces,userName,password);
        if(USER_NAME.equals(userName) && USER_PASSWORD.equals(password)){  
            logger.debug("admin auth success");  
        } else {  
            SOAPException soapExc = new SOAPException("认证错误");  
            logger.debug("admin auth failed");  
            throw new Fault(soapExc);  
        }  
    }  
    /**
     * 校验用户权限
     * @param clazzs 用户发布的服务类所实现的接口列表
     * @param loginName
     * @param password
     * @return
     */
    private boolean validateAuth(Class<?>[] clazzs, String loginName, String password) {
        // 这里写相关的权限校验代码
    	System.out.println("开始校验逻辑！");
    	return true;
    }
}  
