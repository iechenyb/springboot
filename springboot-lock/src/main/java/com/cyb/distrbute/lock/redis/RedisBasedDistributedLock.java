package com.cyb.distrbute.lock.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import redis.clients.jedis.Jedis;

/**
 * ����Redis��SETNX����ʵ�ֵķֲ�ʽ��
 * 
 * ��ȡ��ʱ�����lock(long time, TimeUnit unit), ������·��������߳�һֱ����
 */
public class RedisBasedDistributedLock extends AbstractLock {

	private Jedis jedis;

	// �������
	protected String lockKey;

	// �����Чʱ��(����)
	protected long lockExpires;

	public RedisBasedDistributedLock(Jedis jedis, String lockKey, long lockExpires) {
		this.jedis = jedis;
		this.lockKey = lockKey;
		this.lockExpires = lockExpires;
	}

	// ����ʽ��ȡ���ʵ��
	protected boolean lock(boolean useTimeout, long time, TimeUnit unit, boolean interrupt) throws InterruptedException {
		System.out.println("test1");
		if (interrupt) {
			checkInterruption();
		}

		System.out.println("test2");
		long start = System.currentTimeMillis();
		long timeout = unit.toMillis(time); // if !useTimeout, then it's useless

		while (useTimeout ? isTimeout(start, timeout) : true) {
			System.out.println("test3");
			if (interrupt) {
				checkInterruption();
			}

			long lockExpireTime = System.currentTimeMillis() + lockExpires + 1;// ��ʱʱ��
			String stringOfLockExpireTime = String.valueOf(lockExpireTime);

			System.out.println("test4");
			if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { // ��ȡ����
				System.out.println("test5");
				//�ɹ���ȡ����, ������ر�ʶ
				locked = true;
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}

			System.out.println("test6");
			String value = jedis.get(lockKey);
			if (value != null && isTimeExpired(value)) { // lock is expired
				System.out.println("test7");
				// �������߳�(�ǵ�jvm)ͬʱ�ߵ�����
				String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime); //ԭ�Ӳ���
				// �����ߵ�����ʱÿ���߳��õ���oldValue�϶�������һ��(��Ϊgetset��ԭ���Ե�)
				// �����õ���oldValue��Ȼ��expired�ģ���ô��˵���õ�����
				System.out.println("test8");
				if (oldValue != null && isTimeExpired(oldValue)) {
					System.out.println("test9");
					//�ɹ���ȡ����, ������ر�ʶ
					locked = true;
					setExclusiveOwnerThread(Thread.currentThread());
					return true;
				}
			} else {
				// TODO lock is not expired, enter next loop retrying
			}
		}
		System.out.println("test10");
		return false;
	}

	public boolean tryLock() {
		long lockExpireTime = System.currentTimeMillis() + lockExpires + 1;// ��ʱʱ��
		String stringOfLockExpireTime = String.valueOf(lockExpireTime);

		if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { // ��ȡ����
			// �ɹ���ȡ����, ������ر�ʶ
			locked = true;
			setExclusiveOwnerThread(Thread.currentThread());
			return true;
		}

		String value = jedis.get(lockKey);
		if (value != null && isTimeExpired(value)) { // lock is expired
			// �������߳�(�ǵ�jvm)ͬʱ�ߵ�����
			String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime); //ԭ�Ӳ���
			// �����ߵ�����ʱÿ���߳��õ���oldValue�϶�������һ��(��Ϊgetset��ԭ���Ե�)
			// �����õ���oldValue��Ȼ��expired�ģ���ô��˵���õ�����
			if (oldValue != null && isTimeExpired(oldValue)) {
				//�ɹ���ȡ����, ������ر�ʶ
				locked = true;
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
		} else {
			// TODO lock is not expired, enter next loop retrying
		}

		return false;
	}

	/**
	 * Queries if this lock is held by any thread.
	 * 
	 * @return {@code true} if any thread holds this lock and {@code false}
	 *         otherwise
	 */
	public boolean isLocked() {
		if (locked) {
			return true;
		} else {
			String value = jedis.get(lockKey);
			// TODO ������ʵ���������, ��:��get��������value��, �������value�Ѿ��ǹ��ڵ���,
			// �������˲��, ��һ���ڵ�set��value, ��ʱ���Ǳ�����߳�(�ڵ����), ����������ж�
			// �Ǽ�ⲻ�����������.�����������Ӧ�ò��ᵼ��������������, ��Ϊ���������Ŀ�ı�����
			// ����ͬ������, ��ֻ��һ����״̬�ı���.
			return !isTimeExpired(value);
		}
	}

	@Override
	protected void unlock0() {
		// �ж����Ƿ����
		String value = jedis.get(lockKey);
		if (!isTimeExpired(value)) {
			doUnlock();
		}
	}

	private void checkInterruption() throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
	}

	private boolean isTimeExpired(String value) {
		return Long.parseLong(value) < System.currentTimeMillis();
	}

	private boolean isTimeout(long start, long timeout) {
		return start + timeout > System.currentTimeMillis();
	}

	private void doUnlock() {
		jedis.del(lockKey);
	}

	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}