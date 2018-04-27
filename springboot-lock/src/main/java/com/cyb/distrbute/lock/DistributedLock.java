package com.cyb.distrbute.lock;

import java.util.concurrent.TimeUnit;

public interface DistributedLock {

	/**
	 * ���Ի�ȡ��,�����еȴ�õ�����true,
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean tryLock() throws Exception;

	/**
	 * ����ȴ��ȡ��
	 * 
	 * @throws Exception
	 */
	public void lock() throws Exception;

	/**
	 * �ڹ涨ʱ���ڵȴ��ȡ��
	 * 
	 * @param time
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	public boolean lock(long time, TimeUnit unit) throws Exception;

	/**
	 * �ͷ���
	 * 
	 * @throws Exception
	 */
	public void unLock() throws Exception;

}
