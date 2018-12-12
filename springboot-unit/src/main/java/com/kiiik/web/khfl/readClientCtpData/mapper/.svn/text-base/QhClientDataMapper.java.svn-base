package com.kiiik.web.khfl.readClientCtpData.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiiik.pub.mybatis.bean.EntityInfo;
import com.kiiik.web.khfl.dataImport.bean.DayForceCloseTradeEntity;
import com.kiiik.web.khfl.dataImport.bean.DayFundsEntity;
import com.kiiik.web.khfl.dataImport.bean.DayPositionDetailEntity;
import com.kiiik.web.khfl.dataImport.bean.DayTransactionDetailEntity;
import com.kiiik.web.khfl.dataImport.bean.QhCuinvestorpropertyEntity;
import com.kiiik.web.khfl.dataImport.bean.QhCurrinvestorsEntity;
import com.kiiik.web.khfl.dataImport.bean.ValidclientStartdateEntity;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月18日上午11:07:53
 */
public interface QhClientDataMapper {
	/**
	 * 
	 *方法描述: 获取期货客户CTP数据<br>
	 *创建时间: 2018-11-16
	 *@return 
	 *注意：这里传来的startDate与endDate 格式为'yyyymmdd'
	 */
	List<DayFundsEntity> getDayFundsData(@Param("startDate") Long startDate,@Param("endDate") Long endDate);
	
	List<DayForceCloseTradeEntity> getDayForceTradeByDate(@Param("startDate") Long startDate,@Param("endDate") Long endDate);
	
	List<DayPositionDetailEntity> getPositionDetail(@Param("startDate") Long startDate,@Param("endDate") Long endDate);
	
	List<DayTransactionDetailEntity> getTransDetail(@Param("startDate") Long startDate,@Param("endDate") Long endDate);
	
	List<QhCurrinvestorsEntity> getCurrInvestorDetail(@Param("active") String active);
	
	List<QhCuinvestorpropertyEntity> getCurrInvProperties();
	
	//读取客户第一个交易日日期
	List<ValidclientStartdateEntity> getClientStatrtDate();
	
	
	
}
