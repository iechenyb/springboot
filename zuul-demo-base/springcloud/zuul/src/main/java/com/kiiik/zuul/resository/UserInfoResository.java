package com.kiiik.zuul.resository;

import com.kiiik.zuul.dataObject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoResository extends JpaRepository<UserInfo,Integer> {
    List<UserInfo> findByuserName(String userName);
}
