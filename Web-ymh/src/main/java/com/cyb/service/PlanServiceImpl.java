package com.cyb.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.dao.PlanDaoImpl;
import com.cyb.dao.PlanRepository;
import com.cyb.dao.PlanTypeDaoImpl;
import com.cyb.po.Plan;
import com.cyb.po.PlanType;
import com.cyb.utils.HttpRequest;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月15日
 */
@Service
public class PlanServiceImpl {
	Log log = LogFactory.getLog(PlanServiceImpl.class);

	@Autowired
	PlanDaoImpl dao;

	@Autowired
	PlanTypeDaoImpl daoType;

	@Autowired
	PlanRepository planRep;
	
	@Autowired
	PlanTypeDaoImpl planType;

	public synchronized void savePlan(String url) {
		List<PlanType> types = daoType.getAll();
		for (PlanType type : types) {
			try {
				System.out.println(type.getJhmc() + "," + type.getUrl());
				if (!type.getJhbh().equals("12")) {// &&type.getJhlx().equals("a")
					url = type.getUrl();
					Map<String, StringBuffer> jhs = HttpRequest.sendGetJh(url, null);
					Iterator<String> keys = jhs.keySet().iterator();
					while (keys.hasNext()) {
						Plan p = null;
						p = planRep.findPlan(type.getJhlx(), type.getJhbh());
						if (p == null) {
							p = new Plan();
							p.setJhlx(type.getJhlx());
							p.setJhbh(type.getJhbh());
							p.setContent(jhs.get(keys.next()).toString());
							dao.save(p);
						} else {
							p.setContent(jhs.get(keys.next()).toString());
							dao.update(p);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(type.getJhmc() + "," + e.getMessage());
			}
		}
		/*
		 * url =
		 * "http://www.28ma.net/index.php?get=neirong&username=17NS1246227907&type=houerdanshi&time=";
		 * Map<String,StringBuffer> jhs = HttpRequest.sendGetJh(url, null);
		 * Iterator<String> keys = jhs.keySet().iterator();
		 * while(keys.hasNext()){ Plan p = new Plan(); p.setJhlx("x");
		 * p.setJhbh("y"); p.setContent(jhs.get(keys.next()).toString());
		 * dao.save(p); }
		 */
	}

}
