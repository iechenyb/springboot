package com.kiiik.pub.mybatis.dao;

import java.util.List;

import com.github.pagehelper.Page;
import com.kiiik.pub.mybatis.bean.ComplexCondition;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月11日
 */
public interface GenericDao {
	public int insertDBEntity(Object Object);

	public int updateDBEntityByKey(Object Object);

	public int deleteDBEntityByKey(Object Object);
	
	public int deleteDBEntity(Object Object);//根据所有的属性值删除记录
	
	public int deleteDBEntityByKeyBatchs(Object entityInfo,List<Integer> ids);
	
	//根据日期删除记录 add by qinxiang in 2019-11-15
	public int deleteDBEntityByDate(Object entityInfo,Object start,Object end);
	
	//全表删除记录 add by qinxiang in 2019-11-15
    public int deleteFullDBEntity(Object entityInfo);
	
	int insertDBEntityBatch(List<Object> entitys);
	
	
	<T> int insertDBEntityBatchT(List<T> entitys);

	
	
	/****************查询接口*************************************/
	
	public <T> T queryDBEntitySingle(T entity);
	public <T> List<T> queryDBEntityList(T entity);
	public <T> List<T> queryDBEntityList(T entity,String... orderBys);
	public <T> Page<T> queryDBEntityList(T entity,int pageNum,int pageSize,String... orderBys);
	
	public <T> T queryDBEntitySingleComplex(Class<T> clazz,	ComplexCondition condition);
	public <T> List<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition);
	public <T> Page<T> queryDBEntityListComplexTop(Class<T> clazz,	ComplexCondition condition, int top,String orderBys);
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, int pageNum, int pageSize);
	public <T> Page<T> queryDBEntityListComplex(Class<T> clazz,	ComplexCondition condition, int pageNum, int pageSize,String... orderBys);
}
