package com.cyb.utils;

import java.io.IOException;

import com.cyb.url.UrlUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			String res = UrlUtils.downLoadFromUrl("http://180.169.108.228:8080/VASDataCenter/qutoes/infor.html",
					null, null);
			System.out.println(res);
			JSONArray json = JSONArray.fromObject(res);
			JSONObject price = (JSONObject) json.get(0);
			System.out.println(price.getLong("队列剩余数据"));
			long contractQutoesNums= price.getLong("队列剩余数据");
			if(contractQutoesNums==0||contractQutoesNums>10000){
				System.out.println("行情队列数据出现异常！");
			}
			/*JSONArray json = JSONArray.fromObject(res);
			JSONObject price = (JSONObject) json.get(0);
			System.out.println(price.getJSONArray("price"));
			JSONArray ps = price.getJSONArray("price");
			int num = 0;
			for (int i = 0; i < ps.size(); i++) {
				if ("-".equals(ps.getString(i))) {
					num++;
				}
				if (num >= 20) {
					System.out.println("行情出现异常！");
					break;
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
