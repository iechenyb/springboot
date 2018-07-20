package com.kiiik.vas.example.service;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.vas.example.bean.Person;
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
    @Autowired
    CacheManager cacheManager;
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 查询缓存数据,测试并发内存击穿<br>
     *创建时间: 2017年7月15日hj12
     *@param person
     *@return
     */
    private Lock personlock =  new ReentrantLock();
    @Cacheable(value = "people", key = "#person.id")//3
    public Person findOne(Person person) {
    	//personlock.lock();//并发的时候，如果多个数据同时进入当前方法，则直接读内存数据！
    	if(cacheManager.getCache("person")!=null){
	    	Person personTmp = cacheManager.getCache("person").get(person.getId(), Person.class);
	    	if(personTmp!=null){
	    		return personTmp;
	    	}
    	}
    	//personlock.unlock();
    	/**
    	 * 从数据库中查询
    	 */
        System.out.println("从db中读取用户信息:" + person.getId() + "数据做了缓存");
        return person;
    }
    //#name.length() < 32
    @Cacheable(value = "people", condition = "#cache>=5")//3
    public Person cacheCondition(int cache) {
    	System.out.println("cache>5的时候缓存,当前cache="+cache);
    	return new Person("1","chenyb");
    }
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 根据返回值进行缓存，如果code=1缓存，code!=1则移除缓存数据且不缓存<br>
     *创建时间: 2017年7月15日hj12
     *@param person
     *@return
     */
    @SuppressWarnings("unchecked")
	@CachePut(value = "people", key="#person.id",unless = "#result.code eq 1")//操作成功 进行缓存
    public ResultBean<Person> cacheReturnCondition(Person person) {
    	System.out.println("读取数据库"+person.getId());
    	if(person.getId().equals("10")){
    		return new ResultBean<Person>(person).fail();
    	}else{
    		return new ResultBean<Person>(person).success();
    	}
    }
    /*ey = "'roomId_'+ #result.id"
    condition="#user.id%2==0"
    condition = "#p0.number==1" 必须有key 正确
    condition = "#p0.id==\"1\"" 正确
    condition = "#p0.id.equals(\"1\") 正确
   */
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 根据参数进行判断是否缓存<br>
     *创建时间: 2017年7月15日hj12
     *@param person
     *@return
     */
    @SuppressWarnings("unchecked")
	@Cacheable(value = "people", key="#person.id",condition = "#p0.id.equals(\"1\")")//操作成功 进行缓存
    public ResultBean<Person> cacheCondition(Person person) {
    	System.out.println("读取数据库"+person.getId());
    	return new ResultBean<Person>(person).success();
    }
    
}
