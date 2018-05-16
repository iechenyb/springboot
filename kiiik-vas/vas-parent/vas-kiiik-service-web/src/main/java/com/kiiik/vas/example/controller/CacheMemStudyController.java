package com.kiiik.vas.example.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.vas.example.bean.Person;
import com.kiiik.vas.example.service.CacheService;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月16日
 */
@RestController
@RequestMapping("cache")
public class CacheMemStudyController {
	Log log = LogFactory.getLog(CacheMemStudyController.class);
	@Autowired
	CacheService personService;
	
	@Autowired
	CacheManager cacheManager;

	@GetMapping("/getFromCacheManager")
	public Person getFromCacheManager(String id) {
		Cache cache = cacheManager.getCache("people");
		cache.clear();//清除所有缓存
		cache.evict(id);//移除指定的key
		ValueWrapper vm = cache.get(id);
		Person p = null;
		if(vm!=null)//返回值
		{
			p = (Person) vm.get();
	    }
		return p;
	}
	
	@GetMapping("/delFromCacheManager")
	public void delFromCacheManager(String id) {
		Cache cache = cacheManager.getCache("people");
		cache.evict(id);
	}
	
	
	
	@PostMapping("/add")
	public String put(@RequestBody Person person) {
		Person p = personService.save(person);
		
		return p.getId();
	}

	@GetMapping("/get")
	public Person cacheable(Person person) {
		System.out.println(System.getProperty("java.io.tmpdir"));
		System.out.println(cacheManager.toString());
		return personService.findOne(person);
	}

	@GetMapping("/delete")
	public String evit(String id) {
		personService.remove(id);
		return "ok";
	}
	
	
}
