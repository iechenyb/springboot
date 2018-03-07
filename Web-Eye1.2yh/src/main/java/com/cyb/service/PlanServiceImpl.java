package com.cyb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.contants.PlanContants;
import com.cyb.dao.PlanDaoImpl;
import com.cyb.dao.PlanRepository;
import com.cyb.dao.PlanTypeDaoImpl;
import com.cyb.date.DateUtil;
import com.cyb.file.FileUtils;
import com.cyb.file.ObjectFileUtils;
import com.cyb.po.Plan;
import com.cyb.po.PlanType;
import com.cyb.utils.HttpRequest;

import net.sf.json.JSONArray;

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
	//http://www.28ma.net/28maoem/28ma/images
    String[] names = new String[]{"计划员1号:青青","计划员2号:小叶","计划员3号:冰兰","计划员4号:童童","计划员5号:小慧","计划员6号:阿丽","计划员7号:白雪","计划员8号:凡儿","计划员9号:安琦","计划员10号:月月"};
    String[] imgs = new String[]{
    		"http://114.115.207.62:8080/ymh/static/img/girl1.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl2.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl3.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl4.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl5.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl6.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl7.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl8.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl9.jpg",
    		"http://114.115.207.62:8080/ymh/static/img/girl10.jpg"};
    /**
     * 存在一个bug，当定时任务执行在servlet初始化之前时，存储路径为空值，需要处理！
     * 思路 在定时任务创建之前，先执行初始化信息。
     *作者 : iechenyb<br>
     *方法描述: 说点啥<br>
     *创建时间: 2017年7月15日hj12
     *@param url
     */
    public void savePlan(String url) {
		List<PlanType> types = daoType.getAll();
		// 将计划数据写入文件
		FileUtils.overideString2File(JSONArray.fromObject(types).toString(), PlanContants.context + "planTyps.obj");
		for (PlanType type : types) {
			try {
				log.info(type.getJhmc() + "," + type.getUrl());
				if (!type.getJhbh().equals("12")) {
					url = type.getUrl();
					Map<String, StringBuffer> jhs = HttpRequest.sendGetJh(url, null);
					List<Plan> list = new ArrayList<Plan>();
					Iterator<String> keys = jhs.keySet().iterator();
					while (keys.hasNext()) {
						Plan p = new Plan();
						String cur = keys.next();
						p.setJhlx(type.getJhlx());
						p.setJhbh(type.getJhbh());
						p.setXh(cur);
						p.setPic(imgs[Integer.valueOf(cur)]);
						p.setName(names[Integer.valueOf(cur)]);
						p.setTime(DateUtil.timeToSec());
						p.setContent(jhs.get(cur).toString().replace("28码", ""));
						list.add(p);
					}
					ObjectFileUtils.writeObjectToFile(list,
							PlanContants.context + type.getJhbh() + "-" + type.getJhlx() + ".obj");
					FileUtils.overideString2File(JSONArray.fromObject(list).toString(),
							PlanContants.context + type.getJhbh() + "-" + type.getJhlx() + ".txt");
				} else {
					String data = HttpRequest.sendGet(type.getUrl(), null);
					List<Plan> list = new ArrayList<Plan>();
					Plan p = new Plan();
					p.setJhlx(type.getJhlx());
					p.setJhbh(type.getJhbh());
					p.setXh("12");
					p.setTime(DateUtil.timeToSec());
					p.setContent(data);
					list.add(p);
					FileUtils.overideString2File(JSONArray.fromObject(list).toString(),
							PlanContants.context + "" + type.getJhbh() + "-" + type.getJhlx() + ".txt");
					ObjectFileUtils.writeObjectToFile(list,
							PlanContants.context + "" + type.getJhbh() + "-" + type.getJhlx() + ".obj");
				}
			} catch (Exception e) {
				System.out.println(type.getJhmc() + "," + e.getMessage());
			}
		}
	}

}
