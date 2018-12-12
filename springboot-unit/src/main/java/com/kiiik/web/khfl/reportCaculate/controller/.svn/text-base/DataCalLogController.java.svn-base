package com.kiiik.web.khfl.reportCaculate.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.PageData;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.khfl.reportCaculate.bean.ClassifyCalLogEntity;
import com.kiiik.web.khfl.reportCaculate.service.ParamCalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月28日上午11:12:35
 */
@RestController
@RequestMapping("calLog")
@Api(value="客户等级计算日志",tags="khflCalLog")
public class DataCalLogController extends BaseController{
	Log log = LogFactory.getLog(DataCalLogController.class);
	
	@Autowired
	GenericService genericService;

	@GetMapping("/list")
	@ApiOperation("日志查询分页")
	public ResultBean<PageData<ClassifyCalLogEntity>> infor(ClassifyCalLogEntity entity,KiiikPage p){
		if(p.needAll()){
			List<ClassifyCalLogEntity> data = genericService.queryDBEntityList(entity);
			return new ResultBean<PageData<ClassifyCalLogEntity>>(new PageData<ClassifyCalLogEntity>(data));
		}else{
			Page<ClassifyCalLogEntity> data = genericService.queryDBEntityList(entity,p.getCurPage(),p.getPageSize(),"start_date desc");
			return new ResultBean<PageData<ClassifyCalLogEntity>>(new PageData<ClassifyCalLogEntity>(data,p));
		}
	}
	
	@Autowired
	ParamCalService paramCalService;
	
	@PutMapping("/off")
	@ApiOperation("任务作废")
	public ResultBean<String> cal(Integer id){
		ClassifyCalLogEntity log = new ClassifyCalLogEntity();
		log.setStatus(KiiikContants.TASK_OFF);
		log.setId(id);
		genericService.updateDBEntityByKey(log);
		return new ResultBean<String>().success("任务关闭成功！");
	}
	
}
