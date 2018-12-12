package com.kiiik.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.kiiik.pub.exception.KiiikException;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年5月15日
 */
public class ParamterUtils {
	Log log = LogFactory.getLog(ParamterUtils.class);

	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 检查是否有异常信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param bindingResult
	 */
	public static void checkObjectParams(BindingResult bindingResult) {
		List<ObjectError> errorList = bindingResult.getAllErrors();
		if (bindingResult.hasErrors()) {
			throw new KiiikException(
					"非法参数：" + errorList.get(0).getObjectName() + "" + errorList.get(0).getDefaultMessage());
		}
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取所有的异常信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param bindingResult
	 */
	public static List<String> getAllExceptionInforFromObjectParams(BindingResult bindingResult) {
		List<String> errInfors = new ArrayList<String>();
		List<ObjectError> errorList = bindingResult.getAllErrors();
        if (bindingResult.hasErrors()) {
           for (int i = 0; i < errorList.size(); i++) {
            	String str = errorList.get(0).getObjectName()+"#"+errorList.get(0).getCode();
                System.out.println("校验字段信息 "+str+"#"+errorList.get(i).getDefaultMessage());
            }
           errInfors.add(errorList.get(0).getObjectName()+errorList.get(0).getDefaultMessage());
        }  
        return errInfors;
	}
	
}
