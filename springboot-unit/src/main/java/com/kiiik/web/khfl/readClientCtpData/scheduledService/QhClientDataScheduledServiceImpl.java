package com.kiiik.web.khfl.readClientCtpData.scheduledService;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportMode;
import com.kiiik.web.khfl.readClientCtpData.constants.ReadClientDataConstants;
import com.kiiik.web.khfl.readClientCtpData.service.QhClientDataServiceImpl;

/**
 * 
 *方法描述: 定时获取期货客户CTP数据<br>
 *创建时间: 2018-11-21
 *@return
 */
@Component
@EnableScheduling
public class QhClientDataScheduledServiceImpl {
	Log log = LogFactory.getLog(QhClientDataScheduledServiceImpl.class);
	
	@Autowired
	QhClientDataServiceImpl qhClientDataServiceImpl;
	
	//定时导入客户日资金数据
	//@Scheduled(cron = "00 16 15 ? * MON-FRI")
	public void getDayFundsDataScheduled(){
		log.info("开始定时任务>获取客户日资金数据  "+System.currentTimeMillis());
		try{
			Long date= ConcurrentDateUtil.date2long8(new Date());
			Long longDateArr[] = {date,date};
		    qhClientDataServiceImpl.getDayFundsDataByDate(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum(),longDateArr);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户日资金数据  "+System.currentTimeMillis());
	}
	
	//定时导入客户日强平记录
	//@Scheduled(cron = "00 56 15 ? * MON-FRI")
	public void getDayForceTradeScheduled(){
		log.info("开始定时任务>获取客户日强平记录 "+System.currentTimeMillis());
		try{
			Long date= ConcurrentDateUtil.date2long8(new Date());
			Long longDateArr[] = {date,date};
		    qhClientDataServiceImpl.getDayForceTradeByDate(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum(),longDateArr);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户日强平记录  "+System.currentTimeMillis());
	}
	
	//定时导入客户日持仓明细
	//@Scheduled(cron = "00 31 16 ? * MON-FRI")
	public void getDayPositionDetailScheduled(){
		log.info("开始定时任务>获取客户日持仓明细 "+System.currentTimeMillis());
		try{
			Long date= ConcurrentDateUtil.date2long8(new Date());
			Long longDateArr[] = {date,date};
		    qhClientDataServiceImpl.getPositionDetail(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum(),longDateArr);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户日持仓明细  "+System.currentTimeMillis());
	}
	
	//定时导入客户日成交明细
	//@Scheduled(cron = "00 31 16 ? * MON-FRI")
	public void getDayTransDetailScheduled(){
		log.info("开始定时任务>获取客户日成交明细 "+System.currentTimeMillis());
		try{
			Long date= ConcurrentDateUtil.date2long8(new Date());
			Long longDateArr[] = {date,date};
		    qhClientDataServiceImpl.getTransDetail(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum(),longDateArr);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户日成交明细  "+System.currentTimeMillis());
	}
	
	//定时导入客户信息
	//@Scheduled(cron = "00 44 16 ? * MON-FRI")
	public void getCurrInvestorScheduled(){ 
		log.info("开始定时任务>获取客户信息 "+System.currentTimeMillis());
		try{
		    qhClientDataServiceImpl.getCurrInvestorDetail(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户信息  "+System.currentTimeMillis());
	}
	
	//定时导入客户属性信息
	//@Scheduled(cron = "00 44 16 ? * MON-FRI")
	public void getCurrInvPropertiesScheduled(){ 
		log.info("开始定时任务>获取客户属性信息 "+System.currentTimeMillis());
		try{
		    qhClientDataServiceImpl.getCurrInvProperties(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户属性信息  "+System.currentTimeMillis());
	}
	
	//定时导入客户开始交易日期
	//@Scheduled(cron = "00 44 16 ? * MON-FRI")
	public void getClientStatrtDateScheduled(){ 
		log.info("开始定时任务>获取客户开始交易日期 "+System.currentTimeMillis());
		try{
		    qhClientDataServiceImpl.getClientStatrtDate(ReadClientDataConstants.AUTO_IMPORT_OPERATOR,DataImportMode.AUTO_IMPORT.getStateNum());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info("完成定时任务>获取客户开始交易日期"+System.currentTimeMillis());
	}
	

}
