package com.kiiik.zuul.utils;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.kiiik.zuul.pub.bean.ResultBean;
import com.kiiik.zuul.web.contant.ExceptionCode;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
public class ResponseUtils {
	static Log log = LogFactory.getLog(ResponseUtils.class);
	public static void writeResult(HttpServletResponse response, ResultBean<String> result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return ;
    }
	public static ModelAndView getErrorView(HttpServletRequest request,HttpServletResponse response,Exception e){
		ModelAndView view = new ModelAndView();
    	view.addObject("path", request.getRequestURI());
    	view.addObject("error",e.getMessage() );//e.getMessage()
    	view.addObject("timestamp",new Date().getTime());
    	view.addObject("status",response.getStatus());
    	view.addObject("message", ExceptionCode.ERRORCODE.get(response.getStatus()));
		view.setViewName("error1");
		return view;
	}
	
}
