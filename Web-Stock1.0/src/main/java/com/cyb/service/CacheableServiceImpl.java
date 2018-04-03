package com.cyb.service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cyb.dao.UserDaoImpl;
import com.cyb.po.MyUser;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月3日
 */
@Service
public class CacheableServiceImpl {
	Log log = LogFactory.getLog(CacheableServiceImpl.class);
	@Autowired
	UserDaoImpl userDao;
	/**
	 * Spring 在执行 @Cacheable 标注的方法前先查看缓存中是否有数据，
	 * 如果有数据，则直接返回缓存数据；若没有数据，执行该方法并将方法返回值放进缓存。 
	   参数： value缓存名、 key缓存键值、 condition满足缓存条件、unless否决缓存条件 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param name 
	 *@return
	 */
	@Cacheable(value = "user", key = "#user_id")  
	public MyUser getUserByName(Long user_id){
		Long id = user_id;
		MyUser user = new MyUser();
		user.setUser_id(id);
		user.setUsername("iechenyb"+id);
		user.setZt("any"+id);
		user.setPassword("dont tell you"+id);
		System.out.println("查询用户信息从db中...");
		return user;
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 把方法的返回值放入缓存中, 主要用于数据新增和修改方法<br>
	 *创建时间: 2017年7月15日hj12
	 *@param user
	 *@return
	 */
	@CachePut(value = "user", key = "#user.user_id")  
	public MyUser save(MyUser user) {  
		System.out.println("增加用户信息到db...");
	    return user;  
	}  
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 说点啥<br>
	 *创建时间: 2017年7月15日hj12
	 *@param user
	 *@return
	 */
	@CacheEvict(value = "user", key = "#user.user_id") // 移除指定key的数据  
	public MyUser delete(MyUser user) {  
	    System.out.println("deleteByKey from db...");
	    return user;  
	}  
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 移除user下所有的用户信息<br>
	 *创建时间: 2017年7月15日hj12
	 */
	@CacheEvict(value = "user", allEntries = true) // 移除所有数据  
	public void deleteAll() {  
	    System.out.println("deleteAll");
	} 
}
