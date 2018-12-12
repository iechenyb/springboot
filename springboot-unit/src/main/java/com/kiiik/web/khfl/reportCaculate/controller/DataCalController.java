package com.kiiik.web.khfl.reportCaculate.controller;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.web.khfl.reportCaculate.bean.ClassifyCalLogEntity;
import com.kiiik.web.khfl.reportCaculate.constants.ClientDataConstants;
import com.kiiik.web.khfl.reportCaculate.service.ParamCalService;
import com.kiiik.web.khfl.reportCaculate.utils.DateSectionUtils;
import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
import com.kiiik.web.khfl.reportCaculate.vo.CycleCalVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月28日上午11:12:35
 */
@RestController
@RequestMapping("dataCal")
@Api(value="客户等级计算")
public class DataCalController extends BaseController{
	Log log = LogFactory.getLog(DataCalController.class);
	
	@Autowired
	GenericService genericService;

	@GetMapping("/data")
	@ApiOperation("基础数据查询")
	public ResultBean<Object> infor(int type){
		switch(type){
		case 0:
			return new ResultBean<Object>(ClientDataConstants.ALL_FREEZE_CLIENT);
		case 1:
			return new ResultBean<Object>(ClientDataConstants.ALL_VALId_CLIENT);
		case 2:
			return new ResultBean<Object>(ClientDataConstants.ALL_VIP_CLIENT);
		case 3:
			return new ResultBean<Object>(ClientDataConstants.ALL_VALId_CLIENT_INFO);
		default:return new ResultBean<Object>();
		}
	}
	@Autowired
	ParamCalService paramCalService;
	
	@PostMapping("/calFixedCycle")
	@ApiOperation("计算")
	public ResultBean<String> cal(@RequestBody @Validated final CycleCalVO param) 
			throws NumberFormatException, Exception{
		final CalDate cd = DateSectionUtils.calSection(param);
		if(cd.check()){
			return new ResultBean<String>().fail("日期不能为空！");
		}
		int count = paramCalService.findTask(param);
		if(count>0){
			return new ResultBean<String>().fail("存在相同的正在执行的任务！");
		}else{
			Thread task =new Thread(new Runnable() {
				@Override
				public void run() {
					int id = 0;
					try {
						KiiikContants.KHFL_SEMA_FIX.acquire();
						ClassifyCalLogEntity log = new ClassifyCalLogEntity();
						log.setYear(Integer.valueOf(param.getYear()));
						log.setCircleType(param.getType());
						log.setNote("任务执行中!");
						log.setOperator(getUser().getEmpNo());
						log.setRecordDate(ConcurrentDateUtil.date2long8().toString());
						log.setStartDate(ConcurrentDateUtil.mills());
						if(param.getType()!=4){
							log.setValue(Integer.valueOf(param.getVal()));
						}
						log.setCircleEndDate(cd.getEndDate());
						log.setCircleStartDate(cd.getBeginDate());
						log.setStatus(KiiikContants.TASK_DOING);//1 正在执行 2执行成功  3 执行失败
						genericService.insertDBEntity(log);
						id= log.getId();
						ResultBean<String> rs = paramCalService.calKhzb(cd,param);
						ClassifyCalLogEntity endLog = new ClassifyCalLogEntity();
						endLog.setId(id);
						if(rs.isSuccess()){
							endLog.setStatus(KiiikContants.TASK_DONE_SUCCESS);
						}else{
							endLog.setStatus(KiiikContants.TASK_DONE_FAILED);
						}
						endLog.setNote(rs.getEs());
						endLog.setEndDate(ConcurrentDateUtil.mills());
						genericService.updateDBEntityByKey(endLog);
					} catch (Exception e) {
						e.printStackTrace();
						ClassifyCalLogEntity log = new ClassifyCalLogEntity();
						log.setId(id);
						log.setStatus(KiiikContants.TASK_DONE_FAILED);
						log.setNote("任务执行失败！");
						log.setEndDate(ConcurrentDateUtil.mills());
						genericService.updateDBEntityByKey(log);
					}finally{
						KiiikContants.KHFL_SEMA_FIX.release();
					}
				}
			});
		     task.start();
		}
		return new ResultBean<String>().success("任务已经提交，请查看任务表！");
	}
	@PostMapping("/calNotFixedCycle")
	@ApiOperation("计算")
	public ResultBean<String> cal(@RequestBody @Validated final CalDate param) throws NumberFormatException, Exception{
		final CycleCalVO vo = new CycleCalVO();
		vo.setType(KiiikContants.CIRCLE_NOT_FIXED);//固定周期 //需要记录其实时间和结束时间
		if(paramCalService.findTask(param)>0){
			return new ResultBean<String>().fail("存在相同的正常执行的任务");
		}else{
			if(DateSectionUtils.days(param.getBeginDate(), param.getEndDate())<30){
				return new ResultBean<String>().fail("不定周期大小必须大于等于30之自然日！");
			}
			 new Thread(new Runnable() {
				@Override
				public void run() {
					int id=0;
					try {
						KiiikContants.KHFL_SEMA_NOT_FIX.acquire();
						final ClassifyCalLogEntity log = new ClassifyCalLogEntity();
						log.setCircleType(KiiikContants.CIRCLE_NOT_FIXED);
						log.setNote("任务执行中!");
						log.setOperator(getUser().getEmpNo());
						log.setRecordDate(ConcurrentDateUtil.date2long8().toString());
						log.setStartDate(ConcurrentDateUtil.mills());
						log.setCircleEndDate(param.getEndDate());
						log.setCircleStartDate(param.getBeginDate());
						log.setStatus(KiiikContants.TASK_DOING);//1 正在执行 2执行成功  3 执行失败
						genericService.insertDBEntity(log);
						id = log.getId();
						ResultBean<String> rs = paramCalService.calKhzb(param,vo);
						ClassifyCalLogEntity endLog = new ClassifyCalLogEntity();
						endLog.setId(id);
						if(rs.isSuccess()){
							endLog.setStatus(KiiikContants.TASK_DONE_SUCCESS);
						}else{
							endLog.setStatus(KiiikContants.TASK_DONE_FAILED);
						}
						endLog.setNote(rs.getEs());
						endLog.setEndDate(ConcurrentDateUtil.mills());
						genericService.updateDBEntityByKey(endLog);
					} catch (Exception e) {
						e.printStackTrace();
						ClassifyCalLogEntity log = new ClassifyCalLogEntity();
						log.setId(id);
						log.setStatus(KiiikContants.TASK_DONE_FAILED);
						log.setNote("任务执行失败！");
						log.setEndDate(ConcurrentDateUtil.mills());
						genericService.updateDBEntityByKey(log);
					}finally{
						KiiikContants.KHFL_SEMA_NOT_FIX.release();
					} 
				}
			}).start();
		}
		return new ResultBean<String>().success("任务提交成功！");
	}
	@PostMapping("/lastCycleSection")
	@ApiOperation("计算")
	public ResultBean<CalDate> getLastCycleSection(@RequestBody @Validated CycleCalVO param) throws NumberFormatException, Exception{
		CalDate cd = DateSectionUtils.lastCycyleDate(param);
		if(cd.check()){
			return new ResultBean<CalDate>(cd).fail("日期不能为空！");
		}
		return new ResultBean<CalDate>(cd).success();
	}
	
	@GetMapping("getLessOneMonthClients")
	@ApiOperation("获取未满一个月的新客户")
	public ResultBean<Integer> getLessOneMonthClients(@RequestParam("endDate") String endDate){
		List<String> listcs = paramCalService.getLessOneMonthClientList(endDate);
		log.info("=======listcs.size()====="+listcs.size());
		return new ResultBean<Integer>(listcs.size()).success("列表返回成功");
	}
	
	@GetMapping("getFreezeClientGrade")
	@ApiOperation("获取休眠客户的原评级")
	public void getFreezeClientGrade(@RequestParam("circleType") String circleType,@RequestParam("endDate") String endDate){
		paramCalService.getFreezeClientGrade(circleType, endDate);
	}
	
}
