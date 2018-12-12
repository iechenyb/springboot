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
import com.kiiik.web.khfl.dataImport.bean.VipClientEntity;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportMode;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportStatus;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportType;
import com.kiiik.web.khfl.readClientCtpData.constants.ReadClientDataConstants;

import io.swagger.annotations.ApiOperation;

/**
 *创建者 : qinxiang<br>
 *类描述: vip数据解析并保存至mysql<br>
 *创建时间: 2018年11月13日
 */
@RestController
@RequestMapping("vc")
public class VipClientController {

	Log log = LogFactory.getLog(VipClientController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;

    @PostMapping("/importVipData")
    @ApiOperation("获取性保存accessVip客户数据")
    public int importVipData(HttpServletRequest request,HttpServletResponse response){
    	//access传来数据格式如下
    	//String data = "#8757919,vip#8715936,vip#8757918,vip";
    	List<Object> list = new ArrayList<Object>();
    	String data = "";
    	
    	if (request.getParameter("data") != null){
    		data = request.getParameter("data");
    	}
    	log.info("vip数据="+data);
       
     //解析并保存数据
   	   if ("".equals(data)){
   		   log.info("没有vip数据！");
   		   return 0;
   	   }else{
   		   int insrows = 0;
		   DataImportLogEntity dile = null;
   		   try{
   			   //全表删除数据
   		        VipClientEntity vce = new VipClientEntity();
   		        int delrows = genericService.deleteFullDBEntity(vce);
   		        log.info("删除数量="+delrows);
   		        
   		        dile = new DataImportLogEntity();
	        	dile.setOperator(ReadClientDataConstants.ACCESS_IMPORT_OPERATOR);//操作员
	        	dile.setSelectStartDate(null);//选择的开始日期
	        	dile.setSelectEndDate(null);//选择的结束日期
	        	dile.setImportDataType(DataImportType.VIP.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期  100：客户配合度 101：vip
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
	        	   
	        	   VipClientEntity vc = new VipClientEntity();
	        	   //资产账号
	        	   vc.setFundAccount(tempDataArray[0]);
	        	  //vip类别
	        	   vc.setType(tempDataArray[1]);
	        	   
	        	   list.add(vc);
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
   	   
          
    }
}
