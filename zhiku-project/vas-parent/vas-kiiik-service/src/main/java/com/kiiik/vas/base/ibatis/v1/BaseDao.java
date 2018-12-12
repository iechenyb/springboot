package com.kiiik.vas.base.ibatis.v1;

import java.util.HashMap;

/**
 *作者 : iechenyb<br>
 *类描述: 通用接口集合<br>
 *创建时间: 2018年9月10日
 */
public interface BaseDao<T> {
	public int insert(T obj);

    public HashMap<?, ?> query(long id, Class<?> c);

    public int update(T obj);

    public int delete(T obj);
}
