package com.kiiik.web.khfl.reportCaculate.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kiiik.web.khfl.reportCaculate.bean.ReportFormEntity;
import com.kiiik.web.khfl.readClientCtpData.vo.ImportStatus;
import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
import com.kiiik.web.khfl.reportCaculate.vo.FreezeClientGrade;
import com.kiiik.web.khfl.reportCaculate.vo.FundAccountDays;
import com.kiiik.web.khfl.reportCaculate.vo.ParamSettings;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月11日
 */
public interface ParamCalDMMapper {
	
	List<String> getFreezeClient(@Param("freezeStatus") String freezeStatus,@Param("active") String active);
	
	List<String> getLessOneMonthClients(String endDate);
	
	List<FreezeClientGrade> getFreezeClientGradeCircle(@Param("freezeList") List<String> freezeList,@Param("circleType") String circleType,@Param("endDate") String endDate);
	
	List<FreezeClientGrade> getFreezeClientGradeNoCircle(@Param("freezeList") List<String> freezeList,@Param("circleType") String circleType,@Param("endDate") String endDate);
	
	//获取CTP数据导入最新状态信息
	List<ImportStatus> getCTPDataImportStatus(String operator);
		
	List<ParamSettings> paramSettings(); 
	
	List<FundAccountDays> tradeDays(@Param("date") CalDate date);
	List<Map<String,String>> marketQutoes(CalDate date );
	List<FundAccountDays> additionalMarginDays(CalDate date);
	List<Map<String,String>> riskDays(CalDate date);
	List<FundAccountDays> throughWarehouseDays(CalDate date);
	List<Map<String,String>> contractSpirit(CalDate date);
	List<FundAccountDays> forceLiquidationDays(CalDate date);
	List<FundAccountDays> cooperateDays(CalDate date);
	List<Map<String,String>> investmentContracts(CalDate date);
	List<ReportFormEntity> lastCycleAvgRiskRate(@Param("date")CalDate date,@Param("type") int type);
}
