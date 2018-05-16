package com.kiiik.vas.pub.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kiiik.vas.pub.bean.Person;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月16日
 */
@Service
public class CacheService {
	String cacheName="people";
	Log log = LogFactory.getLog(CacheService.class);
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 将数据存入缓存中<br>
	 *创建时间: 2017年7月15日hj12
	 *@param person
	 *@return
	 */
    @CachePut(value = "people", key = "#person.id")
    public Person save(Person person) {
        System.out.println("从db中读取用户信息:" + person.getId() + "数据做了缓存");
        return person;
    }
    //key值的类型必须与缓存时的类型一样，否则当使用long缓存，string提取的时候，容易出错
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 删除缓存数据<br>
     *创建时间: 2017年7月15日hj12
     *@param id
     */
    @CacheEvict(value = "people",key = "#id")//2allEntries=true
    public void remove(String id) {
        System.out.println("删除了id、key为" + id + "的数据缓存");
        //这里不做实际删除操作
    }
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 查询缓存数据<br>
     *创建时间: 2017年7月15日hj12
     *@param person
     *@return
     */
    @Cacheable(value = "people", key = "#person.id")//3
    public Person findOne(Person person) {
    	/**
    	 * 从数据库中查询
    	 */
        System.out.println("从db中读取用户信息:" + person.getId() + "数据做了缓存");
        return person;
    }
}
