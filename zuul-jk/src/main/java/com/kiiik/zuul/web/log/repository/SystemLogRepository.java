package com.kiiik.zuul.web.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiiik.zuul.web.log.bean.SystemLog;

import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {
    List<SystemLog> findByUri(String uri);
}
