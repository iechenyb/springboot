package com.kiiik.web.khfl.reportCaculate.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.web.khfl.reportCaculate.bean.ReportFormEntity;
import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
import com.kiiik.web.khfl.reportCaculate.vo.CycleCalVO;
import com.kiiik.web.khfl.reportCaculate.vo.FreezeClientGrade;
import com.kiiik.web.khfl.reportCaculate.vo.ParamSettings;
public interface ParamCalService {
	int findTask(CycleCalVO param);//查询是否存在正在执行的任务
	int findTask(CalDate param);//查询是否存在正在执行的任务
	ResultBean<String> calKhzb(CalDate cd,CycleCalVO param) throws InterruptedException, ExecutionException, Exception;
	List<String> getLessOneMonthClientList(String endDate);
	List<FreezeClientGrade> getFreezeClientGrade(String circleType,String endDate);//circleType 0:自定义周期 1：月度  2：季度  3：半年度  4：年度
	Map<String,List<ParamSettings>> paramSettings(); 
	Map<String, Integer> tradeDays(CalDate date);
	Integer marketQutoes(CalDate date );
	Map<String, Integer> additionalMarginDays(CalDate date);
	Map<String, Double> riskDays(CalDate date);
	Map<String, Integer> throughWarehouseDays(CalDate date);
	Map<String, Integer> contractSpirit(CalDate date);
	Map<String, Integer> forceLiquidationDays(CalDate date);
	Map<String, Integer> cooperateDays(CalDate date);
	Map<String, Set<String>> investmentContracts(CalDate date);
	Map<String,ReportFormEntity> lastCycleReportForm(CalDate date,int type);
}
