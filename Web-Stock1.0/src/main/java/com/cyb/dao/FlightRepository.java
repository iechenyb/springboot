package com.cyb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyb.po.Flight;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月13日
 */

public interface FlightRepository extends JpaRepository<Flight, Long> {
    
}