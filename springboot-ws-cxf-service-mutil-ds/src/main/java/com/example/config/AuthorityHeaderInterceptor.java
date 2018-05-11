package com.example.config;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月4日
 */
public class AuthorityHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage>{

	public AuthorityHeaderInterceptor(String phase) {
		super(phase);
		// TODO Auto-generated constructor stub
		
	}/*  
    private AuthorityParameter authorityParameter;
    public AuthorityHeaderInterceptor(AuthorityParameter authorityParameter) {  
        super(Phase.PREPARE_SEND);  
        this.authorityParameter = authorityParameter;
    }   
    public void handleMessage(SoapMessage msg) throws Fault {    
        List<Header> headers = msg.getHeaders();  
        //创建Document对象  
        Document doc = DOMUtils.createDocument();  

        //配置服务器端Head信息的用户密码  
        Element eleId= doc.createElement(this.authorityParameter.getUserNameKey());  
        eleId.setTextContent(this.authorityParameter.getUserNameValue());  
        Element elePass = doc.createElement(this.authorityParameter.getPasswordKey());  
        elePass.setTextContent(this.authorityParameter.getPasswordValue());   
        *//** 
         * 也可以先创建一个父节点，则生成的XML文档 ，我们这里是直接使用用户名和密码
         * <authHeader> 
         *      <userId>lzw</userId> 
         *      <userPass>123456</userPass> 
         * </authHeader> 
         *//*  
        headers.add(new Header(new QName(""), eleId));  
        headers.add(new Header(new QName(""), elePass)); 
    }   
*/

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		// TODO Auto-generated method stub
		
	}}  
