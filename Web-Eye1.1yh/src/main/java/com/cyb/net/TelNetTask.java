package com.cyb.net;

import org.springframework.stereotype.Component;

import com.cyb.cmd.CmdUtils;
import com.cyb.date.DateUtil;
import com.cyb.po.NetClass;
import com.cyb.po.NetResult;

@Component
public class TelNetTask{
    public  NetResult execute(String ip,String port) {
    	NetResult nr;
			String rs = CmdUtils.exeCMDWithResult("telnet "+ip +" "+port);
			nr = new NetResult();
			nr.setType("port");
			nr.setIp(ip);
			nr.setDomain(ip);
			nr.setName("本地服务");
			nr.setTime(DateUtil.timeToSec());
			if(rs.contains("连接失败")){
				nr.setZt(0);
				nr.setInfor("端口异常!");
			}else{
				nr.setZt(1);
				nr.setInfor("端口正常");
			}
			return nr;
    }
    public  NetResult execute(NetClass cls) {
    		NetResult nr;
			String rs = CmdUtils.exeCMDWithResult("telnet "+cls.getIp() +" "+cls.getPort());
			nr = new NetResult();
			nr.setType("port");
			nr.setIp(cls.getIp());
			nr.setDomain(cls.getDomain());
			nr.setName(cls.getName());
			nr.setTime(DateUtil.timeToSec());
			if(rs.contains("连接失败")){
				nr.setZt(0);
				nr.setInfor("端口异常!");
			}else{
				nr.setZt(1);
				nr.setInfor("端口正常");
			}
			return nr;
    }
}