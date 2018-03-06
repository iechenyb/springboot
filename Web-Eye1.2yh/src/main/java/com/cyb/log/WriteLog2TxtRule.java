package com.cyb.log;
import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cyb.file.FileUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月5日
 */
@Component
public class WriteLog2TxtRule  implements LogRule{
	Log log = LogFactory.getLog(WriteLog2TxtRule.class);

	@Override
	public void saveExceptionLog(MyLog log) {
		if(StringUtils.isEmpty(log.getUserName())){
			log.setUserName("匿名");
		}
		File file = new File("d:/data/logs/ymh-error.log");
		if(!file.exists()){ try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}}
		FileUtils.appendString2File(log.toString()+"\n", "d:/data/logs/ymh-error.log");
	}

	@Override
	public void saveVistiorLog(MyLog log) {
		if(StringUtils.isEmpty(log.getUserName())){
			log.setUserName("匿名");
		}
		File file = new File("d:/data/logs/ymh-visitor.log");
		if(!file.exists()){ try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}}
		FileUtils.appendString2File(log.format()+"\n", "d:/data/logs/ymh-visitor.log");
	}
}
