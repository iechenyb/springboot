package com.kiiik.pub.contant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.kiiik.pub.bean.SessionUser;

public class KiiikContants {
	public static final String PROD = "prod";
	public static final String TEST = "test";
	public static final String DEV = "dev";
	public static final SessionUser DEFALUTUSER = new SessionUser("缺省值", "default", "999999");
	public static final int MONTH = 0;
	public static final int SEASON = 1;
	public static final int HALFYEAR = 2;
	public static final int YEAR = 3;
	public static final int CIRCLE_NOT_FIXED=0;
	public static final Integer MARKET_QUTOES_MID = 2;
	public static final Integer TASK_DOING = 1;
	public static final Integer TASK_DONE_SUCCESS = 2;
	public static final Integer TASK_DONE_FAILED = 3;
	public static final ExecutorService KHFL_WORK = Executors.newFixedThreadPool(10);
	public static final Semaphore KHFL_SEMA_NOT_FIX = new Semaphore(1);
	public static final Semaphore KHFL_SEMA_FIX = new Semaphore(1);
	public static final Integer TASK_OFF = 4;
}
