package com.kiiik.zuul.web.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiiik.zuul.web.log.bean.SystemLog;

public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {
    List<SystemLog> findByUri(String uri);
}
