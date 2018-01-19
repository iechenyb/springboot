package com.cyb.config;
import java.util.Collection;
import java.util.Iterator;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年1月12日
 */
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Created by PrimaryKey on 17/2/4.
 *
 * 最后一个类
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
	//如何当前url没有对应的角色配置，则不进入当前的decide方法，如何处理！
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // TODO  权限 .... >>>
    	System.out.println("进入MyAccessDecisionManager...");
        if (configAttributes == null) {
        	//为空  直接放行，人为是可以访问的资源！
            return;
        }
        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
             //用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                 if (needPermission.equals(ga.getAuthority())) {
                	 System.out.println("needPermission-->"+needPermission+"------》ga.getAuthority()值=" + ga.getAuthority() + "," + "当前类=MyAccessDecisionManager.decide()");
                    return;
                }
            }
        }//无权限，结束！
        throw new AccessDeniedException("没有权限访问！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
