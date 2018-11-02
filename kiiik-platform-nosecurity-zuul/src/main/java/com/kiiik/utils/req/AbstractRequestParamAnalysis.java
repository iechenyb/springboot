package com.kiiik.utils.req;

import javax.servlet.http.HttpServletRequest;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月2日
 */
public abstract class AbstractRequestParamAnalysis  implements RequestParamAnalysis{
	public  String putParams(HttpServletRequest request){return "";}
	public  String deleteParams(HttpServletRequest request){ return "";}
	public  String parseParams(HttpServletRequest request){
		String methodUpper = request.getMethod().toUpperCase();
		if("GET".equals(methodUpper)){
			return getParams(request);
		}else if("POST".equals(methodUpper)){
			return postParams(request);
		}else if("PUT".equals(methodUpper)){
			return putParams(request);
		}else if("DELETE".equals(methodUpper)){
			return deleteParams(request);
		}
		return "";
		
	}
}
