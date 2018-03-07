package com.cyb.log;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cyb.config.SystemConfigSettings;
import com.cyb.date.DateUtil;
import com.cyb.file.FileUtils;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年3月7日
 */
public abstract class AbstractLogRule implements LogRule {
	Log log = LogFactory.getLog(AbstractLogRule.class);
	
	@Autowired
	SystemConfigSettings setting;
	
	@Override
	public void saveSessionLog(String msg) {
		checkFileExist(setting.getSession());
		FileUtils.appendString2File(msg+"\n", setting.getLogPath()+format(setting.getSession()));
	}
	
	@Override
	public void saveExceptionLog(MyLog log) {
		if(StringUtils.isEmpty(log.getUserName())){
			log.setUserName("匿名");
		}
		checkFileExist(setting.getException());
		FileUtils.appendString2File(log.toString()+"\n", setting.getLogPath()+format(setting.getException()));
	}

	@Override
	public void saveVistiorLog(MyLog log) {
		if(StringUtils.isEmpty(log.getUserName())){
			log.setUserName("匿名");
		}
		checkFileExist(setting.getVisitor());
		FileUtils.appendString2File(log.format()+"\n", setting.getLogPath()+format(setting.getVisitor()));
	}
	
	public static String format(String type) {
		 //模式字符串
        String pattern = "ymh-{0}-{1}.log";
       //实例化MessageFormat对象，并装载相应的模式字符串
       MessageFormat format = new MessageFormat(pattern, Locale.CHINA);
       Object arr[] = {type,DateUtil.date2long8().toString()};
        //格式化模式字符串，参数数组中指定占位符相应的替换对象
       String result = format.format(arr);
       return result;
	}
	public static void main(String[] args) {
		format("session");
	}
	public void checkFileExist(String path) {
		File file = new File(path);
		checkFileExist(file);
	}
	
	public void checkFileExist(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
