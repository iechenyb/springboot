package com.cyb.vip.dianshiju.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月15日
 */

import com.cyb.vip.dianshiju.VipConstants;
import com.cyb.vip.dianshiju.bean.DianShiJuBean;

@Service
public class TengXunDianShiJuService {
	Log log = LogFactory.getLog(TengXunDianShiJuService.class);

	// 根据名称模糊查找电视剧信息
	public DianShiJuBean queryDianshijuBean(String name) throws IOException {
		List<DianShiJuBean> ls = /*(List<DianShiJuBean>) ObjectFileUtils
				.readObjectFromFile(PlanContants.context + "dianshiju.obj");*/
				initDianShiJuInfor();
		DianShiJuBean target = null;
		for (DianShiJuBean bean : ls) {
			if (bean.getName().contains(name)) {
				target = bean;
				break;
			}
		}
		return target;
	}

	// 持久化电视剧概要，不持久化电视剧集
	public List<DianShiJuBean> initDianShiJuInfor() throws IOException {
		Document doc = Jsoup.connect(VipConstants.Tengxundsj).get();
		Elements aList = doc.select("li[class=list_item] a[class=figure]");
		int num = aList.size();
		List<DianShiJuBean> ls = new ArrayList<DianShiJuBean>();
		DianShiJuBean vo = null;
		for (int i = 0; i < num; i++) {
			vo = new DianShiJuBean();
			vo.setName(aList.get(i).parent().select("img").get(0).attr("alt"));
			vo.setUrl(aList.get(i).attr("href"));
			ls.add(vo);
			System.out.println(vo.getUrl());
		}
		/*
		 * ObjectFileUtils.writeObjectToFile(ls, PlanContants.context +
		 * "dianshiju.obj");//给定默认名字
		 */
		return ls;
	}
	public static void main(String[] args) throws IOException {
		new TengXunDianShiJuService().initDianShiJuInfor();
		
	}
}
