package com.kiiik.web.example.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年12月13日
 */
public class TaskFactory {
	Log log = LogFactory.getLog(TaskFactory.class);
	public static List<TaskInfor> tasks = new ArrayList<>();
	static {
		tasks.add(new TaskInfor("1", "5秒一次", "*/5 * * * * ?"));
		tasks.add(new TaskInfor("2", "每分钟一次", "0 * * * * ?"));
		tasks.add(new TaskInfor("3", "每小时一次", "0 0 * * * ?"));
	}

	public static void add(TaskInfor task) {
		tasks.add(task);
	}

	public static Map<String, TaskInfor> map() {
		Map<String, TaskInfor> map = new HashMap<>();
		for (TaskInfor t : tasks) {
			map.put(t.getId(), t);
		}
		return map;
	}
}
