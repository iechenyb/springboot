package com.cyb.net;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.cyb.po.NetClass;
import com.cyb.po.NetResult;

@Component
public class UrlTask {

	public NetResult execute(String url) {
		NetResult nr = new NetResult();
		try {
			int status = testConnection(url);
			nr.setType("url");
			nr.setIp(url);
			nr.setDomain(url);
			nr.setName("百度首页");
			nr.setTime(com.cyb.date.DateUtil.timeToSec(new Date()));
			if (status != 200) {
				nr.setZt(0);
				nr.setInfor("请求异常!返回状态码" + status);
			} else {
				nr.setZt(1);
				nr.setInfor("请求正常");
			}
		} catch (Exception e) {
			nr.setZt(0);
			e.printStackTrace();
		}
		return nr;
	}
	
	public NetResult execute(NetClass cls) {
		NetResult nr = new NetResult();
		try {
			int status = testConnection(cls.getUrl());
			nr.setType(cls.getType());
			nr.setIp(cls.getIp());
			nr.setDomain(cls.getDomain());
			nr.setName(cls.getName());
			nr.setTime(com.cyb.date.DateUtil.timeToSec(new Date()));
			if (status != 200) {
				nr.setZt(0);
				nr.setInfor("请求异常!返回状态码" + status);
			} else {
				nr.setZt(1);
				nr.setInfor("请求正常");
			}
		} catch (Exception e) {
			nr.setZt(0);
			e.printStackTrace();
		}
		return nr;
	}

	public static int testConnection(String address) {
		int status = 404;
		try {
			URL urlObj = new URL(address);
			HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();
			oc.setUseCaches(false);
			oc.setConnectTimeout(3000); // 设置超时时间
			status = oc.getResponseCode();// 请求状态
			oc.disconnect();
			oc  = null;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
	}
}