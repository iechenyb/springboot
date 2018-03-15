package com.cyb.vip.dianshiju.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.vip.dianshiju.bean.DianShiJuBean;
import com.cyb.vip.dianshiju.bean.DianShiJuDetailsBean;
import com.cyb.vip.dianshiju.service.AqyDianShiJuService;
import com.cyb.vip.dianshiju.service.YouKuDianShiJuService;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月15日
 */
@RestController
@RequestMapping("vip/youku/dianshiju")
public class YouKuDianShiJuController {
	Log log = LogFactory.getLog(YouKuDianShiJuController.class);
	
	@Autowired
	YouKuDianShiJuService service;
	
	@GetMapping("dsList")
	public  List<DianShiJuBean> getVipDianshijuList(String url) throws IOException{
		return service.initDianShiJuInfor(url);
	}
	
	@GetMapping("details")
	public  List<DianShiJuDetailsBean> details(String url) throws IOException{
		if(StringUtils.isEmpty(url)){
			return new ArrayList<>();
		}
		return getDetails(url);
	}
	
	public  List<DianShiJuDetailsBean> getDetails(String url) throws IOException{
		/**
		 * a[class=site-piclist_pic_link]
		 * a
		 */
		Document doc = Jsoup.connect(url).get();
		Elements aList = doc.select("div[id=listitem_page1] div[class=item item-num] a[class=sn]");
		int num = aList.size();
		List<DianShiJuDetailsBean> ls = new ArrayList<DianShiJuDetailsBean>();
		DianShiJuDetailsBean vo = null;
		for(int i=0;i<num;i++){
			vo = new DianShiJuDetailsBean();
			vo.setIdx(String.valueOf(i+1));
			vo.setUrl("https:"+aList.get(i).attr("href"));
			vo.setDesc(String.valueOf(i+1));
			vo.setName(doc.select("meta[name=irAlbumName]").attr("content"));
			ls.add(vo);
		}
		return ls;
	}
}
