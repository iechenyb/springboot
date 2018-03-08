package com.cyb.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.dao.NetClsDaoImpl;
import com.cyb.po.NetClass;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月15日
 */
@Service
public class NetClsServiceImpl {
	Log log = LogFactory.getLog(NetClsServiceImpl.class);
	
	
	@Autowired
	NetClsDaoImpl dao;
	
	public List<NetClass> getList(){
		return  dao.getAll();
	}
}
