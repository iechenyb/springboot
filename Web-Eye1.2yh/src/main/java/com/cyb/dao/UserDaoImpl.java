package com.cyb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.cyb.base.HibernateBaseDao;
import com.cyb.po.MyUser;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月13日
 */
@Repository
public class UserDaoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	// jpa
	@Autowired
	MyUserRepository myUserRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public MyUser getUserByName(String userName) {
		try {
			MyUser user = this.jdbcTemplate.queryForObject(
					"select user_id,username,password from ms_security_user where username='" + userName + "'",
					new RowMapper<MyUser>() {
						@Override
						public MyUser mapRow(ResultSet rs, int rowNum) throws SQLException {
							System.out.println("读取用户信息。。。");
							MyUser user = new MyUser();
							user.setUser_id(rs.getLong("user_id"));
							user.setUsername(rs.getString("username"));
							user.setPassword(rs.getString("password"));
							return user;
						}
					});
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public void updateTx(int has, String newName,int type) {
		if(type==1){
			updateJdbcTx(has,newName);
		}else if(type==2){
			updateHibernateTx(has,newName);
		}else{
			
		}
	}
	
	public void updateJdbcTx(int has, String newName) {
		try {
			String updateSql1 = "update ms_security_user set username='cyb' where user_id=1 ";
			String updateSql2 = "update ms_security_user set username='" + newName + "'  where user_id=1";
			this.jdbcTemplate.execute(updateSql1);
			if (has == 1) {
				System.out.println(1 / 0);
			}
			this.jdbcTemplate.execute(updateSql2);
		} catch (DataAccessException e) {
			e.printStackTrace();

		}
	}
	public void updateHibernateTx(int has, String newName) {
		try {
			MyUser user = this.entityManager.find(MyUser.class, 1l);
			user.setUsername("cyb");
			this.entityManager.merge(user);
			if (has == 1) {
				System.out.println(1 / 0);
			}
			user.setUsername(newName);
			this.entityManager.merge(user);
		} catch (DataAccessException e) {
			e.printStackTrace();

		}
	}

	/*
	 * public String saveRegister (MyUser user){
	 * if(applicationUserRepository.findByUsername(user.getUsername())!=null){
	 * return "你已经注册！"; }else{
	 * user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	 * applicationUserRepository.save(user); return "注册成功！"; } }
	 */
	public String saveRegister(MyUser user) {
		// return addUserHibernate(user);
		return addUserHibernate(user);
	}

	public String addUserJpa(MyUser user) {
		if (myUserRepository.findByUsername(user.getUsername()) != null) {
			return "你已经注册！";
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			myUserRepository.save(user);
			return "注册成功！";
		}
	}

	public String addUserHibernate(MyUser user) {
		if (getUserByName(user.getUsername()) != null) {
			return "你已经注册！";
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			this.entityManager.persist(user);
			return "注册成功！";
		}
	}

	public MyUser getByNumber(String username) {
		Query query = this.entityManager.createQuery("from MyUser u where u.username=:username", MyUser.class);
		query.setParameter("username", username);
		MyUser user = (MyUser) query.getSingleResult();
		return user;
	}
	/*
	 * public User getById(int id) { //find by primary key return
	 * this.entityManager.find(User.class,id); }
	 * 
	 * public MyUser getByNumber(String number) { Query query =
	 * this.entityManager.createQuery("from User u where u.number=:number"
	 * ,MyUser.class); query.setParameter("number",number); MyUser user =
	 * (MyUser)query.getSingleResult(); return user; }
	 * 
	 * public int addUser(User user) { this.entityManager.persist(user); //print
	 * the id System.out.println(user.getId()); return user.getId(); }
	 * 
	 * public void deleteUserById(int id) { User user =
	 * this.entityManager.find(User.class,id); //关联到记录，方可删除
	 * this.entityManager.remove(user); }
	 * 
	 * public User updateUser(User user) { User userNew =
	 * this.entityManager.merge(user); return userNew; }
	 */
	@Autowired
	HibernateBaseDao<MyUser> hibernateDao ;
	
	@Autowired
	CommonDaoImpl commonDaoImpl;
	
	public void saveMyUser(MyUser user){
		hibernateDao.save(user);
		//commonDaoImpl.save(user);
	}
	//禁用和启用用户
	public void jyUser(String userName,String zt){
		MyUser user = getUserByName(userName);
		user.setZt(zt);
		this.entityManager.merge(user);
	}
}
