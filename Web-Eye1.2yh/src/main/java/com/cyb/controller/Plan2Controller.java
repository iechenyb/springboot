package com.cyb.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyb.aop.ResultBean;
import com.cyb.contants.PlanContants;
import com.cyb.dao.PlanRepository;
import com.cyb.dao.PlanTypeDaoImpl;
import com.cyb.dao.PlanTypeRepository;
import com.cyb.file.ObjectFileUtils;
import com.cyb.po.Plan;
import com.cyb.po.PlanType;

/**
 * 作者 : iechenyb<br>
 * 类描述: 异步刷新版本<br>
 * 创建时间: 2017年12月15日
 */
@Controller
@RequestMapping("plan2")
public class Plan2Controller {
	Log log = LogFactory.getLog(Plan2Controller.class);
	
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
	
	@GetMapping("/cq")
	@ResponseBody
	public ResultBean<List<PlanType>> cqTypes() {
		return new ResultBean<List<PlanType>>(planTypeRep.findPlanJhlx("cq"));
	}
	
	@GetMapping("/pk10")
	@ResponseBody
	public ResultBean<List<PlanType>> pk10Types() {
		return new ResultBean<List<PlanType>>(planTypeRep.findPlanJhlx("pk10"));
	}
	
	@GetMapping("/getUser")
	@ResponseBody
	public ResultBean<String> getUser(HttpServletRequest req) {
		return new ResultBean<String>(req.getSession().getAttribute("userid").toString());
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		List<Plan> list=(List<Plan>) ObjectFileUtils.readObjectFromFile( "c:/data/plan/01-cq.obj");
		for(Plan p:list){
			/*if(p.getJhlx().equals("01")||p.getJhlx().equals("02")||p.getJhlx().equals("11")){
				if(p.getJhbh().equals("cq")){
					if(p.getContent().split("<br><br>").length>3){
						p.setContentHidden(p.getContent().split("<br><br>")[1]);
					}
				}
			}
			p.setContentHidden(p.getContent());*/
			if(p.getContent().split("<br><br>").length==4)
			System.out.println(p.getContent().split("<br><br>")[1]);
		}
	} 
	@GetMapping("/getPlan")
	@ResponseBody
	public ResultBean<Object> getPlans(String jhbh,String jhlx,HttpServletRequest req) {
		log.info("获取计划信息...");
		Object  list =  ObjectFileUtils.readObjectFromFile(PlanContants.context + "" + jhbh + "-" + jhlx + ".obj");
		/*for(Plan p:list){
			if(p.getJhlx().equals("01")||p.getJhlx().equals("02")||p.getJhlx().equals("11")){
				if(p.getJhbh().equals("cq")){
					if(p.getContent().split("<br><br>").length>3){
						p.setContentHidden(p.getContent().split("<br><br>")[1]);
					}
				}
			}
			p.setContentHidden(p.getContent());
		}*/
		return new ResultBean<Object>(list);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/getPlanFromFile")
	public Map<String, StringBuffer> getList(String jhbh,String jhlx,HttpServletRequest req) {
		Map<String, StringBuffer> jhs = (Map<String, StringBuffer>) ObjectFileUtils.readObjectFromFile("d:/data/plan/"+jhbh+"-"+jhlx+".obj");
		return jhs ;
	}

}
