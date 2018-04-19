package com.cyb.validate.bean;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月19日
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
	Log log = LogFactory.getLog(IsMobileValidator.class);
    private boolean required = false;
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.info("校验值"+value);
		if(required){
			//编写校验逻辑
			return true;
		}else{
			if(StringUtils.isEmpty(value)){
				return true;
			}
		}
		return false;
	}


}
