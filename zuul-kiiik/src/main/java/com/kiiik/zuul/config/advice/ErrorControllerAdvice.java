package com.kiiik.zuul.config.advice;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.zuul.pub.bean.ResultBean;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月15日
 */
@Controller
public class ErrorControllerAdvice implements ErrorController {
	Log log = LogFactory.getLog(ErrorControllerAdvice.class);
	 private final static String ERROR_PATH = "/error";
	 
	    /**
	     * Supports the HTML Error View
	     *
	     * @param request
	     * @return
	     */
	    @RequestMapping(value = ERROR_PATH, produces = "text/html")
	    // @ResponseBody
	    public ModelAndView errorHtml(HttpServletRequest request,HttpServletResponse response,Exception e) {
	    	 /*if(!isProduction) {
	             return JSON.toJSONString(buildBody(request,response,true)).toString();
	         }else{
	             return JSON.toJSONString(buildBody(request,response,false)).toString();
	         }*/
	    	ResultBean<String> rs = buildBody(request,response,true);
	    	ModelAndView view = new ModelAndView();
	    	view.addObject("msg", rs.getMsg());
	    	if(200!=response.getStatus()){
	    		view.setViewName("/"+response.getStatus());
	    	}
			return view;//本应用可以用，但是子系统不能用
	    }
	 
	    boolean isProduction = false;
	    /**
	     * Supports other formats like JSON, XML
	     *
	     * @param request
	     * @return
	     */
	    @RequestMapping(value = ERROR_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
	    @ResponseBody
	    public Object error(HttpServletRequest request ,HttpServletResponse response,Exception e) {
	    	 if(!isProduction) {
	             return buildBody(request,response,true);
	         }else{
	             return buildBody(request,response,false);
	         }
	    }
	 
	    /**
	     * Returns the path of the error page.
	     *
	     * @return the error path
	     */
	    public String getErrorPath() {
	        return ERROR_PATH;
	    }
	   /* @Autowired
	    private ErrorAttributes errorAttributes;*/
	    public String getErrType(int code){
	    	 if(code==500){
		        	return "["+code+"] 服务器故障！";
		        }else if(code==404){
		        	return "["+code+"] 请求尚未开发！";
		        }else if(code==403){
		        	return "["+code+"] 请求被拒绝！";
		        }else{
		        	return "异常代码 ["+code+"]!";
		        }
	    }
		@SuppressWarnings("unchecked")
		private ResultBean<String> buildBody(HttpServletRequest request,HttpServletResponse response,Boolean includeStackTrace){
	        String message=getErrType(response.getStatus());
	        return new ResultBean<String>().fail(message);
	    }
	   /* private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
	        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
	        errorAttributes.getErrorAttributes(null, includeStackTrace);
	        
	        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	    }*/
}
