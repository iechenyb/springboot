package com.cyb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**http://blog.csdn.net/king_is_everyone/article/details/53080851
 * 作者 : iechenyb<br>
 * 类描述: 说点啥http://blog.csdn.net/zhaozhirongfree1111/article/details/72526885<br>
 * 创建时间: 2017年12月28日
 */
//@Controller
//@ControllerAdvice war包使用
//@RequestMapping(value = "error")
public class BaseErrorController  implements ErrorController{
	//Log log = LogFactory.getLog(BaseErrorController.class);
	Logger log = LoggerFactory.getLogger(getClass());
	private static final String PATH = "/exception/error";
   // @Autowired
   // private ErrorAttributes errorAttributes;

//    @RequestMapping(value = PATH)
    @RequestMapping(value = PATH,  produces = {MediaType.APPLICATION_JSON_VALUE})
    String error(HttpServletRequest request, HttpServletResponse response) {
        /*if(!EnvironmentUtils.isProduction()) {
            return buildBody(request,true);
        }else{
            return buildBody(request,false);
        }*/
    	return getErrorPath();
    }

    /*private StatefulBody buildBody(HttpServletRequest request,Boolean includeStackTrace){
        Map errorAttributes = getErrorAttributes(request, includeStackTrace);
        Integer status=(Integer)errorAttributes.get("status");
        String path=(String)errorAttributes.get("path");
        String messageFound=(String)errorAttributes.get("message");
        String message="";
        String trace ="";
        if(!StringUtils.isEmpty(path)){
            message=String.format("Requested path %s with result %s",path,messageFound);
        }
        if(includeStackTrace) {
             trace = (String) errorAttributes.get("trace");
             if(!StringUtils.isEmpty(trace)) {
                 message += String.format(" and trace %s", trace);
             }
        }
        return FailureResponseBody.builder().code(0).status(status).message(message).build();
    }
*/
    @Override
    public String getErrorPath() {
        return PATH;
    }
   /* private Map getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }*/
}
	//Map<String,Object> data,HttpServletRequest req,HttpServletResponse res
	/*@Override
	public String getErrorPath() {
		//log.info("出错啦！进入自定义错误控制器");
		return "exception/error";
	}

	@RequestMapping
	public ModelAndView error(Map data,HttpServletRequest req,HttpServletResponse res) {
	    ModelAndView m = new ModelAndView();
        //m.addObject("roncooException", exception.getMessage());
        m.setViewName("exception/"+res.getStatus());
		return m;
	}
	*//**
     * 统一异常处理
     * 
     * @param exception
     *            exception
     * @return
     *//*
    @ExceptionHandler({ RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView processException500(RuntimeException exception,HttpServletResponse res) {
        log.info("自定义异常处理-RuntimeException"+res.getStatus());
        ModelAndView m = new ModelAndView();
        m.addObject("roncooException", exception.getMessage());
        m.setViewName("exception/500");
        return m;
    }

    *//**
     * 统一异常处理
     * 
     * @param exception
     *            exception
     * @return
     *//*
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView processException404(RuntimeException exception) {
        log.info("自定义异常处理-Exception");
        ModelAndView m = new ModelAndView();
        m.addObject("roncooException", exception.getMessage());
        m.setViewName("exception/404");
        return m;
    }
    
    *//**
     * 统一异常处理
     * 
     * @param exception
     *            exception
     * @return
     *//*
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView processException403(RuntimeException exception) {
        log.info("自定义异常处理-Exception");
        ModelAndView m = new ModelAndView();
        m.addObject("roncooException", exception.getMessage());
        m.setViewName("exception/404");
        return m;
    }*/
