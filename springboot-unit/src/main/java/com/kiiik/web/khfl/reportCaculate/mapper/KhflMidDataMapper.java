package com.kiiik.web.khfl.reportCaculate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiiik.web.khfl.reportCaculate.bean.KhflMidDateEntity;
import com.kiiik.web.khfl.reportCaculate.vo.FundAccountStatics;
import com.kiiik.web.khfl.reportCaculate.vo.FundsInfor;

public interface KhflMidDataMapper {
	/**
	 * 获取交易人员信息
	 * @param ywrq
	 * @return
	 */
	List<String> getTradeAcounts(@Param("ywrq") String ywrq);
	/**
	 * 
	 * 获取某一天所有人资金账号
	 * @param ywrq
	 * @return
	 */
	List<String> getAccountsOfDay(@Param("ywrq") String ywrq);
	/**
	 * 获取所有的日期
	 * @param ywrq
	 * @return
	 */
	List<String> getDates();
	/**
	 * 删除当日累计数据
	 * @param ywrq
	 */
	void deleteByDay(@Param("ywrq")  String ywrq);
	
	/**
	 * 获取上一个交易日累计值
	 * @param ywrq
	 * @return
	 */
	List<KhflMidDateEntity> getLastDayLj(@Param("ywrq") String ywrq);
	
	/**
	 * 获取资金信息
	 * @param ywrq
	 * @return
	 */
	List<FundsInfor> getFundInfor(@Param("ywrq") String ywrq);
	
	List<FundAccountStatics> getMatchfor(@Param("ywrq") String ywrq,@Param("type")  Integer type);
	/**
	 * 获取每人的合约数据量
	 * @param ywrq
	 * @return
	 */
	List<FundAccountStatics> getContractsfor(@Param("ywrq") String ywrq);
	/**
	 * 求区域边界累计值 每个人一条
	 * @param start
	 * @param end
	 * @param ordor asc 取起始点值  desc 取结束点值
	 * @return
	 */
	List<KhflMidDateEntity> sectionMarginLj(@Param("start") String start,@Param("end") String end,@Param("ordor") String ordor);
	
}