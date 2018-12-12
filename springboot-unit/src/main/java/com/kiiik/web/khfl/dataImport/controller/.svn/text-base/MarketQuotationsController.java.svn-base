package com.kiiik.web.khfl.dataImport.controller;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.ConcurrentDateUtil;
import com.kiiik.web.khfl.dataImport.bean.MarketQuotationsEntity;

import io.swagger.annotations.ApiOperation;

/**
 *创建者 : qinxiang<br>
 *类描述: 市场风险数据管理<br>
 *创建时间: 2018年11月7日
 */
@RestController
@RequestMapping("mq")
public class MarketQuotationsController extends BaseController{

	Log log = LogFactory.getLog(MarketQuotationsController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
    
    /**
     *分页读取列表信息<br>
     */
	@GetMapping("list")
	@ApiOperation(value = "/list", notes = "分页查询 ")
	public ResultBean<List<MarketQuotationsEntity>> listUsersPage(MarketQuotationsEntity entity,@ModelAttribute @Validated KiiikPage page) {
		List<MarketQuotationsEntity> entitys = genericService.queryDBEntityList(entity, page.getCurPage(), page.getPageSize(), " record_date desc");
		return new ResultBean<List<MarketQuotationsEntity>>(entitys).success("列表返回成功");
	}
    
    /**
     *新增记录<br>
     */
    @SuppressWarnings("unchecked")
	@PostMapping("add")
	@ApiOperation(value="add",notes="新增信息")
	public ResultBean<Integer> addMarketQuotationsEntity(@RequestBody MarketQuotationsEntity mq){
		//判断同一个日期是否有风险级别信息
		MarketQuotationsEntity mq_tmp = null;
		mq_tmp = genericService.queryDBEntitySingleComplex(MarketQuotationsEntity.class, 
				new ComplexCondition()
				.and()
				.col("recordDate")
				.eq(mq.getRecordDate()));
		int count = 0 ;
		if (mq_tmp != null){//相同日期已经存在数据风险信息则返回
			return new ResultBean().fail(mq.getRecordDate()+"已存在市场风险数据！");
		}
		//保存市场风险数据
		String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
		mq.setCreateTime(currentTime);
		mq.setUpdateTime(currentTime);
		count = genericService.insertDBEntity(mq);
		return new ResultBean<Integer>(count).success("市场风险数据插入成功!");
		
	}
	
	/**
     *更新记录<br>
     */
	@PostMapping("update")
	@ApiOperation(value="update",notes="更新信息")
	public ResultBean<Integer> updMarketQuotationsEntity(@RequestBody MarketQuotationsEntity mq){
		MarketQuotationsEntity mq_tmp = null;
		mq_tmp = genericService.queryDBEntitySingleComplex(MarketQuotationsEntity.class, 
					new ComplexCondition().col("id")
					.notIn(mq.getId())
					.and(new ComplexCondition()
							.col("recordDate")
							.eq(mq.getRecordDate())
						)
					);
			if(mq_tmp!=null){
				return new ResultBean<Integer>().success(mq.getRecordDate()+"已存在市场风险数据！");
			}
			//修改信息需要重新审核
			mq.setIsVerify("0");
	       //更新修改时间
			String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
			mq.setUpdateTime(currentTime);
			int count = genericService.updateDBEntityByKey(mq);
			return new ResultBean<Integer>(count).success("更新成功！更新记录数"+count);
			
	}
	
	 /**
     *根据主键删除记录<br>
     */
	/*
	@GetMapping("deleteById")
	@ApiOperation("根据主键删除单个日期市场风险")
	public ResultBean<String> delMarketQuotation(Integer id){
		MarketQuotationsEntity mq = new MarketQuotationsEntity();
		mq.setId(id);
		int count = genericService.deleteDBEntityByKey(mq);
		return new ResultBean<String>().success("删除"+count+"条记录！");
	}*/
	
	@GetMapping("deleteByIds")
	@ApiOperation("根据主键批量删除市场风险")
	public ResultBean<String> delMarketQuotationBatch(@RequestParam("ids") List<Integer> ids){
		MarketQuotationsEntity mq = new MarketQuotationsEntity();
		int count = genericService.deleteDBEntityByKeyBatchs(mq,ids);
		return new ResultBean<String>().success("删除"+count+"条记录！");
	}
	
	
	@GetMapping("verify")
	@ApiOperation("审核市场风险数据")
	public ResultBean<Integer> verifyById(@RequestParam("id") Integer id,@RequestParam("is_verify") String is_verify){
	    //获取用户EmpNo
		String empNo = getUser().getEmpNo();
		String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
		MarketQuotationsEntity mq = new MarketQuotationsEntity();
		mq.setId(id);
		mq.setIsVerify(is_verify);
		mq.setVerifyTime(currentTime);
		mq.setVerifyId(empNo);
		int count = genericService.updateDBEntityByKey(mq);
		return new ResultBean<Integer>(count).success("市场风险数据审核完成！更新记录数"+count);
	}

}
