package com.cyb.utils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

import net.sf.json.JSONObject;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月23日
 */
public class JsonUtils {
	Log log = LogFactory.getLog(JsonUtils.class);
	public static void main(String[] args) throws JSONException {
		Integer a = 100;//单个值可以扔进map
		JSONObject intJson = JSONObject.fromObject(a);
		
		Map<Object,Object> map = createMap(a);
		String json = "{\"data\":\"efg\",\"other\":\"abc\"}"; 
		System.out.println(new org.json.JSONObject(json).getString("data"));
		
		
		reg();
				
	}
	private static void reg() {
	    // TODO Auto-generated method stub
	    String line = "[\"http://yjj-img-main.oss-cn-hangzhou.aliyuncs.com/1440227044447Capture One Session5324.jpg\","  
	    + "\"http://yjj-img-main.oss-cn-hangzhou.aliyuncs.com/1440227044463网漏肩中长上衣.png\","  
	    + "\"http://yjj-img-main.oss-cn-hangzhou.aliyuncs.com/1440227044451Capture One Session5322.jpg\"," 
	    + "\"http://yjj-img-main.oss-cn-hangzhou.aliyuncs.com/1440227044427Capture One Session5326.jpg\"]";

	    Pattern pattern = Pattern.compile("http://(?!(\\.jpg|\\.png)).+?(\\.jpg|\\.png)");
	    Matcher matcher = pattern.matcher(line);
	    while (matcher.find()) {
	        System.out.println(matcher.group(0));
	        System.out.println();
	    }
	}
	public static Map<Object,Object> createMap(Object data){
		  Map<Object,Object> map= new HashMap<>();
		  map.put("data", data);
		  return map;
	}
}
