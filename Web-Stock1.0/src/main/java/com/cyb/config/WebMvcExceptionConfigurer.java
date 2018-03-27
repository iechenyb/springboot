package com.cyb.config;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cyb.aop.ResultBean;

import net.sf.json.JSONObject;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月8日
 */
@Configuration
public class WebMvcExceptionConfigurer extends WebMvcConfigurerAdapter {
	Log logger = LogFactory.getLog(WebMvcExceptionConfigurer.class);
    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                ResultBean<String> result = new ResultBean<String>();
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    if (e instanceof NullPointerException) {//业务失败的异常，如“账号或密码错误”
                        result.setCode(0);
                        result.setMsg(e.getMessage());
                        logger.error(e.getMessage());
                    } else {
                        result.setCode(0);
                        result.setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员!"+e.getMessage());
                        String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                        logger.error(message, e);
                    }
                } else {
                    if (e instanceof NoHandlerFoundException) {
                        result.setCode(0);
                        result.setMsg("接口 [" + request.getRequestURI() + "] 不存在");
                    } else {
                        result.setCode(0);
                        result.setMsg(e.getMessage());
                        logger.error(e.getMessage(), e);

                    }
                }
                responseResult(response, result);
                return new ModelAndView();
            }

        });
    }
   private void responseResult(HttpServletResponse response, ResultBean<String> result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSONObject.fromObject(result).toString());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}