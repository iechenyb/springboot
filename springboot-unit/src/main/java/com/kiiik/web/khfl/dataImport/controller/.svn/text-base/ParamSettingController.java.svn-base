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
import com.kiiik.web.khfl.dataImport.bean.ParamSettingEntity;

import io.swagger.annotations.ApiOperation;
/**
 *创建者 : qinxiang<br>
 *类描述:报表指标数据管理<br>
 *创建时间: 2018年11月7日
 */
@RestController
@RequestMapping("ps")
public class ParamSettingController extends BaseController{
	
	Log log = LogFactory.getLog(ParamSettingController.class);
	
	@Autowired
	GenericService genericService;
	
	/*
	@PostMapping("list")
	@ApiOperation("指标列表")
	public ResultBean<List<ParamSettingEntity>> listParamSettings(@RequestBody ParamSettingEntity ps){
		List<ParamSettingEntity> listps = genericService.queryDBEntityList(ps," id asc");
		return new ResultBean<List<ParamSettingEntity>>(listps).success("列表返回成功");
	}*/
	
	
	@GetMapping("list")
	@ApiOperation("指标列表分页查询")
	public ResultBean<List<ParamSettingEntity>> listContraSpiritsPage(ParamSettingEntity ps,KiiikPage page){
		List<ParamSettingEntity> listps = genericService.queryDBEntityList(ps,page.getCurPage(),page.getPageSize()," id asc");
		return new ResultBean<List<ParamSettingEntity>>(listps).success("列表返回成功");
	}
	

	@PostMapping("add")
	@ApiOperation("新增指标")
	public ResultBean<Integer> addContractSpirit(@RequestBody ParamSettingEntity ps){
		//判断是否有重名指标
		ParamSettingEntity ps_tmp = null;
		ps_tmp = genericService.queryDBEntitySingleComplex(ParamSettingEntity.class, 
				new ComplexCondition()
				.and()
				.col("paramName")
				.eq(ps.getParamName())
				.or()
				.col("paramIdenty")
				.eq(ps.getParamIdenty()));
		
		int count = 0 ;
		
		if(ps_tmp!=null){
			return new ResultBean<Integer>(count).fail("已存在同名的指标！");
		}else{
			String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
			ps.setCreateTime(currentTime);
			ps.setUpdateTime(currentTime);
			count = genericService.insertDBEntity(ps);
			return new ResultBean<Integer>(count).success("指标插入成功!");
		}
	}
	
	@PostMapping("update")
	@ApiOperation("更新指标")
	public ResultBean<Integer> updParamEntity(@RequestBody ParamSettingEntity ps){
		ParamSettingEntity ps_tmp = null;
		ps_tmp = genericService.queryDBEntitySingleComplex(ParamSettingEntity.class, 
				new ComplexCondition().col("id")
				.notIn(ps.getId())
				.and(new ComplexCondition()
						.col("paramName")
						.eq(ps.getParamName())
						.or()
						.col("paramIdenty")
						.eq(ps.getParamIdenty())
					)
				);
		
		if(ps_tmp!=null){
			return new ResultBean<Integer>().success("已存在同名的指标！");
		}else{
	       //更新修改时间
			String currentTime = ConcurrentDateUtil.format(ConcurrentDateUtil.PATTEN_OF_DATE, new Date());
			ps.setUpdateTime(currentTime);
			int count = genericService.updateDBEntityByKey(ps);
			return new ResultBean<Integer>(count).success("更新成功！更新记录数"+count);
		}
		
	}
	
	/*@GetMapping("deleteById")
	@ApiOperation("根据主键删除单个指标")
	public ResultBean<String> delParamEntity(Integer id){
		ParamSettingEntity ps = new ParamSettingEntity();
		ps.setId(id);
		int count = genericService.deleteDBEntityByKey(ps);
		return new ResultBean<String>().success("删除"+count+"条记录！");
	}*/
	
	@GetMapping("deleteByIds")
	@ApiOperation("根据主键批量删除指标")
	public ResultBean<String> delParamEntities(@RequestParam("ids") List<Integer> ids){
		ParamSettingEntity ps = new ParamSettingEntity();
		int count = genericService.deleteDBEntityByKeyBatchs(ps,ids);
		return new ResultBean<String>().success("删除"+count+"条记录！");
	}
	
}
