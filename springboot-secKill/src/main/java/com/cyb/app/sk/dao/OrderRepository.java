package com.cyb.app.sk.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cyb.app.sk.po.Order;

@Repository  
@Qualifier("orderRepository")  
public interface OrderRepository extends CrudRepository<Order,Long>{  
    public Order findOne(Long id);  
  
    @Query("select t from Order t where t.userId=:userId and t.goodsName=:goodsName")  
    public Order hasMakeOrder(@Param("userId") long userId,@Param("goodsName") String goodsName);  
}  
