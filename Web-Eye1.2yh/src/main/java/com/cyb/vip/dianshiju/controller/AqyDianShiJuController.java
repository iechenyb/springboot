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

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月15日
 */
@RestController
@RequestMapping("vip/aqy/dianshiju")
public class AqyDianShiJuController {
	Log log = LogFactory.getLog(AqyDianShiJuController.class);
	
	@Autowired
	AqyDianShiJuService service;
	
	@GetMapping("dsList")
	public  List<DianShiJuBean> getVipDianshijuList() throws IOException{
		return service.initDianShiJuInfor();
	}
	
	@GetMapping("details")
	public  List<DianShiJuDetailsBean> details(String name) throws IOException{
		if(StringUtils.isEmpty(name)){
			return new ArrayList<>();
		}
		DianShiJuBean bean =service.queryDianshijuBean(name);
		return getDetails(bean);
	}
	
	public  List<DianShiJuDetailsBean> getDetails(DianShiJuBean bean) throws IOException{
		/**
		 * a[class=site-piclist_pic_link]
		 * a
		 */
		Document doc = Jsoup.connect(bean.getUrl()).get();
		Elements aList = doc.select("li[data-albumlist-elem=playItem] div[class=site-piclist_info] p[class=site-piclist_info_title] a");
		int num = aList.size();
		List<DianShiJuDetailsBean> ls = new ArrayList<DianShiJuDetailsBean>();
		DianShiJuDetailsBean vo = null;
		for(int i=0;i<num;i++){
			vo = new DianShiJuDetailsBean();
			vo.setIdx(aList.get(i).html());
			vo.setUrl(aList.get(i).attr("href"));
			Elements node = aList.get(i).parent().parent().select("p[class=site-piclist_info_describe] a");//div p a 
			vo.setDesc(node.get(0).text());
			vo.setName(bean.getName());
			ls.add(vo);
		}
		return ls;
	}
}
