package com.cyb.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.dao.PlanRepository;
import com.cyb.dao.PlanTypeDaoImpl;
import com.cyb.po.Plan;
import com.cyb.po.PlanType;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月15日
 */
@RestController
@RequestMapping("plan")
public class PlanController {
	Log log = LogFactory.getLog(PlanController.class);
	
	@Autowired
	PlanTypeDaoImpl planType;
	
	@Autowired
	PlanRepository planRep;
	
	@GetMapping("/types")
	@ResponseBody
	public List<PlanType> types() {
		return planType.getAll();
	}
	
	@GetMapping("/getPlanContent")
	@ResponseBody
	public Plan getList(String jhbh,String jhlx) {
		return planRep.findPlan(jhlx, jhbh);
	}

}
