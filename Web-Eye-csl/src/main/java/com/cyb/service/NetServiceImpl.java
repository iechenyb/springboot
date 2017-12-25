package com.cyb.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.dao.NetResultDaoImpl;
import com.cyb.dao.NetResultRepository;
import com.cyb.net.PingTask;
import com.cyb.net.TelNetTask;
import com.cyb.net.UrlTask;
import com.cyb.po.NetClass;
import com.cyb.po.NetResult;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月15日
 */
@Service
public class NetServiceImpl {
	Log log = LogFactory.getLog(NetServiceImpl.class);
	
	@Autowired
	UrlTask urlTask;
	
	@Autowired
	PingTask pingTask;
	
	@Autowired
	TelNetTask telNetTask;
	
	@Autowired
	NetResultRepository rep;
	
	
	@Autowired
	NetResultDaoImpl dao;
	
	 
	public void saveUrlTest(String url){
		NetResult nr  = null;
		nr =urlTask.execute("http://www.baidu.com");
		dao.save(nr);
		NetResult nr_0 = pingTask.execute("www.baidu.com");
		dao.save(nr_0);
		NetResult nr_1 = telNetTask.execute("127.0.0.1","8080");
		dao.save(nr_1);
	}
	
	public void saveCommon(){
		try{
			NetResult nr  = null;
			nr = new NetResult();
			nr.setDomain("doamin");
			nr.setInfor("infor");
			nr.setIp("ip");
			nr.setName("name");
			nr.setTime("time");
			dao.save(nr);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Autowired
	NetNotifyService service;
	
	public void saveUrlTest(NetClass cls){
		NetResult nr = null;
		if(cls.getType().equals("url")){
			nr = urlTask.execute(cls);
		}else if(cls.getType().equals("port")){
			nr = telNetTask.execute(cls);
		}else if(cls.getType().equals("ip")){
			nr = pingTask.execute(cls);
		}else{
			System.out.println("参数类型错误！");
		}
		if(nr!=null){
			dao.save(nr);
		}
		if(nr.getZt()==0){
			service.sendMail();
		}
	}
	
	public List<NetResult> getList(){
		return  dao.getAll();
	}
}
