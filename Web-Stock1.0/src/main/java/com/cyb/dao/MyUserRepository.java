package com.cyb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);
    
    @Query("select p from MyUser p where p.username=?1")
    List<MyUser> findByUsername1(String name);
    
    @Query("select p from MyUser p where p.username=:val")
    List<MyUser> findByUsername2(@Param("val") String name);
    
    @Modifying
    //@Transactional
    @Query("update MyUser p set p.username=?1 where p.user_id=?2 ")
    int updateUserName(String name,Long id);
    
    @Modifying
    @Query(value = "insert into t_sys_org_user(org_id,user_id) values(?1,?2)",nativeQuery = true)
    int addUserToOrg(Long orgId,Long userId);
}