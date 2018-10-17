package com.kiiik.zuul.web.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kiiik.zuul.resository.UserAuthDao;
import com.kiiik.zuul.resository.UserInfoResository;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @SuppressWarnings("unused")
	@Autowired
    private UserInfoResository resository;
    @Autowired
    private UserAuthDao authDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // log.info("用户的用户名: {}", username);
    	System.out.println("用户名："+username);
       /* // 根据用户名，查找到对应的密码，与权限
        List<UserInfo> result = resository.findByuserName(username);
        UserInfo userInfo = result.get(0);
        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        return new User(username, userInfo.getUserPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));*/
		try {
			/*User userFromQuery = jdbcTemplate.queryForObject(sqlLoadUser,
					myUserDetailsRowMapper, username, username, username);
			List<GrantedAuthority> authorities = jdbcTemplate.query(
					sqlLoadAuthorities, authorityRowMapper, username);
			return new User(userFromQuery.getId(), userFromQuery.getUsername(),
					userFromQuery.getPassword(), userFromQuery.isEnabled(),
					authorities);*/
			return new User(username,username,authDao.getAuthorities(username));
		} catch (EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
    }
}