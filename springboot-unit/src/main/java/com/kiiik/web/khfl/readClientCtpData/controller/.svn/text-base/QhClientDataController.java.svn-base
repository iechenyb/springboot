package com.kiiik.web.khfl.readClientCtpData.controller;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportMode;
import com.kiiik.web.khfl.readClientCtpData.service.QhClientDataServiceImpl;
import com.kiiik.web.khfl.readClientCtpData.vo.ImportStatus;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月18日上午11:12:35
 */
@RestController
@RequestMapping("qhClientData")
public class QhClientDataController extends BaseController{
	Log log = LogFactory.getLog(QhClientDataController.class);
	@Autowired
	QhClientDataServiceImpl qhClientDataServiceImpl;
	@Autowired
	GenericService genericService;
	
	/*
	 * 客户日资金数据导入
	 */
	@GetMapping("/getDayFunds")
	@ApiOperation("获取期货客户日资金数据")
	public ResultBean<Integer> getDayFundsDataByDate(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		/*
		 * 判断日期是否非空？
		 * 非空则返回“正在导入”，启动导入线程
		 * 空则返回“日期为空”
		 */
	    ExecutorService executor = Executors.newFixedThreadPool(1);
		if (startDate != null || endDate != null){
			final Long[] dataArr = stringtoLong(startDate,endDate);
			//获取用户EmpNo
			final String operator = getUser().getEmpNo(); 
			//final String operator = "qinxiang";
			log.info("数据导入操作员=="+operator);
			//启动导入线程
			executor.execute(new Runnable() {
				 @Override
		         public void run() {
					 qhClientDataServiceImpl.getDayFundsDataByDate(operator,DataImportMode.IMPORT_BY_HAND.getStateNum(),dataArr);
				 }
			});
		    return new ResultBean<Integer>().success("正在导入...");
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}
	}
	
	/*
	 * 客户日强平记录数据导入
	 */
	@GetMapping("/getDayForceTrade")
	@ApiOperation("获取客户日强平记录")
	public ResultBean<Integer> getDayForceTradeByDate(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		/*
		 * 判断日期是否非空？
		 * 非空则返回“正在导入”，启动导入线程
		 * 空则返回“日期为空”
		 */
	    ExecutorService executor = Executors.newFixedThreadPool(1);
		if (startDate != null || endDate != null){
			final Long[] dataArr = stringtoLong(startDate,endDate);
			//获取用户EmpNo
			final String operator = getUser().getEmpNo(); 
			//final String operator = "qinxiang";
			log.info("数据导入操作员=="+operator);
			//启动导入线程
			executor.execute(new Runnable() {
				 @Override
		         public void run() {
					 qhClientDataServiceImpl.getDayForceTradeByDate(operator,DataImportMode.IMPORT_BY_HAND.getStateNum(),dataArr);
				 }
			});
		    return new ResultBean<Integer>().success("正在导入...");
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}
	
	}
	
	/*
	 * 客户日持仓明细导入
	 */
	@GetMapping("/getPositionDetail")
	@ApiOperation("获取 客户日持仓明细")
	public ResultBean<Integer> getPositionDetail(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		/*
		 * 判断日期是否非空？
		 * 非空则返回“正在导入”，启动导入线程
		 * 空则返回“日期为空”
		 */
	    ExecutorService executor = Executors.newFixedThreadPool(1);
		if (startDate != null || endDate != null){
			final Long[] dataArr = stringtoLong(startDate,endDate);
			//获取用户EmpNo
			final String operator = getUser().getEmpNo(); 
			//final String operator = "qinxiang";
			log.info("数据导入操作员=="+operator);
			//启动导入线程
			executor.execute(new Runnable() {
				 @Override
		         public void run() {
					 qhClientDataServiceImpl.getPositionDetail(operator,DataImportMode.IMPORT_BY_HAND.getStateNum(),dataArr);
				 }
			});
		    return new ResultBean<Integer>().success("正在导入...");
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}
	}
	
	/*
	 * 客户日成交明细导入
	 */
	@GetMapping("/getTransDetail")
	@ApiOperation("获取客户日成交明细")
	public ResultBean<Integer> getTransDetail(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		/*
		 * 判断日期是否非空？
		 * 非空则返回“正在导入”，启动导入线程
		 * 空则返回“日期为空”
		 */
	    ExecutorService executor = Executors.newFixedThreadPool(1);
		if (startDate != null || endDate != null){
			final Long[] dataArr = stringtoLong(startDate,endDate);
			//获取用户EmpNo
			final String operator = getUser().getEmpNo(); 
			//final String operator = "qinxiang";
			log.info("数据导入操作员=="+operator);
			//启动导入线程
			executor.execute(new Runnable() {
				 @Override
		         public void run() {
					 qhClientDataServiceImpl.getTransDetail(operator,DataImportMode.IMPORT_BY_HAND.getStateNum(),dataArr);
				 }
			});
		    return new ResultBean<Integer>().success("正在导入...");
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}
	}

	/*
	* 客户信息表
	*/
	@GetMapping("/getCurrInvestors")
	@ApiOperation("获取客户基本信息")
	public ResultBean<Integer> getCurrInvestors(){
	    
	    ExecutorService executor = Executors.newFixedThreadPool(1);
		//获取用户EmpNo
		final String operator = getUser().getEmpNo(); 
		//final String operator = "qinxiang";
		log.info("数据导入操作员=="+operator);
		//启动导入线程
		executor.execute(new Runnable() {
			 @Override
	         public void run() {
				 qhClientDataServiceImpl.getCurrInvestorDetail(operator,DataImportMode.IMPORT_BY_HAND.getStateNum());
			 }
		});
	    return new ResultBean<Integer>().success("正在导入...");
	}
	
	/*
	 * 客户属性信息
	 */
	@GetMapping("/getCurrInvProperties")
	@ApiOperation("获取客户属性信息")
	public ResultBean<Integer> getCurrInvProperties(){
	 
	    ExecutorService executor = Executors.newFixedThreadPool(1);
			//获取用户EmpNo
			final String operator = getUser().getEmpNo(); 
			//final String operator = "qinxiang";
			log.info("数据导入操作员=="+operator);
			//启动导入线程
			executor.execute(new Runnable() {
				 @Override
		         public void run() {
					 qhClientDataServiceImpl.getCurrInvProperties(operator,DataImportMode.IMPORT_BY_HAND.getStateNum());
				 }
			});
		    return new ResultBean<Integer>().success("正在导入...");
	
	
	}
	
	/*
	 * 客户开始交易日期
	 */
	@GetMapping("/getClientStartDate")
	@ApiOperation("获取客户开始交易日期")
	public ResultBean<Integer> getClientStatrtDate(){
		ExecutorService executor = Executors.newFixedThreadPool(1);
		//获取用户EmpNo
		final String operator = getUser().getEmpNo(); 
		//final String operator = "qinxiang";
		log.info("数据导入操作员=="+operator);
		//启动导入线程
		executor.execute(new Runnable() {
			 @Override
	         public void run() {
				 qhClientDataServiceImpl.getClientStatrtDate(operator,DataImportMode.IMPORT_BY_HAND.getStateNum());
			 }
		});
	    return new ResultBean<Integer>().success("正在导入...");
	
	}
	
	/*
	 * 获取CTP数据导入最新状态信息
	 */
	@GetMapping("/getCtpDataImportStatus")
	@ApiOperation("获取CTP数据导入最新状态信息")
	public ResultBean<List<ImportStatus>> getCTPDataImportStatus(){
		//获取用户EmpNo
		String operator = getUser().getEmpNo(); 
		//String operator = "qinxiang";
		log.info("数据导入操作员=="+operator);
		return qhClientDataServiceImpl.getCTPDataImportStatus(operator);
	}
	
	
	public Long[] stringtoLong(String date1,String date2){
		
		Long[] dates = new Long[2];
		
		if (date1 != null){
			dates[0] = Long.valueOf(date1);
		}
		if (date2 != null){
			dates[1] = Long.valueOf(date2);
		}
		
		if (dates[0] != null && dates[1] == null){
			dates[1] = dates[0];
	    }else if (dates[0] == null && dates[1] != null){
	    	dates[0] = dates[1];;
	    } 
		
		 if(dates[0] >= dates[1]){
      	   Long tempValue = dates[0];
      	   dates[0] = dates[1];
      	   dates[1] = tempValue;
         }
		
		return dates;
	}
		
	
}
