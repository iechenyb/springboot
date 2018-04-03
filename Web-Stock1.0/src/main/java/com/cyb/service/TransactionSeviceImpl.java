package com.cyb.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月3日
 */
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyb.dao.FlightRepository;
import com.cyb.dao.MessageRepository;
import com.cyb.date.DateUtil;
import com.cyb.po.Flight;
import com.cyb.po.Message;
@Service
public class TransactionSeviceImpl {
	Log log = LogFactory.getLog(TransactionSeviceImpl.class);
	
	@Autowired
	FlightRepository flightDao;
	
	@Autowired
	MessageRepository msgDao;
	
	@PersistenceContext
	private EntityManager entityManager; 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveFlight(Flight f){
		//flightDao.save(f);
		//flightDao.saveAndFlush(f);
		entityManager.persist(f);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveMessage( Message msg){
		//msgDao.save(msg);
		entityManager.persist(msg);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveMessageAtWrong( Message msg) throws Exception{
		//msgDao.save(msg);
		entityManager.persist(msg);
		System.out.println(1/0);
		//throw new ArrayIndexOutOfBoundsException("短信发送失败");
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 测试   使用默认事务  和新建事务
	 * 在调用的方法内部使用事务，saveInOneMethod无transaction注解<br>
	 *创建时间: 2017年7月15日hj12
	 *
	 *测试结果期望值  航班订单保存成功，保存订单信息，短信发送失败不存信息。
	 *测试实际结果：航班和短信发送记录均回滚，未保存！
	 *@param f
	 *@throws Exception
	 *原因 用this调用的，不是走的代理对象
	 *通过代理对象可以不在当前方法添加事务注解的情况下，使用代理方法自定义的事务类型
	 *需要去掉 <tx:method name="*"  propagation="REQUIRED"  rollback-for="Exception"></tx:method> 
	 *否则不能单独生效
	 */
	public void saveInOneMethod(Flight f,int hasException ) throws Exception{
		TransactionSeviceImpl proxy = (TransactionSeviceImpl) AopContext.currentProxy();
		proxy.saveFlight(f);
		Message m = new Message();
		m.setMsg("009 ordered success！");
		m.setFid(f.getId());
		m.setTime(DateUtil.date2long14());
		if(hasException==1){
			//try{
				proxy.saveMessageAtWrong(m);//检测是否会新创建事务
			/*}catch(Exception e){//不catch的话，全部回滚
				e.printStackTrace();
			}*/
		}else{
			proxy.saveMessage(m);
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param f 没有实际的事务可用 save* 没有声明式事务，看配置文件
	 *@param hasException
	 *@throws Exception
	 */
	public void saveInOneMethodCommon(Flight f,int hasException ) throws Exception{
		saveFlight(f);
		Message m = new Message();
		m.setMsg("009 ordered success！");
		m.setFid(f.getId());
		m.setTime(DateUtil.date2long14());
		if(hasException==1){
			try{
				saveMessageAtWrong(m);//检测是否会新创建事务
			}catch(Exception e){//catch 之后都保存，否则都回滚！
			    System.out.println(e.toString());
			}
		}else{
			saveMessage(m);
		}
	}
	//save* 没有声明式事务，看配置文件，所以加事务注解 添加事务，如下方法在同一个事务里
	@Transactional
	public void saveInOneMethodCommon1(Flight f,int hasException ) throws Exception{
		saveFlight(f);
		Message m = new Message();
		m.setMsg("009 ordered success！");
		m.setFid(f.getId());
		m.setTime(DateUtil.date2long14());
		if(hasException==1){
			try{
				saveMessageAtWrong(m);//检测是否会新创建事务
			}catch(Exception e){//catch 之后都保存，否则都回滚！
			    System.out.println(e.toString());
			}
		}else{
			saveMessage(m);
		}
	}
}
