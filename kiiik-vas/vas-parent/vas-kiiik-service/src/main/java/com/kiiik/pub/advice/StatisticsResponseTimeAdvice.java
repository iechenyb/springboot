package com.kiiik.pub.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.kiiik.config.SwitchProperties;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.context.TimeContext;

import net.sf.json.JSONObject;



/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年4月23日
 */
@ControllerAdvice
public class StatisticsResponseTimeAdvice implements ResponseBodyAdvice<Object> {
	Log logger = LogFactory.getLog(StatisticsResponseTimeAdvice.class);
	@Autowired
	SwitchProperties switchPro;
	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
	}

	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {
		if (!methodParameter.hasMethodAnnotation(ResponseBody.class)&&
				(methodParameter.getContainingClass().getAnnotation(RestController.class)==null)) {
			return o;
		} else { 
			boolean inspectTime = true;//配置文件设置
			if(inspectTime){
				return customerReturnObject(o) ;//customerReturnObject(o)
			}else{
				return o;
			}
		}
	}
	
	public  Object customerReturnObject(Object o){
		if(o==null){ 
			return o;
		}
		logger.info("进入时间统计方法："+o.getClass().getSimpleName());
		/*if (o.getClass().getSimpleName().equals("List")) {
			JSONArray object = JSONArray.fromObject(o);
			object.add("执行时间:"+TimeContext.getTime());
			return o;//可以新增，但是对前台解析有影响， 元素类型不定
		}else	 if (o.getClass().getSimpleName().equals("Map")) {// 集合对象直接添加值  
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
		} else*/ 
		if(o.getClass().getSimpleName().contains("ResultBean")){
			//自定义对象上新增一个参数，也可以，不需要在其他bean上定义一个time字段
			@SuppressWarnings("unchecked")
			ResultBean<Object> ret = (ResultBean<Object>) o;
			if(ret.getData()==null){
				ret.setData("");
			}
			JSONObject object = JSONObject.fromObject(o);
			if(switchPro.isShowControllerTime()){
				object.put("time", TimeContext.getTime());
			}
			return object;
		} else if(o.getClass().getSimpleName().contains("VO")) {
			ResultBean<Object> ret  = new ResultBean<Object>();
			ret.data(o).success("执行成功");
			JSONObject object = JSONObject.fromObject(ret);
			if(switchPro.isShowControllerTime()){
				object.put("time", TimeContext.getTime());
			}
			return object;
		}else
		{ //如果视图包含vo，则将vo扔到集合里边
			return o;
		}
	}
}