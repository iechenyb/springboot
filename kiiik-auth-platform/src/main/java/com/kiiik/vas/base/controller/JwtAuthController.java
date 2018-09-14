package com.kiiik.vas.base.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.config.security.User;
import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.vas.base.service.impl.AuthService;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月12日下午3:43:00
 */
@RestController
public class JwtAuthController {
	Log log = LogFactory.getLog(JwtAuthController.class);
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResultBean<String> createAuthenticationToken(
            String username,String password
    ) throws AuthenticationException{
        //  @RequestBody JwtAuthenticationRequest authenticationRequest
    	  SecurityContextHolder.clearContext();
        final String token = authService.login(username,password);
        // Return the token
        return new ResultBean<String>(token);
    }

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public ResultBean<String> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return new ResultBean<String>("");
        } else {
            return new ResultBean<String>(token);
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }	
}
