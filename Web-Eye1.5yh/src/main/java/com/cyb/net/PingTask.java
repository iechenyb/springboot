package com.cyb.net;

import org.springframework.stereotype.Component;

import com.cyb.cmd.CmdUtils;
import com.cyb.date.DateUtil;
import com.cyb.po.NetClass;
import com.cyb.po.NetResult;

@Component
public class PingTask {
	public NetResult execute(String domain) {
		NetResult nr = new NetResult();
		String ip = "www.baidu.com";
		String rs = CmdUtils.exeCMDWithResult("ping " + ip);
		nr = new NetResult();
		nr.setType("ip");
		nr.setIp(ip);
		nr.setDomain(ip);
		nr.setName("百度服务");
		nr.setTime(DateUtil.timeToSec());
		if (rs.contains("找不到主机")) {
			nr.setZt(0);
			nr.setInfor("通道异常!");
		} else {
			nr.setZt(1);
			nr.setInfor("通道正常");
		}
		return nr;
	}
	
	public NetResult execute(NetClass cls) {
		NetResult nr = new NetResult();
		String ip = "www.baidu.com";
		String rs = CmdUtils.exeCMDWithResult("ping " + ip);
		nr = new NetResult();
		nr.setType("port");
		nr.setIp(cls.getIp());
		nr.setDomain(cls.getDomain());
		nr.setName(cls.getName());
		nr.setTime(DateUtil.timeToSec());
		if (rs.contains("找不到主机")) {
			nr.setZt(0);
			nr.setInfor("通道异常!");
		} else {
			nr.setZt(1);
			nr.setInfor("通道正常");
		}
		return nr;
	}
}