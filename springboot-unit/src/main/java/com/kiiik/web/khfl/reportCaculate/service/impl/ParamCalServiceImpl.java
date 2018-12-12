package com.kiiik.web.khfl.reportCaculate.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.service.BaseService;
import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.utils.TimeContext;
import com.kiiik.web.khfl.reportCaculate.bean.ClassifyCalLogEntity;
import com.kiiik.web.khfl.reportCaculate.bean.KhflMidDateEntity;
import com.kiiik.web.khfl.reportCaculate.bean.ReportFormEntity;
import com.kiiik.web.khfl.reportCaculate.constants.ClientDataConstants;
import com.kiiik.web.khfl.reportCaculate.mapper.KhflMidDataMapper;
import com.kiiik.web.khfl.reportCaculate.mapper.ParamCalDMMapper;
import com.kiiik.web.khfl.reportCaculate.service.ParamCalService;
import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
import com.kiiik.web.khfl.reportCaculate.vo.CycleCalVO;
import com.kiiik.web.khfl.reportCaculate.vo.FreezeClientGrade;
import com.kiiik.web.khfl.reportCaculate.vo.FundAccountDays;
import com.kiiik.web.khfl.reportCaculate.vo.ParamSettings;

/**
 * 业务处理层
   旧逻辑，没有使用累计值
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-12 16:00:22
 */

@Service
public class ParamCalServiceImpl extends BaseService implements ParamCalService {
	
	//当前业务数据库服务接口
	Log log = LogFactory.getLog(ParamCalServiceImpl.class);
	
	@Autowired
    ParamCalDMMapper paramCalDMMapper;
	
	@Autowired
	ClientDataConstants clientData;
    
    public List<String> queryFreezeClientList(String freezeStatus,String isActive){
		return paramCalDMMapper.getFreezeClient(freezeStatus,isActive);
	}

    public int findTask(CycleCalVO param){
    	ClassifyCalLogEntity log = new ClassifyCalLogEntity();
		log.setYear(Integer.valueOf(param.getYear()));
		log.setCircleType(param.getType());
		if(param.getType()!=4){
			log.setValue(Integer.valueOf(param.getVal()));
		}
		log.setStatus(KiiikContants.TASK_DOING);//1 正在执行 2执行成功  3 执行失败
		List<ClassifyCalLogEntity> data = genericDao.queryDBEntityList(log);
		if(CollectionUtils.isEmpty(data)){
			return 0;
		}else{
			return data.size();
		}	
    }
    @Autowired
    KhflMidDataMapper midDataMapper;
	@Override
	public ResultBean<String> calKhzb(final CalDate cd,final CycleCalVO param) throws Exception {
		TimeContext.recordTimeStart(); 
		//查找是否有当前任务
		//1查询所有人当前日期区间内的交易天数
		//2查询市场行情  假如[m,n]n日为非交易日或者无行情数据，则取date=max<n的市场行情。
		Callable<Integer> marketQutoesTask = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				 TimeContext.recordTimeStart();
				 Integer status=marketQutoes(cd);
				 TimeContext.calExecuteTime("市场行情查询！");
				return status;
			}
		};
		
		Callable<Map<String,Integer>> contractSpiritTask = new Callable<Map<String,Integer>>() {
			@Override
			public Map<String,Integer> call() throws Exception {
				 TimeContext.recordTimeStart();
				 Map<String,Integer> data = contractSpirit(cd);
				 TimeContext.calExecuteTime("合约精神查询！");
				return data;
			}
		};
		
		Callable<Map<String,ReportFormEntity>> lastAvgRiskTask = new Callable<Map<String,ReportFormEntity>>() {
			@Override
			public Map<String,ReportFormEntity> call() throws Exception {
				TimeContext.recordTimeStart();
				Map<String, ReportFormEntity> data = lastCycleReportForm(cd, param.getType());
				TimeContext.calExecuteTime("上期平均风险查询！");
				return data;
			}
		};
		
		FutureTask<Integer> marketQutoesFuture = new FutureTask<>(marketQutoesTask);
		FutureTask<Map<String,Integer>> contractSpiritFuture = new FutureTask<>(contractSpiritTask);
		FutureTask<Map<String,ReportFormEntity>> lastAvgRiskFuture = new FutureTask<>(lastAvgRiskTask);
		ExecutorService worker = Executors.newFixedThreadPool(9);
		worker.submit(marketQutoesFuture);
		worker.submit(contractSpiritFuture);
		worker.submit(lastAvgRiskFuture);
		/*Map<String,Integer> tradeDaysData= new HashMap<>();
		if(CollectionUtils.isEmpty(tradeDaysData)){
			return new ResultBean<String>().fail("人员交易天数查询失败！");
		}*/
		Integer marketQutoesData = marketQutoesFuture.get();
		if(marketQutoesData==null){ 
			//throw new Exception("市场行情数据查询失败！");
			//不存在时 默认中级
			marketQutoesData = KiiikContants.MARKET_QUTOES_MID;
		}
		Map<String,KhflMidDateEntity> startLjDataMap =new HashMap<>(); 
		List<KhflMidDateEntity>	startLjDataList = midDataMapper.sectionMarginLj(cd.getBeginDate(), cd.getEndDate(), "asc");
		if(!CollectionUtils.isEmpty(startLjDataList)){
			for(KhflMidDateEntity entity:startLjDataList){
				startLjDataMap.put(entity.getFundAccount(), entity);
			}
		}
		Map<String,KhflMidDateEntity> endLjDataMap=new HashMap<>();
		List<KhflMidDateEntity>	endLjDataList = midDataMapper.sectionMarginLj(cd.getBeginDate(), cd.getEndDate(), "desc");
		if(!CollectionUtils.isEmpty(endLjDataList)){
			for(KhflMidDateEntity entity:endLjDataList){
				endLjDataMap.put(entity.getFundAccount(), entity);
			}
		}
		if(CollectionUtils.isEmpty(startLjDataList)&&CollectionUtils.isEmpty(endLjDataMap)){
			return new ResultBean<String>().fail("当前区间累计值没有计算！");
		}
		Map<String,Integer> contractSpiritData = contractSpiritFuture.get();
		Map<String,ReportFormEntity> lastAvgRiskData = lastAvgRiskFuture.get();
		Map<String, List<ParamSettings>> settings = paramSettings();//等级规则设置信息
		if(CollectionUtils.isEmpty(settings)){
			return new ResultBean<String>().fail("等级规则设置信息查询失败！");
		}
		//3 资金表->追保发生率、平均风险率、穿仓次数
		//4 契约精神、强平发生率、配合率
		//5 分期投资（合约数目统计）
		//6 具体指标计算（报表生成）
		//查询交易不足一个月的客户
		List<String> lessThanOneMonthClientList = getLessOneMonthClientList(cd.getEndDate());
		//查询休眠客户
		Map<String,FreezeClientGrade> freezeClientGrade = getFreezeClientGradeMap(param.getType(),cd.getEndDate());
		
		ReportFormEntity condition = new ReportFormEntity();
		condition.setStartDate(cd.getBeginDate());
		condition.setEndDate(cd.getEndDate());
		condition.setCircleType(String.valueOf(param.getType()));
		genericDao.deleteDBEntity(condition);
		List<ReportFormEntity> reports = new ArrayList<ReportFormEntity>();
		for(String fundAccount:ClientDataConstants.ALL_VALId_CLIENT){
			//正常客户
			ReportFormEntity report = new ReportFormEntity();
			report.setFundAccount(fundAccount);
			report.setStartDate(cd.getBeginDate());
			report.setEndDate(cd.getEndDate());
			report.setCircleType(String.valueOf(param.getType()));
			report.setUserName(ClientDataConstants.ALL_VALId_CLIENT_INFO.get(fundAccount));
			if(ClientDataConstants.ALL_VIP_CLIENT.contains(fundAccount)){
				//VIP客户或者大权益客户
				report.setNote("VIP客户");
				report.setGrade("A+");//等级
			}else if(ClientDataConstants.ALL_FREEZE_CLIENT.contains(fundAccount)){
				//有效休眠客户
				report.setNote("有效休眠客户");//保留上一个周期的等级
				if(freezeClientGrade!=null&&freezeClientGrade.get(fundAccount)!=null){
					report.setGrade(freezeClientGrade.get(fundAccount).getLatestGrade());
				}
			}else if(lessThanOneMonthClientList.contains(fundAccount)){
				//少于一个月的客户
				report.setNote("交易未满一个月");
				report.setGrade("N");//等级
			}else{
				report.setNote("正常有效客户");//备注。如果是VIP/休眠/交易未满一个月的则备注，否则为空
				KhflMidDateEntity startLj = startLjDataMap.get(fundAccount);
				KhflMidDateEntity endLj = endLjDataMap.get(fundAccount);
				Integer days = 0;
				if(startLj!= null&&endLj!=null){
					days = cal(endLj.getTradeDaysTotal(),startLj.getTradeDaysTotal(),startLj.getHasTrade());//end累计值-start累计值+start当日值
				}
				if(days==0){
					//交易天数为0，跳过！
					System.err.println(fundAccount+"人员交易天数为0，跳过计算！");
					continue;
				}
				if(startLj!=null&&endLj!=null){
					Double fxdTotal = cal(endLj.getFxdTotal(),startLj.getFxdTotal(),startLj.getFxd());
					report.setAverageRiskRate(fxdTotal/days*1.0);//平均风险率
					if(lastAvgRiskData!=null&&lastAvgRiskData.get(fundAccount)!=null){
						//上期存在或者值为0，则增长率为50%
						Double lastAvgRiskRate = lastAvgRiskData.get(fundAccount).getAverageRiskRate();//<=0.0001d
						if(lastAvgRiskRate!=null&&lastAvgRiskRate<=0.0001d){
							report.setAverageRiskGrowthRate(0.5);
						}else{
							Double growRate = (report.getAverageRiskRate()-lastAvgRiskRate)/lastAvgRiskRate;
						    report.setAverageRiskGrowthRate(growRate);//平均风险增长率
						}
					}else{//上期不存在
						report.setAverageRiskGrowthRate(0.5);
					}
					int contractTotals = cal(endLj.getContractNumsTotal(),startLj.getContractNumsTotal(),startLj.getContractNums());
					report.setDecentraInvestment(contractTotals/days*1.0);//分散投资
					int qpNumTotals = cal(endLj.getQpDaysTotal(),startLj.getQpDaysTotal(),startLj.getQpNums());
					report.setForcedClosingRate(qpNumTotals/days*1.0);//强平率
					int zbDaysTotals = cal(endLj.getZbDaysTotal(),startLj.getZbDaysTotal(),startLj.getQpNums());
					report.setMargetCallRate(zbDaysTotals/days*1.0);//追保发生率
					int phNumTotals = cal(endLj.getPhqpDaysTotal(),startLj.getPhqpDaysTotal(),startLj.getPhqpNums());
					int qpNoticeTotal = qpNumTotals+phNumTotals;
					if(qpNoticeTotal==0){
						//没有通知强平
						report.setMatchRate(1.0);//配合率100
					}else{
						report.setMatchRate(phNumTotals/qpNoticeTotal*0.1);
					}
					int ccTotals = cal(endLj.getCcNumsTotal(),startLj.getCcNumsTotal(),startLj.getCcNums());
					report.setThroughwearTimes(ccTotals);//穿仓次数
				} else if(endLj==null&&startLj!=null){// 不存在结束点累计值，存在起始点累计值(数据异常处理)
					Double fxdTotal = cal(0.0,startLj.getFxdTotal(),startLj.getFxd());
					report.setAverageRiskRate(fxdTotal/days*1.0);//平均风险率
					if(lastAvgRiskData!=null&&lastAvgRiskData.get(fundAccount)!=null){
						//上期存在或者值为0，则增长率为50%
						Double lastAvgRiskRate = lastAvgRiskData.get(fundAccount).getAverageRiskRate();//<=0.0001d
						if(lastAvgRiskRate!=null&&lastAvgRiskRate<=0.0001d){
							report.setAverageRiskGrowthRate(0.5);
						}else{
							Double growRate = (report.getAverageRiskRate()-lastAvgRiskRate)/lastAvgRiskRate;
						    report.setAverageRiskGrowthRate(growRate);//平均风险增长率
						}
					}else{//上期不存在
						report.setAverageRiskGrowthRate(0.5);
					}
					int contractTotals = cal(0,startLj.getContractNumsTotal(),startLj.getContractNums());
					report.setDecentraInvestment(contractTotals/days*1.0);//分散投资
					int qpNumTotals = cal(0,startLj.getQpDaysTotal(),startLj.getQpNums());
					report.setForcedClosingRate(qpNumTotals/days*1.0);//强平率
					int zbDaysTotals = cal(0,startLj.getZbDaysTotal(),startLj.getQpNums());
					report.setMargetCallRate(zbDaysTotals/days*1.0);//追保发生率
					int phNumTotals = cal(0,startLj.getPhqpDaysTotal(),startLj.getPhqpNums());
					int qpNoticeTotal = qpNumTotals+phNumTotals;
					if(qpNoticeTotal==0){
						//没有通知强平
						report.setMatchRate(1.0);//配合率100
					}else{
						report.setMatchRate(phNumTotals/qpNoticeTotal*0.1);
					}
					int ccTotals = cal(0,startLj.getCcNumsTotal(),startLj.getCcNums());
					report.setThroughwearTimes(ccTotals);//穿仓次数
				}else{//累计值都不存在 不进行计算 endLj!=null&&startLj==null // 不存在结束点累计值，存在起始点累计值(数据异常处理)
					/*report.setAverageRiskGrowthRate(0.0);//平均风险增长率
					report.setAverageRiskRate(0.0);//平均风险率
					report.setDecentraInvestment(0.0);//分散投资
					report.setForcedClosingRate(0.0);//强平率
					report.setMargetCallRate(0.0);//追保发生率
					report.setMatchRate(0.0);//配合率
					report.setThroughwearTimes(0);
					report.setNote("无法计算");//备注。如果是VIP/休眠/交易未满一个月的则备注，否则为空
					*/
					@SuppressWarnings("null")
					Double fxdTotal = cal(endLj.getFxdTotal(),0.0,0.0);
					report.setAverageRiskRate(fxdTotal/days*1.0);//平均风险率
					if(lastAvgRiskData!=null&&lastAvgRiskData.get(fundAccount)!=null){
						//上期存在或者值为0，则增长率为50%
						Double lastAvgRiskRate = lastAvgRiskData.get(fundAccount).getAverageRiskRate();//<=0.0001d
						if(lastAvgRiskRate!=null&&lastAvgRiskRate<=0.0001d){
							report.setAverageRiskGrowthRate(0.5);
						}else{
							Double growRate = (report.getAverageRiskRate()-lastAvgRiskRate)/lastAvgRiskRate;
						    report.setAverageRiskGrowthRate(growRate);//平均风险增长率
						}
					}else{//上期不存在
						report.setAverageRiskGrowthRate(0.5);
					}
					int contractTotals = cal(endLj.getContractNumsTotal(),0,0);
					report.setDecentraInvestment(contractTotals/days*1.0);//分散投资
					int qpNumTotals = cal(endLj.getQpDaysTotal(),0,0);
					report.setForcedClosingRate(qpNumTotals/days*1.0);//强平率
					int zbDaysTotals = cal(endLj.getZbDaysTotal(),0,0);
					report.setMargetCallRate(zbDaysTotals/days*1.0);//追保发生率
					int phNumTotals = cal(endLj.getPhqpDaysTotal(),0,0);
					int qpNoticeTotal = qpNumTotals+phNumTotals;
					if(qpNoticeTotal==0){
						//没有通知强平
						report.setMatchRate(1.0);//配合率100
					}else{
						report.setMatchRate(phNumTotals/qpNoticeTotal*0.1);
					}
					int ccTotals = cal(endLj.getCcNumsTotal(),0,0);
					report.setThroughwearTimes(ccTotals);//穿仓次数
			    }
				report.setScore(0.0);//初始化总分数
				report.setGrade("");//初始化等级
				report.setMarketSituation(marketQutoesData);//市场行情 1：低风险 2：中风险 3：高风险
				report.setContractSpirit(contractSpiritData.get(fundAccount)==null?1:contractSpiritData.get(fundAccount));//契约精神1具备 0不具备
				calScoreLevel(report,settings);//精度处理
				round(report);//精度修改
				reports.add(report);
				if(reports.size()%1000==0){
					genericDao.insertDBEntityBatchT(reports);
					reports.clear();
				}
			}// end 正常有效客户
		}//end for
		if(!CollectionUtils.isEmpty(reports)){
			genericDao.insertDBEntityBatchT(reports);
		}
		TimeContext.calExecuteTime("指标计算！");
		worker.shutdown();
		return new ResultBean<String>().success("执行成功！");
	}
	/**
	 * 
	 * @param endValue 结束点累计值
	 * @param startValue 起始点累计值
	 * @param startDayValue 起始点当日值
	 * @return
	 */
	private Integer cal(Integer endValue,Integer startValue,Integer startDayValue){
		endValue = endValue==null?0:endValue;
		startValue = startValue==null?0:startValue;
		startDayValue = startDayValue==null?0:startDayValue;
		if(endValue.intValue()==0){
			return startValue;
		}
		return endValue-startValue+startDayValue;
	}
	/**
	 * 
	 * @param endValue 结束点累计值
	 * @param startValue 起始点累计值
	 * @param startDayValue 起始点当日值
	 * @return
	 */
	private double cal(Double endValue,Double startValue,Double startDayValue){
		endValue = endValue==null?0:endValue;
		startValue = startValue==null?0:startValue;
		startDayValue = startDayValue==null?0:startDayValue;
		if(endValue.intValue()==0){
			return startValue;
		}
		return endValue-startValue+startDayValue;
	}
	final int defalutScale = 4;
	private void round(ReportFormEntity report) {
		report.setAverageRiskGrowthRate(round(report.getAverageRiskGrowthRate(),defalutScale));
		report.setMargetCallRate(round(report.getMargetCallRate(),defalutScale));
		report.setAverageRiskRate(round(report.getAverageRiskRate(),defalutScale));
		report.setDecentraInvestment(round(report.getDecentraInvestment(),defalutScale));
		report.setForcedClosingRate(round(report.getForcedClosingRate(),defalutScale));
		report.setMatchRate(round(report.getMatchRate(),defalutScale));
	}
	double round(Double v,int scale){
		if(v!=null){
			BigDecimal b =new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		}else{
			return 0.0d;
		}
	}
	public final String cccs = "cccs";//穿仓次数
	private final String fstz= "fstz";//分散投资
	private final String phl= "phl";//配合率
	private final String pjfxl= "pjfxl";//平均风险率
	private final String pjfxlzzl= "pjfxlzzl";//平均风险增长率
	private final String qpfsl= "qpfsl";//强平发生率
	private final String zbfsl= "zbfsl";//追保发生率

	private void calScoreLevel(ReportFormEntity report,Map<String, List<ParamSettings>> settings) throws ScriptException {
		Double totalScore=0.0d;
		//计算契约精神分数
		if(report.getContractSpirit()==1)//不具备契约精神时，加0*0.1.
		{	
			totalScore = totalScore+100*0.1;
		}
		//履约能力-配合率-强平
		ParamSettings cur = findSetting(settings,report.getMatchRate(),phl);
		if(cur!=null){
			totalScore = totalScore+cur.getValue()*cur.getWeight()/100;
		}
		cur = findSetting(settings,report.getAverageRiskRate(),pjfxl);
		if(cur!=null){
			totalScore = totalScore+cur.getValue()*cur.getWeight()/100;
		}
		cur = findSetting(settings,report.getAverageRiskGrowthRate(),pjfxlzzl);
		if(cur!=null){
			totalScore = totalScore+cur.getValue()*cur.getWeight()/100;
		}
		cur = findSetting(settings,report.getMargetCallRate(),zbfsl);
		if(cur!=null){
			totalScore = totalScore+cur.getValue()*cur.getWeight()/100;
		}
		cur = findSetting(settings,report.getForcedClosingRate(),qpfsl);
		if(cur!=null){
			totalScore = totalScore+cur.getValue()*cur.getWeight()/100;
		}
		//穿仓次数
		totalScore = totalScore +(report.getThroughwearTimes()==0?100:0)*0.1;
		
		cur = findSetting(settings,report.getDecentraInvestment(),fstz);
		if(cur!=null){
			totalScore = totalScore+cur.getValue()*cur.getWeight()/100;
		}
		//市场行情1-100 2-80 3-50 低中高
		if("1".equals(report.getMarketSituation().intValue())){
			totalScore = totalScore +100*0.1 ;
		}else if("2".equals(report.getMarketSituation().intValue())){
			totalScore = totalScore +80*0.1 ;
		}else if("3".equals(report.getMarketSituation().intValue())){
			totalScore = totalScore +50*0.1 ;
		}
		report.setScore(totalScore);
		if(totalScore>=95){
			report.setGrade("A+");
		}else if(totalScore>=80&&totalScore<95){
			report.setGrade("A");
		}else if(totalScore>=60&&totalScore<80){
			report.setGrade("B");
		}else{
			report.setGrade("C");
		}
	}
	
	private ParamSettings findSetting(Map<String, List<ParamSettings>> settings,Object value,String type) throws ScriptException {
		ScriptEngineManager m = new ScriptEngineManager();
		ScriptEngine engine = m.getEngineByName("js");
		engine.put("x", value);
		for(ParamSettings set:settings.get(type)){
			Boolean rest = (Boolean) engine.eval(set.getTerm());
			if(rest){
				return  set;
			}
		}
		return null;
	}

	//计算交易未满一个月新客户列表
	@Override
	public List<String> getLessOneMonthClientList(String endDate) {
		String lastMonthDate = ConcurrentDateUtil.getLastMonthDate(endDate);
    	log.info("lastMonthDate="+lastMonthDate);
		return paramCalDMMapper.getLessOneMonthClients(lastMonthDate);
	}
	
	public Map<String,FreezeClientGrade> getFreezeClientGradeMap(int circleType, String endDate) {
		List<FreezeClientGrade> data = getFreezeClientGrade(String.valueOf(circleType),endDate);
		Map<String,FreezeClientGrade> res = new HashMap<>();
		if(!CollectionUtils.isEmpty(data)){
			for(FreezeClientGrade fcg:data){
				res.put(fcg.getFundAccount(), fcg);
			}
			return res;
		}
		return null;
	}
	
	
	/*
	 * 计算休眠客户评级
	 * //circleType 0:自定义周期  1：月度  2：季度  3：半年度  4：年度
	 */
	@Override
	public List<FreezeClientGrade> getFreezeClientGrade(String circleType, String endDate) {
		List<String> freeze = ClientDataConstants.ALL_FREEZE_CLIENT;
		//List<String> freeze = new ArrayList<String>();
		//freeze.add("3000021");
		//freeze.add("3000022");
		//freeze.add("3000037");
		
		if (circleType != null && !"".equals(circleType)){
			if (!"0".equals(circleType)){//固定周期
				List<FreezeClientGrade> list = paramCalDMMapper.getFreezeClientGradeCircle(freeze,circleType,endDate);
				
				//for (int i=0;i<list.size();i++){
					//FreezeClientGrade fcg = list.get(i);
					//System.out.println("fd=="+fcg.getFundAccount()+", grade== "+fcg.getLatestGrade());
				//}
				return list;
			}else{//自定义周期
				List<FreezeClientGrade> list = paramCalDMMapper.getFreezeClientGradeNoCircle(freeze,"1",endDate);
				return list;
			}
		}
		return null;
	}

	@Override
	public Map<String, Integer> tradeDays(CalDate date) {
		//每个人一条记录
		List<FundAccountDays> data = paramCalDMMapper.tradeDays(date);
		Map<String, Integer> result = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(data)){
			for(FundAccountDays map:data){
				if(map.getDays()!=null){
					//每个人的指定区间风险度累计值
					result.put(map.getFund_account(),map.getDays());
				}
			}
			return result;
		}
		return new HashMap<String,Integer>();
	}
	
	@Override
	public Integer marketQutoes(CalDate date) {
		List<Map<String, String>> data = paramCalDMMapper.marketQutoes(date);
		if(!CollectionUtils.isEmpty(data)){
			String risk_level = data.get(0).get("risk_level");
			if(risk_level!=null){
				return Integer.valueOf(risk_level);
			}
		}
		return null;
	}

	@Override
	public Map<String, Integer> additionalMarginDays(CalDate date) {
		//每个人一条记录
		List<FundAccountDays> data = paramCalDMMapper.additionalMarginDays(date);
		Map<String, Integer> result = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(data)){
			for(FundAccountDays map:data){
				if(map.getDays()!=null){
					//每个人的指定区间风险度累计值
					result.put(map.getFund_account(),map.getDays());
				}
			}
			return result;
		}
		return new HashMap<String,Integer>();
	}

	@Override
	public Map<String, Double> riskDays(CalDate date) {
		//每个人一条记录
		List<Map<String, String>> data = paramCalDMMapper.riskDays(date);
		Map<String, Double> result = new HashMap<String, Double>();
		if(!CollectionUtils.isEmpty(data)){
			for(Map<String, String> map:data){
				if(map.get("days")!=null){
					//每个人的指定区间风险度累计值
					result.put(map.get("fund_account"), Double.valueOf(map.get("fxd")));
				}
			}
			return result;
		}
		return new HashMap<String, Double>();
	}

	@Override
	public Map<String, Integer> throughWarehouseDays(CalDate date) {
		//每个人一条记录
		List<FundAccountDays> data = paramCalDMMapper.throughWarehouseDays(date);
		Map<String, Integer> result = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(data)){
			for(FundAccountDays map:data){
				if(map.getDays()!=null){
					//每个人的指定区间风险度累计值
					result.put(map.getFund_account(),map.getDays());
				}
			}
			return result;
		}
		return new HashMap<String,Integer>();
	}

	@Override
	public Map<String, Integer> contractSpirit(CalDate date) {
		//每个人一条记录
		List<Map<String, String>> data = paramCalDMMapper.contractSpirit(date);
		Map<String, Integer> result = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(data)){
			for(Map<String, String> map:data){
				if(map.get("is_verify")!=null){
					//每个人的指定区间风险度累计值
					result.put(map.get("fund_account"), Integer.valueOf(map.get("is_verify")));
				}
			}
			return result;
		}
		return new HashMap<String,Integer>();
	}

	@Override
	public Map<String, Integer> forceLiquidationDays(CalDate date) {
		//每个人一条记录
		List<FundAccountDays> data = paramCalDMMapper.forceLiquidationDays(date);
		Map<String, Integer> result = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(data)){
			for(FundAccountDays map:data){
				if(map.getDays()!=null){
					//每个人的指定区间风险度累计值
					result.put(map.getFund_account(),map.getDays());
				}
			}
			return result;
		}
		return new HashMap<String,Integer>();
	}

	@Override
	public Map<String, Integer> cooperateDays(CalDate date) {
		//每个人一条记录
		List<FundAccountDays> data = paramCalDMMapper.cooperateDays(date);
		Map<String, Integer> result = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(data)){
			for(FundAccountDays map:data){
				if(map.getDays()!=null){
					//每个人的指定区间风险度累计值
					result.put(map.getFund_account(),map.getDays());
				}
			}
			return result;
		}
		return new HashMap<String,Integer>();
	}

	@Override
	public Map<String, Set<String>> investmentContracts(CalDate date) {
		List<Map<String, String>> data = paramCalDMMapper.investmentContracts(date);
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		if(!CollectionUtils.isEmpty(data)){
			for(Map<String, String> map:data){
				if(result.get(map.get("fund_account"))==null){
					result.put(map.get("fund_account"), new HashSet<String>());
				}
				result.get(map.get("fund_account")).add(map.get("contract"));
			}
		}
		return new HashMap<String,Set<String>>();
	}

	@Override
	public Map<String, List<ParamSettings>> paramSettings() {
		List<ParamSettings> data = paramCalDMMapper.paramSettings();
		Map<String, List<ParamSettings>> res = new HashMap<>();
		if(!CollectionUtils.isEmpty(data)){
			for(ParamSettings setting:data){
				if(!res.containsKey(setting.getIdenty())){
					res.put(setting.getIdenty(),new ArrayList<ParamSettings>());
				}
				res.get(setting.getIdenty()).add(setting);
			}
			return res;
		}
		return null;
	}

	@Override
	public Map<String, ReportFormEntity> lastCycleReportForm(CalDate date, int type) {
		List<ReportFormEntity> data = paramCalDMMapper.lastCycleAvgRiskRate(date, type);
		Map<String, ReportFormEntity> res = new HashMap<>();
		if(!CollectionUtils.isEmpty(data)){
			for(ReportFormEntity m:data){
				  res.put(m.getFundAccount(),m);
		    }
			return res;
		}
		return null;
	}

	@Override
	public int findTask(CalDate param) {
		ClassifyCalLogEntity log = new ClassifyCalLogEntity();
		log.setCircleEndDate(param.getEndDate());
		log.setCircleStartDate(param.getBeginDate());
		log.setCircleType(KiiikContants.CIRCLE_NOT_FIXED);
		log.setStatus(KiiikContants.TASK_DOING);
		List<ClassifyCalLogEntity> data = genericDao.queryDBEntityList(log);
		if(!CollectionUtils.isEmpty(data)){
			return data.size();
		}
		return 0;
	}
    
}