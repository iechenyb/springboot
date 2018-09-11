package com.kiiik.vas.base.ibatis.v2;

import java.util.HashMap;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月10日
 */
public interface BaseService {
	public int insert(Object obj);

    public HashMap<?, ?> query(long id, Class<?> c);

    public int update(Object obj);

    public int delete(Object obj);
}
