package com.cyb.dao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */
@Repository
public class CommonDaoImpl extends HibernateBaseDao<MyUser>{
	Log log = LogFactory.getLog(CommonDaoImpl.class);
}
