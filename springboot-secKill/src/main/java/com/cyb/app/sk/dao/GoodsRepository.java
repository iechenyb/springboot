package com.cyb.app.sk.dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cyb.app.sk.po.Goods;

@Repository  
@Qualifier("goodsRepository")  
public interface GoodsRepository extends CrudRepository<Goods,Long>{  
	
    public Goods findOne(Long id);  
    //默认的是注解事务，所以需要加注解控制事务
    @Transactional
    @Modifying
    @Query("update Goods t  set stock=:nums where t.goodsName=:goodsName") 
    public int updateGoods(@Param("goodsName") String  goodsName,@Param("nums") int nums);  

    //扣减库存
	@Transactional
	@Modifying
	@Query("update Goods t  set stock=stock-:buys where t.goodsName=:goodsName and stock-:buys>=0") 
	public int decGoods(@Param("goodsName") String goodsName,@Param("buys") int buys);
	
	
	@Query("select  t from Goods t   where t.goodsName=:goodsName") 
    public Goods queryGoodsStock(@Param("goodsName") String goodsName);  
	
}