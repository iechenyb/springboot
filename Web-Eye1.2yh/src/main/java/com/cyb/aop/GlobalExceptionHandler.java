package com.cyb.aop;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.exception.CustomException;
/**
 *作者 : iechenyb<br>
 *http://blog.csdn.net/king_is_everyone/article/details/53080851
 *类描述: 通过使用@ControllerAdvice来进行统一异常处理，@ExceptionHandler(value = Exception.class)来指定捕获的异常 
下面针对两种异常进行了特殊处理分别返回页面和json数据，使用这种方式有个局限，
无法根据不同的头部返回不同的数据格式，而且无法针对404、403等多种状态进行处理<br>
 *创建时间: 2018年1月23日
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "exception/error";
    
   /* @SuppressWarnings("rawtypes")
	@ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public ResponseEntity defaultErrorHandler(HttpServletRequest req, CustomException e) throws Exception {
        return ResponseEntity.ok("ok");
    }
    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }*/
}
