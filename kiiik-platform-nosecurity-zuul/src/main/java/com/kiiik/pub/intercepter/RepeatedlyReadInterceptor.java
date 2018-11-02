package com.kiiik.pub.intercepter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kiiik.pub.wapper.RepeatedlyReadRequestWrapper;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月2日
 */
public class RepeatedlyReadInterceptor extends HandlerInterceptorAdapter {
	Log log = LogFactory.getLog(RepeatedlyReadInterceptor.class);


	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        /**
	         * 对来自后台的请求统一进行日志处理
	         */
	        RepeatedlyReadRequestWrapper requestWrapper;//代理bean，非包裹类
	       /* Collection<Part> parts = request.getParts();//只能获取multipart类请求
	        Iterator<Part> a = parts.iterator();
	        while(a.hasNext()){
	        	byte[] data = FileCopyUtils.copyToByteArray(a.next().getInputStream());
	        	System.out.println(new String(data));
	        }*/
	        if (request instanceof RepeatedlyReadRequestWrapper) {
	            // 签名处理过程 start.... 
	            requestWrapper = (RepeatedlyReadRequestWrapper) request;
	            log.info( getBodyString(requestWrapper));
	            // 签名处理过程 end....
	        }
	        return super.preHandle(request, response, handler);
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        RepeatedlyReadRequestWrapper requestWrapper;
	        if (request instanceof RepeatedlyReadRequestWrapper) {
	            // 测试再次获取Body start.... 
	            requestWrapper = (RepeatedlyReadRequestWrapper) request;
	            log.info( getBodyString(requestWrapper));
	            // 测试再次获取Body end....
	        }
	    }

	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	    }

	    /**
	     * 获取请求Body
	     *
	     * @param request
	     *
	     * @return
	     */
	    public static String getBodyString(final ServletRequest request) {
	        StringBuilder sb = new StringBuilder();
	        InputStream inputStream = null;
	        BufferedReader reader = null;
	        try {
	            inputStream = cloneInputStream(request.getInputStream());
	            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
	            String line = "";
	            while ((line = reader.readLine()) != null) {
	            sb.append(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (inputStream != null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            }
	            if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            }
	        }
	        return sb.toString();
	    }

	    /**
	     * Description: 复制输入流</br>
	     *
	     * @param inputStream
	     *
	     * @return</br>
	     */
	    public static InputStream cloneInputStream(ServletInputStream inputStream) {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len;
	        try {
	            while ((len = inputStream.read(buffer)) > -1) {
	            byteArrayOutputStream.write(buffer, 0, len);
	            }
	            byteArrayOutputStream.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	        return byteArrayInputStream;
	    }
}
