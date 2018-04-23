package com.cyb.aop;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.cyb.context.TimeContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月23日
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {
		System.out
				.println("object=" + o + ", class=" + o.getClass() + " threadname" + Thread.currentThread().getName());
		Object a = methodParameter.getMethodAnnotation(ResponseBody.class);
		System.out.println("是否是json请求：" + a);
		// 增加内容不影响解析
		if (a == null) {
			return o;//非json请求放行
		} else {
			return customerReturnObject(o);
		}
	}
	
	public  Object customerReturnObject(Object o){
		if (o.getClass().getSimpleName().contains("List")) {
			JSONArray object = JSONArray.fromObject(o);
			object.add("执行时间:"+TimeContext.getTime());
			return object;
		}else	 if (o.getClass().getSimpleName().contains("Map")) {// 集合对象直接添加值  
			@SuppressWarnings("unchecked")
			Map<Object, Object> ret = (Map<Object, Object>) o;
			ret.put("new", "value");
			JSONObject object = JSONObject.fromObject(ret);
			object.put("time", TimeContext.getTime());
			return object;
		} else if (o.getClass().getSimpleName().contains("String")) {// 处理String
			String json = "{\"data\":\"" + o + "\",\"new\":\"abc\"}";
			JSONObject object = JSONObject.fromObject(json);
			System.out.println("json data:" + object);
			object.put("time", TimeContext.getTime());
			return object.toString();// 直接返回object则报错！
		} else if(o.getClass().getSimpleName().contains("ResultBean")){
			//自定义对象上新增一个参数，也可以，不需要在其他bean上定义一个time字段
			@SuppressWarnings("unchecked")
			ResultBean<Object> ret = (ResultBean<Object>) o;
			if(ret.getData()==null){
				ret.setData("");
			}
			JSONObject object = JSONObject.fromObject(o);
			object.put("time", TimeContext.getTime());
			return object;
		} else {
			return o;
		}
	}
}