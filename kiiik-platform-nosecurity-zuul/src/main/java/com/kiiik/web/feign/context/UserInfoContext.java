package com.kiiik.web.feign.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kiiik.pub.bean.SessionUser;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2019年1月3日
 */
public class UserInfoContext {
	Log log = LogFactory.getLog(UserInfoContext.class);
	private static ThreadLocal<SessionUser> userInfo = new ThreadLocal<SessionUser>();
	public static String KEY_USERINFO_IN_HTTP_HEADER = "X-AUTH-ID";

	public UserInfoContext() {
	}

	public static SessionUser getUser() {
		return userInfo.get();
	}

	public static void setUser(SessionUser user) {
		userInfo.set(user);
	}
}
