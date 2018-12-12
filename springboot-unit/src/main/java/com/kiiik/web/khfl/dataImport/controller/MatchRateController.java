package com.kiiik.web.khfl.dataImport.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.web.khfl.dataImport.bean.DataImportLogEntity;
import com.kiiik.web.khfl.dataImport.bean.MacthRateEntity;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportMode;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportStatus;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportType;
import com.kiiik.web.khfl.readClientCtpData.constants.ReadClientDataConstants;

import io.swagger.annotations.ApiOperation;

/**
 *创建者 : qinxiang<br>
 *类描述: 客户配合率数据解析并保存至mysql<br>
 *创建时间: 2018年11月13日
 */
@RestController
@RequestMapping("mr")
public class MatchRateController {

	Log log = LogFactory.getLog(MatchRateController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;

    @PostMapping("/importMatchRateData")
    @ApiOperation("获取并保存access客户配合度数据")
    public int importMRData(HttpServletRequest request,HttpServletResponse response){
    	//access传来数据格式如下
    	//String accessData = "startDate=2018/10/8&endDate=2018/10/9&data=#8757919,2018/10/8,自行入金#8715936,2018/10/8,强行平仓#8722127,2018/10/9,#8722128,2018/10/9,";
    	List<Object> list = new ArrayList<>();
    	String startDate = "";
    	String endDate = "";
    	String data = "";
    	
    	if (request.getParameter("data") != null){
    		data = request.getParameter("data");
    	}
    	if (request.getParameter("startDate") != null){
    		startDate = request.getParameter("startDate");
    	}
    	if (request.getParameter("endDate") != null){
    		endDate = request.getParameter("endDate");
    	}
       
       if ("".equals(startDate) && !"".equals(endDate)){
    	   startDate = endDate;
       }else if (!"".equals(startDate) && "".equals(endDate)){
    	   endDate = startDate;
       } 
       
       if (!"".equals(startDate) && !"".equals(endDate) ){
    	   startDate = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DAY, ConcurrentDateUtil.parse(ConcurrentDateUtil.PATTEN_OF_DAY2, startDate));
           endDate = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DAY, ConcurrentDateUtil.parse(ConcurrentDateUtil.PATTEN_OF_DAY2, endDate));
           if(startDate.compareTo(endDate) >=0){
        	   String tempValue = startDate;
        	   startDate = endDate;
        	   endDate = tempValue;
           }
           log.info("开始日期="+startDate+" ,结束日期="+endDate);
           
           
           //解析数据
       	   if ("".equals(data)){
       		   log.info("选择时间段内没有数据！");
       		   return 0;
       	   }else{
       		   int insrows = 0;
       		   DataImportLogEntity dile = null;
       		   try{  
	       		    //删除选择日期范围内的数据
	                MacthRateEntity mr = new MacthRateEntity();
	                int delrows = genericService.deleteDBEntityByDate(mr, startDate, endDate);
	                log.info("删除数量="+delrows);
	       		   
	       			log.info("客户数据="+data);
	       			
	       			dile = new DataImportLogEntity();
		        	dile.setOperator(ReadClientDataConstants.ACCESS_IMPORT_OPERATOR);//操作员
		        	dile.setSelectStartDate(startDate);//选择的开始日期
		        	dile.setSelectEndDate(endDate);//选择的结束日期
		        	dile.setImportDataType(DataImportType.MATCH_RATE.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期  100：客户配合度 101：vip
		        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
		        	dile.setImportMode(DataImportMode.IMPORT_BY_HAND.getStateNum());//数据导入方式  0：自动  1：手动 
		        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
		        	
		        	//创建保存
		        	genericService.insertDBEntity(dile);
	       			
	        	    //解析数据
	       			data = data.substring(1);//去掉最前面的#
	        	    String accessDataArray[] = data.split("#");
	                for (int i=0;i<accessDataArray.length;i++){
	            	   String tempDataArray[] = accessDataArray[i].split(",");
	            	   
	            	   MacthRateEntity cmr = new MacthRateEntity();
	            	   //资产账号
	            	   cmr.setFundAccount(tempDataArray[0]);
	            	  //强平通知日期
	            	   String dateTemp = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DAY, ConcurrentDateUtil.parse(ConcurrentDateUtil.PATTEN_OF_DAY2, tempDataArray[1]));
	            	  
	            	   cmr.setRecordDate(dateTemp);
	            	   /*
	            	    * 是否配合 
	            	    * 0：不配合，被强行平仓了 1：配合
	            	    */
	            	   if (tempDataArray.length > 2){
	            		   if ("强行平仓".equals(tempDataArray[2])){
	                		   cmr.setProcessResult("0");
	                	   }else{
	                		   cmr.setProcessResult("1"); 
	                	   }
	            	   }else{
	            		   cmr.setProcessResult("1"); 
	            	   }
	            	   
	            	   list.add(cmr);
	               }
	              //批量导入数据
	                insrows = genericService.insertDBEntityBatch(list);
	                
                    dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		            dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+insrows);
			        dile.setImportDataNumbers(insrows);//插入数据量
			        
			        //更新保存
			        genericService.updateDBEntityByKey(dile);
		        	
	                log.info("导入数量="+insrows);
	                return insrows;
	       		 }catch (Exception e) {
	       			 if (dile != null){
	       				dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
				        
				        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
			        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
				        
			        	log.info("共插入数量="+insrows);
				        dile.setImportDataNumbers(insrows);//插入数据量
				        
				        //更新保存
				        genericService.updateDBEntityByKey(dile);
	       			 }
				    	e.printStackTrace();
				        throw new RuntimeException(e);
				 }
       	   }
              
       }else{
    	   log.error("请选择日期！");
    	   return -1;
       }
      
      
    }
    
    
}
