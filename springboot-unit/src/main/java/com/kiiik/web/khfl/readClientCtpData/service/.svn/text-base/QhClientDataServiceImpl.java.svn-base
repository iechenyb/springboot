package com.kiiik.web.khfl.readClientCtpData.service;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.dao.GenericDao;
import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.web.khfl.dataImport.bean.DataImportLogEntity;
import com.kiiik.web.khfl.dataImport.bean.DayForceCloseTradeEntity;
import com.kiiik.web.khfl.dataImport.bean.DayFundsEntity;
import com.kiiik.web.khfl.dataImport.bean.DayPositionDetailEntity;
import com.kiiik.web.khfl.dataImport.bean.DayTransactionDetailEntity;
import com.kiiik.web.khfl.dataImport.bean.QhCuinvestorpropertyEntity;
import com.kiiik.web.khfl.dataImport.bean.QhCurrinvestorsEntity;
import com.kiiik.web.khfl.dataImport.bean.ValidclientStartdateEntity;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportStatus;
import com.kiiik.web.khfl.readClientCtpData.constants.ImportDataEnum.DataImportType;
import com.kiiik.web.khfl.readClientCtpData.mapper.QhClientDataMapper;
import com.kiiik.web.khfl.readClientCtpData.vo.ImportStatus;
import com.kiiik.web.khfl.reportCaculate.constants.ClientDataConstants;
import com.kiiik.web.khfl.reportCaculate.mapper.ParamCalDMMapper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 
 *方法描述: 获取期货客户CTP数据<br>
 *创建时间: 2018-11-16
 *@return
 */
@Service
public class QhClientDataServiceImpl {
	Log log = LogFactory.getLog(QhClientDataServiceImpl.class);
    
	@Autowired
	QhClientDataMapper qhClientDataMapper;
	
	@Autowired
	ParamCalDMMapper paramCalDMMapper;
	
	@Autowired
	GenericDao genericDao;
	
	@Autowired
	ClientDataConstants clientDataConstants;
	
	@Autowired
	Environment env;
	
	private static final String IN_PUT_FUNDS = "insert into t_cf_day_funds(fund_account,record_date,khqy,fxd,zjbzj) values(?,?,?,?,?)";
	private static final String IN_PUT_FORCE_CLOSE_TRADE = "insert into t_cf_day_force_close_trade(fund_account,record_date) values(?,?)";
	private static final String IN_PUT_POSITION_DETAIL = "insert into t_cf_day_position_detail(fund_account,record_date,contract,buy_positions,sell_positions) values(?,?,?,?,?)";
	private static final String IN_PUT_TRANSCTION_DETAIL = "insert into t_cf_day_transaction_detail(fund_account,record_date) values(?,?)";
	private static final String IN_PUT_CURR_INVESTORS = "insert into t_cf_qh_currinvestors(fund_account,investor_name,is_active) values(?,?,?)";
	private static final String IN_PUT_CURR_INVEPROPERTY  = "insert into t_cf_qh_cuinvestorproperty(fund_account,freeze_status) values(?,?)";
	private static final String IN_PUT_START_DATE  = "insert into t_cf_validclient_startdate(fund_account,start_date) values(?,?)";
	
	// 导入客户日资金数据
	public ResultBean<Integer>  getDayFundsDataByDate(String operator,int mode,Long[] dateArr){
		if (dateArr.length > 1){
				Long start = dateArr[0];
				Long end = dateArr[1];
				log.info("开始日期="+start+",结束日期="+end);
				
				//删除日期范围内的数据
				DayFundsEntity df = new DayFundsEntity();
		        int delRows = genericDao.deleteDBEntityByDate(df, start, end);
		        log.info("删除数量="+delRows);
		        
				//读取oracle中CTP数据
				List<DayFundsEntity> list = qhClientDataMapper.getDayFundsData(start, end);
				log.info("导入数据数量："+list.size());
				
		        if (list.size() > 0){
		        	DataImportLogEntity dile = new DataImportLogEntity();
		        	dile.setOperator(operator);//操作员
		        	dile.setSelectStartDate(String.valueOf(start));//选择的开始日期
		        	dile.setSelectEndDate(String.valueOf(end));//选择的结束日期
		        	dile.setImportDataType(DataImportType.DAY_FUNDS.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
		        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
		        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
		        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
		        	
		        	//创建保存
		        	genericDao.insertDBEntity(dile);
		        	
		        	int count = 0;
		        	
		        	String url = env.getProperty("spring.datasource.authdb.url");
					String user = env.getProperty("spring.datasource.authdb.username");
					String password = env.getProperty("spring.datasource.authdb.password");
					
				    Connection conn = null;
				    PreparedStatement pstm =null;
				    try {
				        Class.forName("com.mysql.jdbc.Driver");
				        conn = (Connection) DriverManager.getConnection(url, user, password);        
				        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_FUNDS);
				        conn.setAutoCommit(false);
				        Long startTime = System.currentTimeMillis();
				        for (int i = 0; i < list.size(); i++) {
				        	DayFundsEntity temp = list.get(i);
				        	
				        	pstm.setString(1, temp.getFundAccount().trim());
				        	pstm.setInt(2, temp.getRecordDate());
				        	pstm.setDouble(3, temp.getKhqy());
				        	pstm.setDouble(4, temp.getFxd());
				        	pstm.setDouble(5, temp.getZjbzj());
				        	
				            pstm.addBatch();
				            
				            count++;
							if (count % 500 == 0) {
								pstm.executeBatch();
								
								conn.commit();
							}
				        }
				        pstm.executeBatch();
				        conn.commit();
				      
				        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
				        
				        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
			        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
				        
			        	log.info("共插入数量="+count);
				        dile.setImportDataNumbers(count);//插入数据量
				        
				        //更新保存
			        	genericDao.updateDBEntityByKey(dile);
				        
				        Long endTime = System.currentTimeMillis();
				        log.info("操作用时：" + (endTime - startTime)); 
				    } catch (Exception e) {
				    	
				    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
				        
				        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
			        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
				        
			        	log.info("共插入数量="+count);
				        dile.setImportDataNumbers(count);//插入数据量
				        
				        //更新保存
			        	genericDao.updateDBEntityByKey(dile);
			        	
				        e.printStackTrace();
				        throw new RuntimeException(e);
				    }finally{
				        if(pstm!=null){
				            try {
				                pstm.close();
				            } catch (SQLException e) {
				                e.printStackTrace();
				                throw new RuntimeException(e);
				            }
				        }
				        if(conn!=null){
				            try {
				                conn.close();
				            } catch (SQLException e) {
				                e.printStackTrace();
				                throw new RuntimeException(e);
				            }
				        }
				    }
				    return new ResultBean<Integer>(count).success("客户日资金数据导入成功");
				}else{
					return new ResultBean<Integer>().fail("选择日期内没有数据！");
				}
			}else{
				return new ResultBean<Integer>().fail("请选择日期！");
			}
	}
	
	
	//导入客户日强平记录
	public ResultBean<Integer> getDayForceTradeByDate(String operator,int mode,Long[] dateArr){
		if (dateArr.length > 1){
			Long start = dateArr[0];
			Long end = dateArr[1];
			log.info("开始日期="+start+",结束日期="+end);
			
			//删除日期范围内的数据
			DayForceCloseTradeEntity dfc = new DayForceCloseTradeEntity();
			int delRows = genericDao.deleteDBEntityByDate(dfc, start, end);
	        log.info("删除数量="+delRows);
	        
			//读取oracle中CTP数据
			List<DayForceCloseTradeEntity> list = qhClientDataMapper.getDayForceTradeByDate(start, end);
	        
			if (list.size() > 0){
				DataImportLogEntity dile = new DataImportLogEntity();
	        	dile.setOperator(operator);//操作员
	        	dile.setSelectStartDate(String.valueOf(start));//选择的开始日期
	        	dile.setSelectEndDate(String.valueOf(end));//选择的结束日期
	        	dile.setImportDataType(DataImportType.DAY_FORCE_TRADE.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
	        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
	        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
	        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
	        	
	        	//创建保存
	        	genericDao.insertDBEntity(dile);
	        	
				int count = 0;
				String url = env.getProperty("spring.datasource.authdb.url");
				String user = env.getProperty("spring.datasource.authdb.username");
				String password = env.getProperty("spring.datasource.authdb.password");
				
			    Connection conn = null;
			    PreparedStatement pstm =null;
			    try {
			        Class.forName("com.mysql.jdbc.Driver");
			        conn = (Connection) DriverManager.getConnection(url, user, password);        
			        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_FORCE_CLOSE_TRADE);
			        conn.setAutoCommit(false);
			        Long startTime = System.currentTimeMillis();
			        for (int i = 0; i < list.size(); i++) {
			        	DayForceCloseTradeEntity temp = list.get(i);
			        	//System.out.println(temp.getId()+temp.getFundAccount()+temp.getRecordDate());
			        	pstm.setString(1, temp.getFundAccount().trim());
			        	pstm.setInt(2, temp.getRecordDate());
			        	
			            pstm.addBatch();
			            
			            count++;
						if (count % 500 == 0) {
							pstm.executeBatch();
							
							conn.commit();
						}
			        }
			        pstm.executeBatch();
			        conn.commit();
			        
			        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+count);
			        dile.setImportDataNumbers(count);//插入数据量
			        
			        //更新保存
		        	genericDao.updateDBEntityByKey(dile);
			        
			        Long endTime = System.currentTimeMillis();
			        log.info("操作用时：" + (endTime - startTime)); 
			    } catch (Exception e) {
			    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+count);
			        dile.setImportDataNumbers(count);//插入数据量
			        
			        //更新保存
		        	genericDao.updateDBEntityByKey(dile);
		        	
			        e.printStackTrace();
			        throw new RuntimeException(e);
			    }finally{
			        if(pstm!=null){
			            try {
			                pstm.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			                throw new RuntimeException(e);
			            }
			        }
			        if(conn!=null){
			            try {
			                conn.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			                throw new RuntimeException(e);
			            }
			        }
			    }
			    return new ResultBean<Integer>(count).success("客户日强平记录导入成功");
				
			}else{
				return new ResultBean<Integer>().fail("选择日期内没有数据！");
			}
			
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}
	}
	
	//导入客户日持仓明细
	public ResultBean<Integer> getPositionDetail(String operator,int mode,Long[] dateArr){
		if (dateArr.length > 1){
			Long start = dateArr[0];
			Long end = dateArr[1];
			log.info("开始日期="+start+",结束日期="+end);
			
			//删除日期范围内的数据
			DayPositionDetailEntity dpd = new DayPositionDetailEntity();
	        int delRows = genericDao.deleteDBEntityByDate(dpd, start, end);
	        log.info("删除数量="+delRows);
	        
			//读取oracle中CTP数据
			List<DayPositionDetailEntity> list = qhClientDataMapper.getPositionDetail(start, end);
	       
			if (list.size() > 0){
				DataImportLogEntity dile = new DataImportLogEntity();
	        	dile.setOperator(operator);//操作员
	        	dile.setSelectStartDate(String.valueOf(start));//选择的开始日期
	        	dile.setSelectEndDate(String.valueOf(end));//选择的结束日期
	        	dile.setImportDataType(DataImportType.DAY_POSITION.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
	        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
	        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
	        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
	        	
	        	//创建保存
	        	genericDao.insertDBEntity(dile);
				
			  	int count = 0;
	        	
	        	String url = env.getProperty("spring.datasource.authdb.url");
				String user = env.getProperty("spring.datasource.authdb.username");
				String password = env.getProperty("spring.datasource.authdb.password");
				
			    Connection conn = null;
			    PreparedStatement pstm =null;
			    try {
			        Class.forName("com.mysql.jdbc.Driver");
			        conn = (Connection) DriverManager.getConnection(url, user, password);        
			        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_POSITION_DETAIL);
			        conn.setAutoCommit(false);
			        Long startTime = System.currentTimeMillis();
			        for (int i = 0; i < list.size(); i++) {
			        	DayPositionDetailEntity temp = list.get(i);
			        	
			        	pstm.setString(1, temp.getFundAccount().trim());
			        	pstm.setInt(2, temp.getRecordDate());
			        	pstm.setString(3, temp.getContract());
			        	pstm.setDouble(4, temp.getBuyPositions());
			        	pstm.setDouble(5, temp.getSellPositions());
			        	
			            pstm.addBatch();
			            
			            count++;
						if (count % 500 == 0) {
							pstm.executeBatch();
							
							conn.commit();
						}
			        }
			        pstm.executeBatch();
			        conn.commit();
			        
			        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+count);
			        dile.setImportDataNumbers(count);//插入数据量
			        
			        //更新保存
		        	genericDao.updateDBEntityByKey(dile);
			        
			        Long endTime = System.currentTimeMillis();
			        log.info("操作用时：" + (endTime - startTime)); 
			    } catch (Exception e) {
			    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+count);
			        dile.setImportDataNumbers(count);//插入数据量
			        
			        //更新保存
		        	genericDao.updateDBEntityByKey(dile);
		        	
			        e.printStackTrace();
			        throw new RuntimeException(e);
			    }finally{
			        if(pstm!=null){
			            try {
			                pstm.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			                throw new RuntimeException(e);
			            }
			        }
			        if(conn!=null){
			            try {
			                conn.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			                throw new RuntimeException(e);
			            }
			        }
			    }
			    return new ResultBean<Integer>(count).success("客户日资金数据导入成功");
			}else{
				return new ResultBean<Integer>().fail("选择日期内没有数据！");
			}
			
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}

	}
	
	//导入客户日成交明细
	public ResultBean<Integer> getTransDetail(String operator,int mode,Long[] dateArr){
		if (dateArr.length > 1){
			Long start = dateArr[0];
			Long end = dateArr[1];
			log.info("开始日期="+start+",结束日期="+end);
			
			//删除日期范围内的数据
			DayTransactionDetailEntity dtd = new DayTransactionDetailEntity();
	        int delRows = genericDao.deleteDBEntityByDate(dtd, start,end);
	        log.info("删除数量="+delRows);
	        
			//读取oracle中CTP数据
			List<DayTransactionDetailEntity> list = qhClientDataMapper.getTransDetail(start, end);
	       
			if (list.size() > 0){
				DataImportLogEntity dile = new DataImportLogEntity();
	        	dile.setOperator(operator);//操作员
	        	dile.setSelectStartDate(String.valueOf(start));//选择的开始日期
	        	dile.setSelectEndDate(String.valueOf(end));//选择的结束日期
	        	dile.setImportDataType(DataImportType.DAY_TRANS.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
	        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
	        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
	        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
	        	
	        	//创建保存
	        	genericDao.insertDBEntity(dile);
	        	
				int count = 0;
				String url = env.getProperty("spring.datasource.authdb.url");
				String user = env.getProperty("spring.datasource.authdb.username");
				String password = env.getProperty("spring.datasource.authdb.password");
				
			    Connection conn = null;
			    PreparedStatement pstm =null;
			    try {
			        Class.forName("com.mysql.jdbc.Driver");
			        conn = (Connection) DriverManager.getConnection(url, user, password);        
			        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_TRANSCTION_DETAIL);
			        conn.setAutoCommit(false);
			        Long startTime = System.currentTimeMillis();
			        for (int i = 0; i < list.size(); i++) {
			        	DayTransactionDetailEntity temp = list.get(i);
			        	//System.out.println(temp.getId()+temp.getFundAccount()+temp.getRecordDate());
			        	pstm.setString(1, temp.getFundAccount().trim());
			        	pstm.setInt(2, temp.getRecordDate());
			        	
			            pstm.addBatch();
			            
			            count++;
						if (count % 500 == 0) {
							pstm.executeBatch();
							
							conn.commit();
						}
			        }
			        pstm.executeBatch();
			        conn.commit();

			        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+count);
			        dile.setImportDataNumbers(count);//插入数据量
			        
			        //更新保存
		        	genericDao.updateDBEntityByKey(dile);
			        
			        Long endTime = System.currentTimeMillis();
			        log.info("操作用时：" + (endTime - startTime)); 
			    } catch (Exception e) {
			    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
			        
			        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
		        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
			        
		        	log.info("共插入数量="+count);
			        dile.setImportDataNumbers(count);//插入数据量
			        
			        //更新保存
		        	genericDao.updateDBEntityByKey(dile);
		        	
			    	e.printStackTrace();
			        throw new RuntimeException(e);
			    }finally{
			        if(pstm!=null){
			            try {
			                pstm.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			                throw new RuntimeException(e);
			            }
			        }
			        if(conn!=null){
			            try {
			                conn.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			                throw new RuntimeException(e);
			            }
			        }
			    }
			    return new ResultBean<Integer>(count).success("客户日成交明细导入成功");
			}else{
				return new ResultBean<Integer>().fail("选择日期内没有数据！");
			}
		}else{
			return new ResultBean<Integer>().fail("请选择日期！");
		}
	}
	
	//导入客户信息
	public ResultBean<Integer> getCurrInvestorDetail(String operator,int mode){
		//1.全表删除
		QhCurrinvestorsEntity vce = new QhCurrinvestorsEntity();
		int delrows = genericDao.deleteFullDBEntity(vce);
		log.info("删除数量="+delrows);
		
		//2.全表插入
		List<QhCurrinvestorsEntity> list = qhClientDataMapper.getCurrInvestorDetail(null);
		
		if (list.size() > 0){
			DataImportLogEntity dile = new DataImportLogEntity();
        	dile.setOperator(operator);//操作员
        	dile.setSelectStartDate(null);//选择的开始日期
        	dile.setSelectEndDate(null);//选择的结束日期
        	dile.setImportDataType(DataImportType.CURR_INVESTOR.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
        	
        	//创建保存
        	genericDao.insertDBEntity(dile);
        	
			int count = 0;
			String url = env.getProperty("spring.datasource.authdb.url");
			String user = env.getProperty("spring.datasource.authdb.username");
			String password = env.getProperty("spring.datasource.authdb.password");
			
		    Connection conn = null;
		    PreparedStatement pstm =null;
		    try {
		        Class.forName("com.mysql.jdbc.Driver");
		        conn = (Connection) DriverManager.getConnection(url, user, password);        
		        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_CURR_INVESTORS);
		        conn.setAutoCommit(false);
		        Long startTime = System.currentTimeMillis();
		        for (int i = 0; i < list.size(); i++) {
		        	QhCurrinvestorsEntity temp = list.get(i);
		        	//System.out.println(temp.getId()+temp.getFundAccount()+temp.getRecordDate());
		        	pstm.setString(1, temp.getFundAccount().trim());
		        	pstm.setString(2, temp.getInvestorName());
		        	pstm.setString(3, temp.getIsActive());
		        	
		            pstm.addBatch();
		            
		            count++;
					if (count % 500 == 0) {
						pstm.executeBatch();
						
						conn.commit();
					}
		        }
		        pstm.executeBatch();
		        conn.commit();
		        
		        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
		        
		        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
		        
	        	log.info("共插入数量="+count);
		        dile.setImportDataNumbers(count);//插入数据量
		        
		        //更新保存
	        	genericDao.updateDBEntityByKey(dile);
		        
		        Long endTime = System.currentTimeMillis();
		        log.info("操作用时：" + (endTime - startTime)); 
		    } catch (Exception e) {
		    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
		        
		        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
		        
	        	log.info("共插入数量="+count);
		        dile.setImportDataNumbers(count);//插入数据量
		        
		        //更新保存
	        	genericDao.updateDBEntityByKey(dile);
	        	
		        e.printStackTrace();
		        throw new RuntimeException(e);
		    }finally{
		        if(pstm!=null){
		            try {
		                pstm.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException(e);
		            }
		        }
		        if(conn!=null){
		            try {
		                conn.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException(e);
		            }
		        }
		    }
		    return new ResultBean<Integer>(count).success("客户信息导入成功");
		}else{
			log.info("数据表里没有数据！");
			return new ResultBean<Integer>().fail("数据表里没有数据！");
		}

	}
	
	//导入客户属性信息
	public ResultBean<Integer> getCurrInvProperties(String operator,int mode){
		 //1.全表删除
		QhCuinvestorpropertyEntity vce = new QhCuinvestorpropertyEntity();
        int delrows = genericDao.deleteFullDBEntity(vce);
        log.info("删除数量="+delrows);
       // return new ResultBean<Integer>(delrows).success("数据表里没有数据！");
		//2.全表插入
		List<QhCuinvestorpropertyEntity> list = qhClientDataMapper.getCurrInvProperties();
       
		if (list.size() > 0){
			DataImportLogEntity dile = new DataImportLogEntity();
        	dile.setOperator(operator);//操作员
        	dile.setSelectStartDate(null);//选择的开始日期
        	dile.setSelectEndDate(null);//选择的结束日期
        	dile.setImportDataType(DataImportType.CURR_INV_PROPERTIES.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
        	
        	//创建保存
        	genericDao.insertDBEntity(dile);
        	
			int count = 0;
			String url = env.getProperty("spring.datasource.authdb.url");
			String user = env.getProperty("spring.datasource.authdb.username");
			String password = env.getProperty("spring.datasource.authdb.password");
			
		    Connection conn = null;
		    PreparedStatement pstm =null;
		    try {
		        Class.forName("com.mysql.jdbc.Driver");
		        conn = (Connection) DriverManager.getConnection(url, user, password);        
		        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_CURR_INVEPROPERTY);
		        conn.setAutoCommit(false);
		        Long startTime = System.currentTimeMillis();
		        for (int i = 0; i < list.size(); i++) {
		        	QhCuinvestorpropertyEntity temp = list.get(i);
		        	//System.out.println(temp.getId()+temp.getFundAccount()+temp.getRecordDate());
		        	pstm.setString(1, temp.getFundAccount().trim());
		        	pstm.setString(2, temp.getFreezeStatus());
		        	
		            pstm.addBatch();
		            
		            count++;
					if (count % 500 == 0) {
						pstm.executeBatch();
						
						conn.commit();
					}
		        }
		        pstm.executeBatch();
		        conn.commit();
		        
		        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
		        
		        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
		        
	        	log.info("共插入数量="+count);
		        dile.setImportDataNumbers(count);//插入数据量
		        
		        //更新保存
	        	genericDao.updateDBEntityByKey(dile);
		        
		        Long endTime = System.currentTimeMillis();
		        log.info("操作用时：" + (endTime - startTime)); 
		    } catch (Exception e) {
		    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
		        
		        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
		        
	        	log.info("共插入数量="+count);
		        dile.setImportDataNumbers(count);//插入数据量
		        
		        //更新保存
	        	genericDao.updateDBEntityByKey(dile);
	        	
		        e.printStackTrace();
		        throw new RuntimeException(e);
		    }finally{
		        if(pstm!=null){
		            try {
		                pstm.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException(e);
		            }
		        }
		        if(conn!=null){
		            try {
		                conn.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException(e);
		            }
		        }
		    }
		    return new ResultBean<Integer>(count).success("客户属性信息导入成功");
		}else{
			log.info("数据表里没有数据！");
			return new ResultBean<Integer>().fail("数据表里没有数据！");
		}
	}
	
	//导入客户的开始交易日期
	public ResultBean<Integer> getClientStatrtDate(String operator,int mode){
		 //1.全表删除
		ValidclientStartdateEntity vse = new ValidclientStartdateEntity();
        int delrows = genericDao.deleteFullDBEntity(vse);
        log.info("删除数量="+delrows);
		//2.全表插入
		List<ValidclientStartdateEntity> list = qhClientDataMapper.getClientStatrtDate();
       
		//所有有效客户设置开始交易日期
		if (list.size() > 0){
			DataImportLogEntity dile = new DataImportLogEntity();
        	dile.setOperator(operator);//操作员
        	dile.setSelectStartDate(null);//选择的开始日期
        	dile.setSelectEndDate(null);//选择的结束日期
        	dile.setImportDataType(DataImportType.CLIENT_START_DATE.getStateNum());//数据导入类别 1：日资金数据  2：日强平数据 3：日持仓明细 4：日成交明细 5：客户信息 6：客户属性 7：客户开始交易日期
        	dile.setImportStatus(DataImportStatus.IMPORTING.getStateNum());//数据导入状态 0：正在导入 1：导入完成 2：导入失败
        	dile.setImportMode(mode);//数据导入方式  0：自动  1：手动 
        	String operateStartTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
        	dile.setOperationStartTime(operateStartTime);//导入操作开始时间
        	
        	//创建保存
        	genericDao.insertDBEntity(dile);
        	
			int count = 0;
			String url = env.getProperty("spring.datasource.authdb.url");
			String user = env.getProperty("spring.datasource.authdb.username");
			String password = env.getProperty("spring.datasource.authdb.password");
			
		    Connection conn = null;
		    PreparedStatement pstm =null;
		    try {
		        Class.forName("com.mysql.jdbc.Driver");
		        conn = (Connection) DriverManager.getConnection(url, user, password);        
		        pstm = (PreparedStatement) conn.prepareStatement(IN_PUT_START_DATE);
		        conn.setAutoCommit(false);
		        Long startTime = System.currentTimeMillis();
		        for (int i = 0; i < list.size(); i++) {
		        	ValidclientStartdateEntity temp = list.get(i);
		        	//System.out.println(temp.getId()+temp.getFundAccount()+temp.getRecordDate());
		        	pstm.setString(1, temp.getFundAccount().trim());
		        	pstm.setString(2, temp.getStartDate());
		        	
		            pstm.addBatch();
		            
		            count++;
					if (count % 500 == 0) {
						pstm.executeBatch();
						
						conn.commit();
					}
		        }
		        pstm.executeBatch();
		        conn.commit();

		        dile.setImportStatus(DataImportStatus.IMPORT_SUCCESS.getStateNum());//修改导入状态
		        
		        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
		        
	        	log.info("共插入数量="+count);
		        dile.setImportDataNumbers(count);//插入数据量
		        
		        //更新保存
	        	genericDao.updateDBEntityByKey(dile);
		        
		        Long endTime = System.currentTimeMillis();
		        log.info("操作用时：" + (endTime - startTime)); 
		    } catch (Exception e) {
		    	dile.setImportStatus(DataImportStatus.IMPORT_FAILURE.getStateNum());
		        
		        String operateEndTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date()); 
	        	dile.setOperationEndTime(operateEndTime);//导入操作结束时间
		        
	        	log.info("共插入数量="+count);
		        dile.setImportDataNumbers(count);//插入数据量
		        
		        //更新保存
	        	genericDao.updateDBEntityByKey(dile);
	        	
		        e.printStackTrace();
		        throw new RuntimeException(e);
		    }finally{
		        if(pstm!=null){
		            try {
		                pstm.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException(e);
		            }
		        }
		        if(conn!=null){
		            try {
		                conn.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		                throw new RuntimeException(e);
		            }
		        }
		    }
		    return new ResultBean<Integer>(count).success("客户的开始交易日期导入成功");
		}else{
			log.info("数据表里没有数据！");
			return new ResultBean<Integer>().fail("数据表里没有数据！");
		}
	}
	
	//获取CTP数据导入最新状态信息
	public ResultBean<List<ImportStatus>> getCTPDataImportStatus(String operator){
		List<ImportStatus> list = null;
		try{
			list = paramCalDMMapper.getCTPDataImportStatus(operator);
			if (list != null){
				return new ResultBean<List<ImportStatus>>(list).success("数据导入最新状态获取成功!");
			}
			return new ResultBean<List<ImportStatus>>(list).success("没有数最新导入状态信息!");
		}catch (Exception e) {
	        e.printStackTrace();
	        return new ResultBean<List<ImportStatus>>(list).fail("数据导入最新状态获取失败!");
	    }
		
	}
	

}
