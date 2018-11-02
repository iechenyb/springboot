package com.kiiik.pub.filter;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.kiiik.pub.wapper.HttpHelper;
import com.kiiik.pub.wapper.RepeatedlyReadRequestWrapper;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月2日
 */
/*@Configurable*/
public class CorsConfig extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 防止流读取一次后就没有了, 所以需要将流继续写出去，提供后续使用
        ServletRequest requestWrapper = new RepeatedlyReadRequestWrapper(request);
        String json = HttpHelper.getBodyString(requestWrapper);
        System.out.println("json:"+json);
        filterChain.doFilter(requestWrapper, response);
    }
}
