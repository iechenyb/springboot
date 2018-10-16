package com.aiyi.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.aiyi.core.beans.Po;
import com.aiyi.core.beans.WherePrams;
import com.aiyi.core.util.Formatter;

/**
 * ������ݿ������
 * @author ��ʤ��
 * @time 2016��5��3������2:55:13
 * @email 719348277@qq.com
 * @param <T> ʵ��PO����
 * @param <PK> PO��������
 */
public interface Dao<T extends Po, PK extends Serializable> {

	/**
	 * ��Ӳ�Ϊ�յļ�¼��ֻ����Ϊ���ֶ���⣬Ч�ʸߣ�
	 * @param po
	 * @return �ܸı�ļ�¼��
	 */
	public int addLocal(T po);
	
	/**
	 * ��¼��ӣ������ֶ���⣬Ч���У�
	 * @param po
	 * @return
	 */
	public int add(T po);
	
	/**
	 * ��Ӷ�����¼
	 * @param pos
	 * @return
	 */
//	public int add(List<T> pos);
	
	/**
	 * ͨ�������ȡĳ����¼
	 * @param id ����
	 * @return PO
	 */
	public T get(PK id);
	
	/**
	 * ͨ�������ȡĳ���ֶε�ֵ
	 * @param id
	 * @param fileName
	 * @return
	 */
	public Serializable getField(PK id, String fileName);

	/**
	 * ������ȡһ����¼
	 * @param t
	 * @param �������ʽ
	 * @return PO
	 */
	public T get(WherePrams where);
	
	/**
	 * ������ȡĳ����¼�ֶ�
	 * @param where
	 * @param fileName
	 * @return
	 */
	public Serializable getFile(WherePrams where, String fileName);
	
	/**
	 * ������ѯ�б�
	 * @param where �������ʽ
	 * @return PO�б�
	 */
	public List<T> list(WherePrams where);
	
	/**
	 * ��ѯĳ���ֶ��б�
	 * @param where �������ʽ
	 * @param fileName Ҫ��ѯ���ֶ�
	 * @return
	 */
	public Serializable[] listFile(WherePrams where, String fileName);
	
	/**
	 * ��ѯĳЩ�ֶ�
	 * @param where �������ʽ
	 * @param files Ҫ��ѯ���ֶμ�
	 * @return ��ѯ��PO�ֶ��б�
	 */
	public List<Map<String, Serializable>> listFiles(WherePrams where, String[] files);
	
	/**
	 * ���²�Ϊnull��PO�ֶ�
	 * @param po
	 * @return ��Ӱ�������
	 */
	public int updateLocal(T po);
	
	/**
	 * ����PO�������ֶ�
	 * @param po
	 * @return ��Ӱ�������
	 */
	public int update(T po);
	
	/**
	 * �������²�Ϊnull���ֶ�
	 * @param po
	 * @param �������ʽ
	 * @return ��Ӱ�������
	 */
	public int updateLocal(T po, WherePrams where);
	
	/**
	 * �������������ֶ�
	 * @param po
	 * @param �������ʽ
	 * @return ��Ӱ�������
	 */
	public int update(T po, WherePrams where);
	
	/**
	 * ɾ��ĳ����¼
	 * @param id ����
	 * @return ��Ӱ�������
	 */
	public int del(PK id);
	
	/**
	 * ����ɾ��ĳ����¼
	 * @param where �������ʽ
	 * @return ��Ӱ�������
	 */
	public int del(WherePrams where);
	
	/**
	 * �Զ���sql��ѯ
	 * @param po ���ڷ�װ���ؽ���Bean
	 * @param sql ����ִ�в�ѯ��Sql
	 * @param args Sqlռλ����Ӧ�Ĳ���
	 * @return ����
	 */
	public List<Map<String, Object>> listBySql(String sql);
	
	/**
	 * ִ���Զ���sql
	 * @param sql ����ִ�е�Sql
	 * @param args Sqlռλ����Ӧ�Ĳ���
	 * @return ��Ӱ�������
	 */
	public int excuse(String sql);
	
	/**
	 * ��ȡָ�������ļ�¼��
	 * @param where �������ʽ
	 * @return ��ѯ���ļ�¼��
	 */
	public long count(WherePrams where);
	
	/**
	 * ��ȡ��Ӧ���еļ�¼��
	 * @return ���е�����
	 */
	public long size();
	
	/**
	 * �Ƿ�����ֶ���ͬ�ļ�¼��ID�Լ���Ϊ�յ��ֶγ��⣩
	 * @param po ����ʵ��
	 * @return
	 */
	public boolean isExist(T po);
	
	/**
	 * �Ƿ����ָ�������ļ�¼
	 * @param where �������ʽ
	 * @return
	 */
	public boolean isExist(WherePrams where);
	
	/**
	 * �ڲ�ѯ
	 * @param fileName �����ڲ�ѯ���ֶ�
	 * @param values �ֶε�ֵ
	 * @return ��ѯ���Ľ��
	 */
	public List<T> in(String fileName, Serializable[] values);
	
	/**
	 * �����һ�����е�ֵ
	 * @return
	 */
	public long nextId();
	
	/**
	 * �����ʽ�Ĳ�ѯ
	 * @param where
	 * @param fmt
	 * @return
	 */
	public List<T> listFormat(WherePrams where, Formatter fmt);
}
