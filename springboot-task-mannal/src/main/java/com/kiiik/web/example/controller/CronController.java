package com.kiiik.web.example.controller;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月13日
 */
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.web.example.task1.SftpTask;
import com.kiiik.web.example.task1.TaskFactory;
import com.kiiik.web.example.task1.TaskInfor;
@RestController
@RequestMapping("cron/task")
public class CronController {
	Log log = LogFactory.getLog(CronController.class);
	
	@Autowired
	SftpTask job;
	
	@GetMapping("startAllTask")
	public ResultBean<Map<String,TaskInfor>> startAllTask(){
		for(TaskInfor t:TaskFactory.tasks){
			if(job.exist(t)){
				System.err.println("存在相同的正在任务！"+t);
			}else{
				job.addJob(t);
			}
		}
		return new ResultBean<Map<String,TaskInfor>>(TaskFactory.map());
	}
	
	@GetMapping("stopAllTask")
	public ResultBean<Map<String,TaskInfor>> stopAllTask(){
		for(TaskInfor t:TaskFactory.tasks){
			if(!job.exist(t)){
				System.err.println("不存在任务！"+t);
			}else{
				job.removeJob(t);
			}
		}
		return new ResultBean<Map<String,TaskInfor>>(TaskFactory.map());
	}
	
	@GetMapping("list")
	public ResultBean<Map<String,TaskInfor>> listTask(){
		return new ResultBean<Map<String,TaskInfor>>(TaskFactory.map());
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("add")
	public ResultBean<String> addTask(TaskInfor task){
		if(TaskFactory.map().containsKey(task.getId())){
			return new ResultBean<>().fail("存在相公的任务！");
		}else{
			TaskFactory.add(task);
			job.addJob(task);
		}
		return new ResultBean<>("添加成功！");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("upd")
	public ResultBean<String> updTask(TaskInfor task){
		TaskInfor cur = TaskFactory.map().get(task.getId());
		if(task.getCron().equals(cur.getCron())){
			return new ResultBean<>().fail("任务定时策略没有发生变化！");
		}
		job.removeJob(task);
		job.addJob(task);
		//job.modifyJob(task);
		return new ResultBean<>("更新成功！");
	}
	
	@GetMapping("stop")
	public ResultBean<String> delTask(String id){
		job.removeJob(TaskFactory.map().get(id));
		return new ResultBean<>("任务移除成功！");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("start")
	public ResultBean<String> startTask(String id){
		if(job.exist(TaskFactory.map().get(id))){
			return new ResultBean<String>().fail("任务已经存在！");
		}
		job.addJob(TaskFactory.map().get(id));
		return new ResultBean<String>("任务启动成功！");
	}
}
