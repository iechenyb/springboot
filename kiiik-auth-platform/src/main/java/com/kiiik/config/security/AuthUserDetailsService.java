package com.kiiik.config.security;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午9:39:39
 */

import com.kiiik.vas.base.dao.UserAuthDao;
@Service
public class AuthUserDetailsService implements UserDetailsService{
	Log log = LogFactory.getLog(AuthUserDetailsService.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	public final String sqlLoadUser;
	public final String sqlLoadAuthorities;
	/*private final RowMapper<User> myUserDetailsRowMapper;
	private final RowMapper<GrantedAuthority> authorityRowMapper;*/
 
 
	public AuthUserDetailsService() {
		super();
		sqlLoadUser = "SELECT id,username,password,enabled FROM user WHERE username=? OR phoneNumber=? OR email=?";
		sqlLoadAuthorities = "SELECT authority FROM view_role WHERE username=?";
		/*myUserDetailsRowMapper = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(rs.getLong(1), rs.getString(2),
						rs.getString(3), rs.getBoolean(4));
			}
		};
		authorityRowMapper = new RowMapper<GrantedAuthority>() {
			@Override
			public GrantedAuthority mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new SimpleGrantedAuthority(rs.getString(1));
			}
		};*/
	}
	@Autowired
    UserAuthDao authDao;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		log.info("查询用户信息:"+username);
		try {
			/*User userFromQuery = jdbcTemplate.queryForObject(sqlLoadUser,
					myUserDetailsRowMapper, username, username, username);
			List<GrantedAuthority> authorities = jdbcTemplate.query(
					sqlLoadAuthorities, authorityRowMapper, username);
			return new User(userFromQuery.getId(), userFromQuery.getUsername(),
					userFromQuery.getPassword(), userFromQuery.isEnabled(),
					authorities);*/
			return new User(1l,username,username,true,authDao.getAuthorities(username));
		} catch (EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
	}

}
