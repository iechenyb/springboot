package com.cyb.net;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.cyb.cmd.CmdUtils;
import com.cyb.date.DateUtil;
import com.cyb.po.NetClass;
import com.cyb.po.NetResult;

@Component
public class PingTask {
	public NetResult execute(String domain) {
		NetResult nr = new NetResult();
		String rs = CmdUtils.exeCMDWithResult("ping " + domain);
		nr = new NetResult();
		nr.setType("ip");
		nr.setDomain(domain);
		nr.setIp(domain);
		nr.setName("百度服务-default");
		nr.setTime(DateUtil.timeToSec());
		if (rs.contains("找不到主机")||rs.contains("请求超时")) {
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
		String rs = CmdUtils.exeCMDWithResult("ping " + cls.getIp());
		nr = new NetResult();
		nr.setType("ip");
		nr.setIp(cls.getIp());
		nr.setPort(cls.getPort());
		nr.setSysType(cls.getSysType());
		nr.setDomain(cls.getDomain());
		nr.setName(cls.getName());
		nr.setTime(DateUtil.timeToSec());
		if (rs.contains("找不到主机")||rs.contains("请求超时")) {
			nr.setZt(0);
			nr.setInfor("通道异常!");
		} else {
			nr.setZt(1);
			nr.setInfor("通道正常");
		}
		return nr;
	}
}