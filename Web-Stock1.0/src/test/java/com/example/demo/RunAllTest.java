package com.example.demo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月28日
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CacheTest.class, RedisTest.class})
public class RunAllTest {
	Log log = LogFactory.getLog(RunAllTest.class);
	
}
