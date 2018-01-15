package com.cyb.config;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.catalina.WebResource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

@Service
public class MyInvocationSecurityMetadataSource 
implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());

    /*@Autowired
    private WebResourceDao webResourceDao;*/

    /**
     * 加载资源，初始化资源变量
     */
    @PostConstruct
    public void loadResourceDefine() {
    	
    	System.out.println("MyInvocationSecurityMetadataSource.loadResourceDefine");
        if (resourceMap == null) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            /*List<WebResource> resources = webResourceDao.findAll();
            for (WebResource resource : resources) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = new SecurityConfig(resource.getValue());// 资源标识
                configAttributes.add(configAttribute);
                resourceMap.put(resource.getUrl(), configAttributes);
            }*/
            //toPage3需要ADMIN角色
            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            ConfigAttribute configAttribute = new SecurityConfig("ROLE_ADMIN");// 资源标识
            configAttributes.add(configAttribute);
            resourceMap.put("/common/toPage1", configAttributes);
            
            //toPage3需要USER角色
            Collection<ConfigAttribute> configAttributes2 = new ArrayList<ConfigAttribute>();
            ConfigAttribute configAttribute2 = new SecurityConfig("ROLE_USER");// 资源标识
            configAttributes2.add(configAttribute2);
            resourceMap.put("/common/toPage2", configAttributes2);
            
            
            //toPage3需要other角色
            Collection<ConfigAttribute> configAttributes3 = new ArrayList<ConfigAttribute>();
            ConfigAttribute configAttribute3 = new SecurityConfig("ROLE_OTHER");// 资源标识
            configAttributes3.add(configAttribute3);
            resourceMap.put("/common/toPage3", configAttributes3);
            
            //JSP需要other角色
            Collection<ConfigAttribute> configAttributes4 = new ArrayList<ConfigAttribute>();
            ConfigAttribute configAttribute4 = new SecurityConfig("ROLE_OTHER");// 资源标识
            configAttributes4.add(configAttribute4);
            resourceMap.put("/need/needOtherPremission.jsp", configAttributes4);
            
            //JSP需要other角色
            Collection<ConfigAttribute> configAttributes5 = new ArrayList<ConfigAttribute>();
            ConfigAttribute configAttribute5 = new SecurityConfig("ROLE_ADMIN");// 资源标识
            configAttributes5.add(configAttribute5);
            resourceMap.put("/need/needAdminPremission.jsp", configAttributes5);
        }
        LOG.info("security info load success!!");
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null) loadResourceDefine();
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        System.out.println("当前访问的url="+requestUrl);
       // 返回当前 url  所需要的权限 即url对应的角色
         return resourceMap.get(requestUrl);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

