package com.kiiik.utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 基础类型的包装类型校验逻辑<br>
 *创建时间: 2018年5月14日
 */
public class ValidateUtils {
	Log log = LogFactory.getLog(ValidateUtils.class);
	public static  ValidateRestult String(Object val){
		if(StringUtils.isEmpty(val)){
			return ValidateRestult.getInstance().fail().msg("不能为空");
		}
		return null;
		
	}
}
