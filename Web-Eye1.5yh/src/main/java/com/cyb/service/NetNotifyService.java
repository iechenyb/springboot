package com.cyb.service;
import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cyb.dao.NetMonitorRepository;
import com.cyb.dao.NetNotifyDaoImpl;
import com.cyb.dao.NetNotifyRepository;
import com.cyb.date.DateUtil;
import com.cyb.po.NetMonitor;
import com.cyb.po.NetNotify;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月18日
 */
@Service
public class NetNotifyService {
	Log log = LogFactory.getLog(NetNotifyService.class);
	
	@Autowired
	private JavaMailSender sender;
	
	@Value(value = "${spring.boot.admin.notify.mail.to}")
    private String to = "1048417686@qq.com";
	
	@Value(value = "${spring.boot.admin.notify.mail.from}")
    private String from ="383065059@qq.com";
    
    @Autowired
    NetMonitorRepository netMonitory;
    
    @Autowired
    NetNotifyRepository notifyRep;
    
    @PersistenceContext
	private EntityManager entityManager;
    
    @Autowired
    NetNotifyDaoImpl netNotifyDao;
    
	public void sendMail(){
		String filePath = "d:/data/netresult.csv";
		List<NetMonitor> persons = netMonitory.findAll();
		//1查找需要通知的人员
		for(NetMonitor p:persons){
			try {
				//2查找当前人员已经通知的次数
			   if(notifyRep.getSendCountBySomeOne(p.getAccount(), p.getType()).size()<=3) {
				   //查询所有已经通知多的记录，通知超过3次则不通知
				   sendAttachmentsMail("公司系统监控提醒","公司系统监控提醒",filePath,p.getAccount());
			   }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	 public void sendAttachmentsMail(String subject, String content, String filePath,String to){  
	        MimeMessage message = sender.createMimeMessage();  
	        try {  
	            //true表示需要创建一个multipart message  
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
	            helper.setFrom(from);  
	            helper.setTo(to);  
	            helper.setSubject(subject);  
	            helper.setText(content, true);  
	  
	            FileSystemResource file = new FileSystemResource(new File(filePath));  
	            String fileName ="default.csv"; //filePath.substring(filePath.lastIndexOf(File.separator));  
	            helper.addAttachment(fileName, file);  
	              
	            sender.send(message);  
	            NetNotify notify = new NetNotify();
	            notify.setAccount(to);
	            notify.setMsg(subject);
	            notify.setType("email");
	            notify.setTime(DateUtil.date2long10());
//	            entityManager.save(notify);
//	            entityManager.persist(notify);
	            netNotifyDao.save(notify);
	        } catch (MessagingException e) { 
	        	e.printStackTrace();
	        }  
	    }  
}
