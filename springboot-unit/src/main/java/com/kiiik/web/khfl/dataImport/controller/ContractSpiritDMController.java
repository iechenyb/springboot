package com.kiiik.web.khfl.dataImport.controller;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.kiiik.web.khfl.dataImport.bean.ContractSpiritEntity;

import io.swagger.annotations.ApiOperation;
/**
 *创建者 : qinxiang<br>
 *类描述: 契约精神数据管理<br>
 *创建时间: 2018年11月7日
 */
@RestController
@RequestMapping("cs")
public class ContractSpiritDMController extends BaseController{
	
	Log log = LogFactory.getLog(ContractSpiritDMController.class);
	
	@Autowired
	GenericService genericService;
	
	@GetMapping("list")
	@ApiOperation("契约精神列表分页查询")
	public ResultBean<List<ContractSpiritEntity>> listContraSpiritsPage(ContractSpiritEntity cs,KiiikPage page){
		List<ContractSpiritEntity> listcs = genericService.queryDBEntityList(cs,page.getCurPage(),page.getPageSize()," record_date desc");
		return new ResultBean<List<ContractSpiritEntity>>(listcs).success("列表返回成功");
	}
	

	@PostMapping("add")
	@ApiOperation("新增客户契约精神")
	public ResultBean<Integer> addContractSpirit(@RequestBody ContractSpiritEntity cs){
		//判断同一个日期是否有重复客户契约精神
		ContractSpiritEntity cs_tmp = null;
		cs_tmp = genericService.queryDBEntitySingleComplex(ContractSpiritEntity.class, 
				new ComplexCondition()
				.and()
				.col("fundAccount")
				.eq(cs.getFundAccount())
				.and()
				.col("recordDate")
				.eq(cs.getRecordDate()));
		int count = 0 ;
		if(cs_tmp==null){
			String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
			cs.setCreateTime(currentTime);
			cs.setUpdateTime(currentTime);
			count = genericService.insertDBEntity(cs);
			return new ResultBean<Integer>(count).success("客户契约精神插入成功!");
		}
		return new ResultBean<Integer>(count).fail("已存在客户契约精神！");
	}
	
	@PostMapping("update")
	@ApiOperation("更新客户契约精神")
	public ResultBean<Integer> updContractSpirit(@RequestBody ContractSpiritEntity cs){
	    ContractSpiritEntity cs_tmp = null;
		cs_tmp = genericService.queryDBEntitySingleComplex(ContractSpiritEntity.class, 
				new ComplexCondition().col("id")
				.notIn(cs.getId())
				.and(new ComplexCondition()
						.col("fundAccount")
						.eq(cs.getFundAccount())
						.and()
						.col("recordDate")
						.eq(cs.getRecordDate())
					)
				);
		if(cs_tmp!=null){
			return new ResultBean<Integer>().success(cs.getFundAccount()+"在"+cs.getRecordDate()+"已经有契约精神记录！");
		}else{
			//修改信息需要重新审核
			cs.setIsVerify("0");
	       //更新修改时间
			String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
			cs.setUpdateTime(currentTime);
			int count = genericService.updateDBEntityByKey(cs);
			return new ResultBean<Integer>(count).success("更新成功！更新记录数"+count);
		}
		
	}
	
	@GetMapping("deleteByIds")
	@ApiOperation("根据主键批量删除客户契约精神")
	public ResultBean<String> delContractSpiritBatch(@RequestParam("ids") List<Integer> ids){
		ContractSpiritEntity cs = new ContractSpiritEntity();
		int count = genericService.deleteDBEntityByKeyBatchs(cs,ids);
		return new ResultBean<String>().success("删除"+count+"条记录！");
	}
	
	@GetMapping("verify")
	@ApiOperation("审核客户契约精神")
	public ResultBean<Integer> verifyById(@RequestParam("id") Integer id,@RequestParam("is_verify") String is_verify){
	    //获取用户EmpNo
		String empNo = getUser().getEmpNo();
		String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
		ContractSpiritEntity cs = new ContractSpiritEntity();
		cs.setId(id);
		cs.setIsVerify(is_verify);
		cs.setVerifyTime(currentTime);
		cs.setVerifyId(empNo);
		int count = genericService.updateDBEntityByKey(cs);
		return new ResultBean<Integer>(count).success("客户契约精神审核完成！更新记录数"+count);
	}
}
