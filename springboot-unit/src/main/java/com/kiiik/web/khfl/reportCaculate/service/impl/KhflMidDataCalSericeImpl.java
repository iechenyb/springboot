package com.kiiik.web.khfl.reportCaculate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.service.BaseService;
import com.kiiik.utils.TimeContext;
import com.kiiik.web.khfl.reportCaculate.bean.KhflMidDateEntity;
import com.kiiik.web.khfl.reportCaculate.mapper.KhflMidDataMapper;
import com.kiiik.web.khfl.reportCaculate.vo.FundAccountStatics;
import com.kiiik.web.khfl.reportCaculate.vo.FundsInfor;

@Service
public class KhflMidDataCalSericeImpl extends BaseService{
	@Autowired
	KhflMidDataMapper khflMidMapper;
	
	/**
	 * 参数为空时，计算所有
	 */
	public ResultBean<String> calMidData(){
		List<String> dates = khflMidMapper.getDates();
		if(CollectionUtils.isEmpty(dates)){
			return new ResultBean<String>().success("没有可计算的日期！");
		}
		return calMidData(dates);
	}
	
	/**
	 * 参数为空时，计算所有
	 */
	public ResultBean<String> calMidDataFromSomeDay(String ywrq){
		List<String> dates = khflMidMapper.getDates();
		if(CollectionUtils.isEmpty(dates)){
			return new ResultBean<String>().success("没有可计算的日期！");
		}
		int idx = dates.indexOf(ywrq);
		return calMidData(dates.subList(idx, dates.size()));
	}
	
	public ResultBean<String> calMidData(List<String> dates){
		ResultBean<String> res=null;
		for(String ywrq:dates){
			res = calMidData(ywrq);
			if(res.isFail()){//如果计算失败 直接终止计算！因为是累计值计算，所以后边计算没有意义
				return  res;
			}
		}
		return new ResultBean<String>().success("计算成功！");
	}
	public ResultBean<String> calMidData(final String ywrq){
		TimeContext.recordTimeStart();
		try{
			//查询当日需要计算的人员
			List<String> accounts = khflMidMapper.getAccountsOfDay(ywrq);
			if(CollectionUtils.isEmpty(accounts)){
				return new ResultBean<String>().fail("没有需要计算的人员信息！");
			}
			
			List<FundsInfor> funds = khflMidMapper.getFundInfor(ywrq);
			Map<String,FundsInfor> fundsMap = new HashMap<>();
			for(FundsInfor fi:funds){
				fundsMap.put(fi.getFund_account(), fi);
			}
			
			//读取当日的合约数据
			List<FundAccountStatics> contracts = khflMidMapper.getContractsfor(ywrq);
			Map<String,FundAccountStatics> contractsMap = new HashMap<>();
			for(FundAccountStatics c:contracts){
				contractsMap.put(c.getFund_account(),c);
			}
			
			//读取档期强平仓数据
            List<FundAccountStatics> qpData = khflMidMapper.getMatchfor(ywrq, 0);//强平
            Map<String,FundAccountStatics> qpDataMap = new HashMap<>();
			for(FundAccountStatics c:qpData){
				qpDataMap.put(c.getFund_account(),c);
			}
            List<FundAccountStatics> phData = khflMidMapper.getMatchfor(ywrq, 1);//配合
            Map<String,FundAccountStatics> phDataMap = new HashMap<>();
			for(FundAccountStatics c:phData){
				phDataMap.put(c.getFund_account(),c);
			}
            List<KhflMidDateEntity> lastLjData = khflMidMapper.getLastDayLj(ywrq);
            Map<String,KhflMidDateEntity> lastLjDataMap = new HashMap<>();
			for(KhflMidDateEntity c:lastLjData){
				lastLjDataMap.put(c.getFundAccount(),c);
			}
			//删除当日数据
			khflMidMapper.deleteByDay(ywrq);
            List<KhflMidDateEntity> data = new ArrayList<>(1000);
			for(String acc:accounts){
				KhflMidDateEntity entity = new KhflMidDateEntity();
				entity.setFundAccount(acc);
				entity.setRecordDate(ywrq);
				int curHasTrade=1;
				int curCc=0;//当日穿仓
				Double curfxdTotal = 0.0;//风险度累计值
				int curZb=0;//当日是否追保
				if(fundsMap.get(acc)!=null){
					curCc = fundsMap.get(acc).getCc()==null?0:fundsMap.get(acc).getCc();
					curfxdTotal = fundsMap.get(acc).getFxd()==null?0:fundsMap.get(acc).getFxd();
					curZb = fundsMap.get(acc).getZb()==null?0:fundsMap.get(acc).getZb();
				}
				int curContract = 0;//当日合约数
				if(contractsMap.get(acc)!=null){
					curContract = contractsMap.get(acc).getNum()==null?0:contractsMap.get(acc).getNum();
				}
                int curQpDays = 0;//当日强平
                if(qpDataMap.get(acc)!=null){
                	curQpDays = qpDataMap.get(acc).getNum()==null?0:qpDataMap.get(acc).getNum();
                }
                int curPhDays =0;//档期配合强平
                if(phDataMap.get(acc)!=null){
                	curPhDays = phDataMap.get(acc).getNum()==null?0:phDataMap.get(acc).getNum();
                }
				if(lastLjDataMap.get(acc)!=null){
					entity.setCcNumsTotal(lastLjDataMap.get(acc).getCcNumsTotal()==null?curCc:lastLjDataMap.get(acc).getCcNumsTotal()+curCc);
					entity.setFxdTotal(lastLjDataMap.get(acc).getFxdTotal()==null?curfxdTotal:lastLjDataMap.get(acc).getFxdTotal()+curfxdTotal);
					entity.setZbDaysTotal(lastLjDataMap.get(acc).getZbDaysTotal()==null?curZb:lastLjDataMap.get(acc).getZbDaysTotal()+curZb);
					entity.setContractNumsTotal(lastLjDataMap.get(acc).getContractNumsTotal()==null?curContract:lastLjDataMap.get(acc).getContractNumsTotal()+curContract);
				    entity.setQpDaysTotal(lastLjDataMap.get(acc).getQpDaysTotal()==null?curQpDays:lastLjDataMap.get(acc).getQpDaysTotal()+curQpDays);
				    entity.setPhqpDaysTotal(lastLjDataMap.get(acc).getPhqpDaysTotal()==null?curPhDays:lastLjDataMap.get(acc).getPhqpDaysTotal()+curPhDays);
				    entity.setTradeDaysTotal(lastLjDataMap.get(acc).getTradeDaysTotal()==null?curHasTrade:lastLjDataMap.get(acc).getTradeDaysTotal()+curHasTrade);
				}else{
					//第一天计算
					entity.setCcNumsTotal(curCc);//穿仓累计天数
					entity.setZbDaysTotal(curZb);//累计追保发生天数
					entity.setFxdTotal(curfxdTotal);//风险度累计值
					entity.setContractNumsTotal(curContract);//每日不同合约累计数
					entity.setQpDaysTotal(curPhDays);//配合平仓累计天数
					entity.setPhqpDaysTotal(curQpDays);//强平累计天数
					entity.setTradeDaysTotal(curHasTrade);
				}
				entity.setHasTrade(1);//当日有交易
				entity.setCcNums(curCc);
				entity.setZbNums(curZb);
				entity.setFxd(curfxdTotal);
				entity.setContractNums(curContract);
				entity.setQpNums(curQpDays);
				entity.setPhqpNums(curPhDays);
				data.add(entity);
				if(data.size()%1000==0){
					genericDao.insertDBEntityBatchT(data);
					data.clear();
				}
			}
			if(data.size()>0){
				genericDao.insertDBEntityBatchT(data);
				data.clear();
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ResultBean<String>().fail(ywrq+"计算失败！");
		}finally{
		}
		TimeContext.calExecuteTime(ywrq+"累计数据计算完成！");
		return new ResultBean<String>().success("计算成功！");
	}
	
}
