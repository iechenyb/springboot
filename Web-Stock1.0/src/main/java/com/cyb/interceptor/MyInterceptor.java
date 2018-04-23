package com.cyb.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月28日
 */
public class MyInterceptor implements HandlerInterceptor {
	Log logger = LogFactory.getLog(MyInterceptor.class);
	 /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("preHandle "+request.getRequestURL());
       /* if(request.getSession().getAttribute("user")!=null){
			UserContext.setUser((ValidBean)request.getSession().getAttribute("user"));
			System.out.println("进入前置处理器 name="+UserContext.getUserBean().getName()+" age="+UserContext.getUserBean().getAge());
		}*/
    	//System.out.println("进入前置处理器 name="+UserContext.getUserBean().getName()+" age="+UserContext.getUserBean().getAge());
        //获取session
        /* HttpSession session = request.getSession(true);
        //判断用户ID是否存在，不存在就跳转到登录界面
        if(session.getAttribute("userId") == null){
            logger.info("------:跳转到login页面！");
            response.sendRedirect(request.getContextPath()+"/phone/plan/login.jsp");
            return false;
        }else{
            //session.setAttribute("userId", session.getAttribute("userId"));
            return true;
        }*/
        return true;
    }
    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    		logger.info("postHandle "+request.getRequestURL());
    }
    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	logger.info("afterCompletion "+request.getRequestURL());
    }

}