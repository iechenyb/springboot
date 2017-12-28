package com.cyb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.dao.PlanRepository;
import com.cyb.dao.PlanTypeDaoImpl;
import com.cyb.dao.PlanTypeRepository;
import com.cyb.po.PlanType;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月15日
 */
@Controller
@RequestMapping("plan")
public class PlanController {
	Log log = LogFactory.getLog(PlanController.class);
	
	@Autowired
	PlanTypeDaoImpl planType;
	
	@Autowired
	PlanRepository planRep;
	
	@Autowired
	PlanTypeRepository planTypeRep;
	
	@GetMapping("/types")
	@ResponseBody
	public List<PlanType> types() {
		return planType.getAll();
	}
	
	@GetMapping("/getPlan")
	public ModelAndView getList1(String jhbh,String jhlx,HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("phone/plan/content");
		view.addObject("data",planRep.findPlan(jhlx, jhbh));
		 view.addObject("username",req.getSession().getAttribute("userid"));
		view.addObject("t1",planTypeRep.findPlanJhlx("cq"));//大类1
		view.addObject("t2",planTypeRep.findPlanJhlx("pk10"));//大类2
		return view ;
	}

}
