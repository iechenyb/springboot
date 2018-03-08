package com.cyb.log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月5日
 */
@Component
@Profile("dev")
//@Primary
public class WriteLog2TxtRuleDev  extends AbstractLogRule implements LogRule{
	Log log = LogFactory.getLog(WriteLog2TxtRuleDev.class);
	
	/*@Override
	public void saveExceptionLog(MyLog log) {
		if(StringUtils.isEmpty(log.getUserName())){
			log.setUserName("匿名");
		}
		checkFileExist(setting.getException());
		FileUtils.appendString2File(log.toString()+"\n", setting.getException());
	}

	@Override
	public void saveVistiorLog(MyLog log) {
		if(StringUtils.isEmpty(log.getUserName())){
			log.setUserName("匿名");
		}
		checkFileExist(setting.getVisitor());
		FileUtils.appendString2File(log.format()+"\n", setting.getVisitor());
	}*/

}
