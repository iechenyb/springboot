package com.cyb.cacheable.service;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cyb.cacheable.bean.News;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月3日
 */
@Service
public class CacheableServiceImpl {
	Log log = LogFactory.getLog(CacheableServiceImpl.class);

	/**
	 * Spring 在执行 @Cacheable 标注的方法前先查看缓存中是否有数据，
	 * 如果有数据，则直接返回缓存数据；若没有数据，执行该方法并将方法返回值放进缓存。 参数： value缓存名、 key缓存键值、
	 * condition满足缓存条件、unless否决缓存条件 作者 : 
	 * iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param name
	 *            key = "#title"+"#id"
	 * @return
	 */
	// 查询一个参数的方法
	@Cacheable(value = "news", keyGenerator = "keyGenerator", cacheManager = "cacheManager")
	public News getNewsById(String id) {
		News news = new News();
		news.setId(id);
		news.setTitle("iechenyb" + id);
		news.setContent("any" + id);
		System.out.println("查询用户信息从db中...");
		return news;
	}

	// 查询两个参数的方法 不会检查差异并同步，需要通过设置有效时间来进行若同步！
	@Cacheable(value = "news#120#100", keyGenerator = "keyGenerator", cacheManager = "cacheManager")
	public News getNewsById(String id, String title) {
		News news = new News();
		news.setId(id);
		news.setTitle("iechenyb" + id);
		news.setContent("查询缓存是否动态更新到缓存！" + new Random().nextInt());
		System.out.println("查询用户信息从db中...");
		return news;
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 把方法的返回值放入缓存中, 主要用于数据新增和修改方法<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param news
	 * @returnkey = "#news.id"
	 */
	@CachePut(value = "news#200#150", keyGenerator = "keyGenerator")
	public News save(String id, String title) {
		System.out.println("增加用户信息到db...");
		News news = new News();
		news.setId(id);
		news.setTitle(title);
		news.setContent("我是更新过的缓存内容");
		return news; // 返回值更新到数据库后，会更新到缓存中！
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param news
	 *            key = "#news.id" keyGenerator = "keyGenerator"
	 * @return
	 */
	@CacheEvict(value = "news", keyGenerator = "keyGenerator") // 移除指定key的数据
	public News delete(String id, String title) {
		System.out.println("deleteByKey from db...");
		return new News();
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 移除news下所有的用户信息<br>
	 * 创建时间: 2017年7月15日hj12
	 */
	@CacheEvict(value = "news", allEntries = true, keyGenerator = "keyGenerator") // 移除所有数据
	public void deleteAll() {
		System.out.println("deleteAll");
	}
}
