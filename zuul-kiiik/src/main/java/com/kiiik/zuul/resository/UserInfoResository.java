package com.kiiik.zuul.resository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiiik.zuul.web.user.bean.UserEntity;

import java.util.List;

public interface UserInfoResository extends JpaRepository<UserEntity,Integer> {
    List<UserEntity> findByuserName(String userName);
}
