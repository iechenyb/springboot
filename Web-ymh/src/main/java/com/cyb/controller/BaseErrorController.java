package com.cyb.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥http://blog.csdn.net/zhaozhirongfree1111/article/details/72526885<br>
 * 创建时间: 2017年12月28日
 */
//@Controller
@ControllerAdvice
@RequestMapping(value = "error")
public class BaseErrorController implements ErrorController {
	Log log = LogFactory.getLog(BaseErrorController.class);

	@Override
	public String getErrorPath() {
		log.info("出错啦！进入自定义错误控制器");
		return "exception/error";
	}

	@RequestMapping
	public String error() {
		return getErrorPath();
	}
	/**
     * 统一异常处理
     * 
     * @param exception
     *            exception
     * @return
     */
    @ExceptionHandler({ RuntimeException.class })
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView processException(RuntimeException exception) {
        log.info("自定义异常处理-RuntimeException");
        ModelAndView m = new ModelAndView();
        m.addObject("roncooException", exception.getMessage());
        m.setViewName("exception/500");
        return m;
    }

    /**
     * 统一异常处理
     * 
     * @param exception
     *            exception
     * @return
     */
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView processException(Exception exception) {
        log.info("自定义异常处理-Exception");
        ModelAndView m = new ModelAndView();
        m.addObject("roncooException", exception.getMessage());
        m.setViewName("exception/500");
        return m;
    }
}
