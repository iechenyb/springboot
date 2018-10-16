package com.aiyi.core.aop;

import javax.annotation.Resource;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 内部自动化事物控制器
 * @author 郭胜凯
 * @time 2016年5月11日上午10:02:15
 * @email 719348277@qq.com
 */
@Aspect
public class TransactionManager {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sqlSessionTemplateASS")
	private SqlSessionTemplate sqlSessionTemplateASS;
	
	@Pointcut("@annotation(com.aiyi.core.annotation.po.TraMethod)")
	public void transactionMethod(){}
	
	@Before("transactionMethod()")
	public void openTra(){
		System.out.println("前置^^^事务开启");
	}
	
	@AfterReturning("transactionMethod()")
	public void cmmintTra(){
		System.out.println("前置^^^事务提交");
	}
}
