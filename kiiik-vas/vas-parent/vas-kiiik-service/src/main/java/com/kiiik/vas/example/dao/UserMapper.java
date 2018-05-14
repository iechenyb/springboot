package com.kiiik.vas.example.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kiiik.vas.example.model.MyUser;

/**
 * 作者 : iechenyb<br>
 * 类描述: sql直接写在注解方法上<br>
 * 创建时间: 2018年5月11日
 */
@Mapper
public interface UserMapper {
	Log log = LogFactory.getLog(UserMapper.class);

	@Select("select * from t_user where account =#{acc}")
	MyUser findMyUsersByName(String acc);

	@Delete("delete from  t_user where id =#{id}")
	int deleteMyUserById(int id);

	@Delete("delete from  t_user where account =#{account}")
	int deleteMyUserByMyUserName(String account);

	@Insert("insert into t_user(id,account, password) values(#{id},#{password},#{account})")
	int insert(MyUser MyUser);

	@Select("select * from t_user")
	List<MyUser> getAllMyUsers();

	@Select("select * from t_user  limit #{start},#{end} ")
	List<MyUser> getPageMyUsers(int start, int end);

	// 分页查询不能使用 需要用xml写 这个不太有用
	@Update("update t_user set password=#{password} where id=#{id}")
	int updateMyUser(MyUser MyUser);
}
