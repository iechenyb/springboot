package com.cyb.app.user.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cyb.app.user.pojo.User;

@Repository  
@Table(name="t_user")  
@Qualifier("userRepository")  
public interface UserRepository extends CrudRepository<User,Long>{  
    public User findOne(Long id);  
  
    @Query("select t from User t where t.username=:name")  
    public User findUserByName(@Param("name") String name);  
}  
