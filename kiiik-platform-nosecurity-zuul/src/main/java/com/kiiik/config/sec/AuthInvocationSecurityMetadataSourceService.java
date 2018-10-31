package com.kiiik.config.sec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.web.system.service.MenuServiceImpl;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日上午10:57:41
 */
@Service
public class AuthInvocationSecurityMetadataSourceService  implements
        FilterInvocationSecurityMetadataSource {
	Log log = LogFactory.getLog(AuthInvocationSecurityMetadataSourceService.class);
	
	@Autowired
	MenuServiceImpl menuService;
	
	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
	
	@PostConstruct
	private void loadResourceDefine() {
		resourceMap = menuService.systemRoleMenus();
	}
	
	/**
	 * 查找url对应的角色  如何获取当前用户的权限信息
	 */
	public void loadResourceDefine(String url_) {
		 if (resourceMap == null) {
			 loadResourceDefine();
		 }
	}
    @Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    	if (CollectionUtils.isEmpty(resourceMap)) loadResourceDefine();//加载所有
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		String url = request.getRequestURI();
		url = url.replaceFirst(request.getContextPath(), "");
		log.info("url:"+url+",是否需要授权:"+resourceMap.containsKey(url));
		return resourceMap.get(url);
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	public boolean supports(Class<?> aClass) {
		return true;
	}
}
