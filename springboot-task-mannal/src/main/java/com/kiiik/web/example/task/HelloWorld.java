package com.kiiik.web.example.task;
import org.quartz.Job;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  
  
public class HelloWorld implements Job{  
  
    @Override  
    public void execute(JobExecutionContext context)  
            throws JobExecutionException {  
        System.err.println("Hello World....");  
    }  
  
}  