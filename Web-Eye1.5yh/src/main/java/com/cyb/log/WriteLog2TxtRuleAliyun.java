package com.cyb.log;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月5日
 */
@Component
@Profile("aliyun")
//@ActiveProfiles("aliyun") //配合profile使用
public class WriteLog2TxtRuleAliyun extends AbstractLogRule  implements LogRule{

}
