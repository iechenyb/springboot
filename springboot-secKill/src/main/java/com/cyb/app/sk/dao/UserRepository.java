package com.cyb.app.sk.dao;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cyb.app.sk.po.User;

@Repository  
@Table(name="t_user")  
@Qualifier("userRepository")  
public interface UserRepository extends CrudRepository<User,Long>{  
  
    @Query("select t from User t where t.token=:token")  
    public User findUserByToken(@Param("token") String token);  
}  
