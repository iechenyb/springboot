package com.kiiik.vas.base.service.impl;
import com.kiiik.config.security.User;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月12日下午3:50:26
 */
public interface AuthService {
	User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
