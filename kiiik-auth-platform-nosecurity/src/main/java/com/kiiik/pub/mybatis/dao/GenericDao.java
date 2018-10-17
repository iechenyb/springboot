package com.kiiik.pub.mybatis.dao;

import java.util.List;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月11日
 */
public interface GenericDao {
	public int insertDBEntity(Object Object);

	public int insertDBEntityBatch(List<Object> Object);

	public int updateDBEntityByKey(Object Object);

	public int deleteDBEntityByKey(Object Object);
	
	public int deleteDBEntity(Object Object);//根据所有的属性值删除记录
}
