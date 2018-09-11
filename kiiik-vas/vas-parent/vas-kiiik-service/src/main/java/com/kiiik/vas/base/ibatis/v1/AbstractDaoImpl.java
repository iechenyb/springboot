package com.kiiik.vas.base.ibatis.v1;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月10日
 */
public class AbstractDaoImpl<E> implements AbstractDao<E> {
	@Resource
	protected SqlSessionTemplate masterSqlSessionTemplate;

	//@Resource
	//protected SqlSessionTemplate slaveSqlSessionTemplate;

	
	public AbstractDaoImpl(){
		System.out.println("AbstractDaoImpl init..."+masterSqlSessionTemplate);
	}
	
	@Override
	public long insert(E e) {
		return masterSqlSessionTemplate.insert(getStatement() + ".insert", e);
	}

	@Override
	public void batchInsert(List<E> data) {
		if (data != null) {
			for (E e : data) {
				insert(e);
			}
		}
	}

	@Override
	public long replace(E e) {
		return masterSqlSessionTemplate.insert(getStatement() + ".replace", e);
	}

	@Override
	public void batchReplace(List<E> data) {
		if (data != null) {
			for (E e : data) {
				replace(e);
			}
		}
	}

	@Override
	public int update(E e, Map<String, Object> conditions) {
		return masterSqlSessionTemplate.update(getStatement() + ".update", conditions);
	}

	@Override
	public void updateByPrimary(E e, long primary) {
		masterSqlSessionTemplate.update(getStatement() + ".updateByPrimary", primary);
	}

	@Override
	public E getOne(Map<String, Object> conditions, int offset, Map<String, Order> orders) {
		List<E> result = select(conditions, offset, 1, orders);
		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public E getOne(Map<String, Object> conditions, int offset) {
		return getOne(conditions, offset, null);
	}

	@Override
	public E getOne(Map<String, Object> conditions, Map<String, Order> orders) {
		return getOne(conditions, 0, orders);
	}

	@Override
	public E getOne(Map<String, Object> conditions) {
		return getOne(conditions, null);
	}

	@Override
	public List<E> select(Map<String, Object> conditions, int offset, int size, Map<String, Order> orders) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset argument value could not be negative integer");
		}

		if (size < 1) {
			throw new IllegalArgumentException("Size argument value must be positive integer");
		}

		if (conditions == null) {
			conditions = new HashMap<String, Object>();
		}

		conditions.put("OFFSET", offset);
		conditions.put("LIMIT", size);

		return masterSqlSessionTemplate.selectList(getStatement() + ".select", conditions);
	}

	@Override
	public List<E> select(Map<String, Object> conditions, int offset, int size) {
		return select(conditions, offset, size, null);
	}

	@Override
	public List<E> select(Map<String, Object> conditions, int size, Map<String, Order> orders) {
		if (size < 1) {
			throw new IllegalArgumentException("Size argument value must be positive integer");
		}

		return select(conditions, 0, size, orders);
	}

	@Override
	public List<E> select(Map<String, Object> conditions, int size) {
		return select(conditions, size, null);
	}

	@Override
	public List<E> select(Map<String, Object> conditions, Map<String, Order> orders) {
		return masterSqlSessionTemplate.selectList(getStatement() + ".select", conditions);
	}

	@Override
	public List<E> select(Map<String, Object> conditions) {
		return select(conditions, null);
	}

	@Override
	public Pagination<E> page(Map<String, Object> conditions, int page, int pagesize, Map<String, Order> orders) {
		if (page < 1) {
			throw new IllegalArgumentException("Page argument value must be positive integer");
		}

		if (pagesize < 1) {
			throw new IllegalArgumentException("Pagesize argument value must be positive integer");
		}

		int totalRecords = count(conditions);

		Pagination<E> pagination = new Pagination<E>(page, pagesize, totalRecords);

		if (pagination.getTotalRecords() > 0) {
			List<E> result = select(conditions, pagination.getOffset(), pagination.getPagesize(), orders);
			pagination.setData(result);
		}

		return pagination;
	}

	@Override
	public Pagination<E> page(Map<String, Object> conditions, int page, int pagesize) {
		return page(conditions, page, pagesize, null);
	}

	@Override
	public List<E> getAll() {
		return masterSqlSessionTemplate.selectList(getStatement() + ".getAll");
	}

	@Override
	public int count(Map<String, Object> conditions) {
		return masterSqlSessionTemplate.selectOne(getStatement() + ".count", conditions);
	}

	@Override
	public int delete(Map<String, Object> conditions) {
		return masterSqlSessionTemplate.delete(getStatement() + ".delete", conditions);
	}

	@Override
	public int deleteByPrimary(long primary) {
		return masterSqlSessionTemplate.delete(getStatement() + ".deleteByPrimary", primary);
	}

	@Override
	public int clear() {
		return masterSqlSessionTemplate.delete(getStatement() + ".clear");
	}

	protected final SqlSessionTemplate getMasterSqlSessionTemplate() {
		return masterSqlSessionTemplate;
	}

	/*protected final SqlSessionTemplate getSlaveSqlSessionTemplate() {
		return slaveSqlSessionTemplate;
	}*/

	@SuppressWarnings("unchecked")
	protected final String getStatement() {
		Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		System.out.println(clazz.getCanonicalName());
		return "com.domain.common.mapper." + clazz.getSimpleName() + "Mapper";
	}

	@Override
	public E getByPrimary(long primary) {
		return null;
	}
}
