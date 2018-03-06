package com.cyb.service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.base.HibernateBaseDao;
import com.cyb.dao.LoginLogDaoImpl;
import com.cyb.dao.LoginLogHisDaoImpl;
import com.cyb.date.DateUtil;
import com.cyb.po.MyUser;
import com.cyb.po.UserLoginLog;
import com.cyb.po.UserLoginLogHistory;
@Service
public class LoginLogServiceImpl {
	Log log = LogFactory.getLog(LoginLogServiceImpl.class);
	@Autowired
	LoginLogDaoImpl dao;
	
	@Autowired
	LoginLogHisDaoImpl hisDao;
	
	@Autowired
	HibernateBaseDao<UserLoginLogHistory> userLoginLogHistory ;
	
	@Autowired
	HibernateBaseDao<UserLoginLog> userLoginLog ;
	@PersistenceContext
	private EntityManager entityManager;
	public synchronized void saveLoginLog(MyUser user,HttpServletRequest req){
		//查找原来的人员登录日志（唯一的）
		UserLoginLog loginLog = dao.get("username", user.getUsername());
		if(loginLog==null){
			loginLog = new UserLoginLog();
			//loginLog.setId(0);
			loginLog.setCount(1l);
	        loginLog.setIp(req.getRemoteHost());
	        loginLog.setLastTime(DateUtil.timeToMilis());
	        loginLog.setUsername(user.getUsername());
	        loginLog.setLoginTime(DateUtil.timeToMilis());
	        userLoginLog.save(loginLog);//更新不能用，save能用！！！！？？？
		}else{
			loginLog.setCount(loginLog.getCount()+1);
			loginLog.setLastTime(loginLog.getLoginTime());
			loginLog.setLoginTime(DateUtil.timeToMilis());
			entityManager.merge(loginLog);
			dao.update(null);
		}
    	UserLoginLogHistory loginLogHis = new UserLoginLogHistory();
    	//loginLogHis.setId(0);
    	loginLogHis.setIp(req.getRemoteHost());
    	loginLogHis.setUsername(user.getUsername());
    	loginLogHis.setLoginTime(DateUtil.timeToMilis());
        userLoginLogHistory.save(loginLogHis);
	}
}
