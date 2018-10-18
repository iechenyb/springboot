package com.kiiik.pub.controller;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kiiik.pub.bean.SessionUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
public class BaseController {
	Configuration config = null;
	Log log = LogFactory.getLog(BaseController.class);
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取zuul转发传递的权限信息<br>
	 *创建时间: 2018年10月19日
	 *@return
	 */
	protected String getRemoteUser() {
		String user = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("X-AUTH-ID");
		return user;
	}
	
	protected SessionUser getUser() {
		if(getConfig().getBoolean("platform.integration")){//如果已经集成，则从zuul的转发请求中获取用户信息
			return wrapUserInfor(getRemoteUser());
		}else{
			return new SessionUser("缺省值","default","999999");
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 将请求中的用户信息进行包装<br>
	 *创建时间: 2017年7月15日hj12
	 *@param userInfor
	 *@return
	 */
	private SessionUser wrapUserInfor(String userInfor){
		return  new SessionUser("缺省值","default","999999");//暂时先不实现，等后期确定后在进行实现
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 读取配置文件<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 */
    public  Configuration getConfig(){
        if(config == null){
            synchronized (BaseController.class) {
                if(config == null)
					try {
						config = new PropertiesConfiguration("application.properties");
					} catch (ConfigurationException e) {
						e.printStackTrace();
						return null;
					}
            }
        }
        return config;
    }
}
