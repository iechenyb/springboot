package com.kiiik.zuul.security;

import com.kiiik.zuul.dataObject.UserInfo;
import com.kiiik.zuul.resository.UserInfoResository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoResository resository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户的用户名: {}", username);
        // 根据用户名，查找到对应的密码，与权限
        List<UserInfo> result = resository.findByuserName(username);
        UserInfo userInfo = result.get(0);
        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        return new User(username, userInfo.getUserPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }
}