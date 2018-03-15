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

import com.cyb.url.HttpsClient;
import com.cyb.vip.dianshiju.VipConstants;
import com.cyb.vip.dianshiju.bean.DianShiJuBean;

@Service
public class YouKuDianShiJuService {
	Log log = LogFactory.getLog(YouKuDianShiJuService.class);

	// 根据名称模糊查找电视剧信息
	public DianShiJuBean queryDianshijuBean(String url) throws IOException {
		List<DianShiJuBean> ls = /*
									 * (List<DianShiJuBean>) ObjectFileUtils
									 * .readObjectFromFile(PlanContants.context
									 * + "dianshiju.obj");
									 */
		initDianShiJuInfor(url);
		DianShiJuBean target = ls.get(0);
		return target;
	}

	// 持久化电视剧概要，不持久化电视剧集
	public List<DianShiJuBean> initDianShiJuInfor(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements aList = doc.select("div[id=listitem_page1] div[class=item item-num] a[class=sn]");
		int num = aList.size();
		List<DianShiJuBean> ls = new ArrayList<DianShiJuBean>();
		DianShiJuBean vo = null;
		for (int i = 0; i < num; i++) {
			vo = new DianShiJuBean();
			vo.setUrl("http:"+aList.get(i).attr("href"));
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
		String url = "http://v.youku.com/v_show/id_XMzQwMjgyNjQ2MA==.html?spm=a2h03.9268448.t-filter-lists.3!2~5~5~A!2";
		new YouKuDianShiJuService().initDianShiJuInfor(url);
		//System.out.println(HttpsClient.getPageHtml(url));
	}
}
