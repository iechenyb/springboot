package com.cyb.config;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
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
    public Map<String,String> roleResources(){
    	Map<String,String> auths = new HashMap<String,String>();
    	//管理员权限
    	auths.put("/users/signup", "ROLE_ADMIN");
    	auths.put("/users/register", "ROLE_ADMIN");
    	auths.put("/users/tick", "ROLE_ADMIN");
    	//普通用户权限
    	auths.put("/plan/getPlan", "ROLE_USER");
    	auths.put("/plan/index", "ROLE_USER");
    	return auths;
    }
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 优化，角色对象只创建一个<br>
     *创建时间: 2017年7月15日hj12
     *@return
     */
    public Map<String,ConfigAttribute> roleResources2(){
    	String[] roles = new String[]{"ROLE_ADMIN","ROLE_USER"};
    	Map<String,ConfigAttribute> auths = new HashMap<String,ConfigAttribute>();
    	for(int i=0;i<roles.length;i++){
    		ConfigAttribute configAttribute = new SecurityConfig(roles[i]);// 资源标识
    		auths.put(roles[i], configAttribute);
    	}
    	return auths;
    }
    /**
     * 加载资源，初始化资源变量
     */
    @PostConstruct
    public void loadResourceDefine() {
    	System.out.println("MyInvocationSecurityMetadataSource.loadResourceDefine");
        if (resourceMap == null) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            Map<String,String> auths = roleResources();
            /*for(String url:auths.keySet()){
            	  Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                  ConfigAttribute configAttribute = new SecurityConfig(auths.get(url));// 资源标识
                  configAttributes.add(configAttribute);//创建个数为uri的个数，浪费内存
                  resourceMap.put(url, configAttributes);
            }*/
            Map<String,ConfigAttribute> auths2 = roleResources2();
            for(String url:auths.keySet()){
          	  	Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                configAttributes.add(auths2.get(auths.get(url)));//只用一个地址
                resourceMap.put(url, configAttributes);
          }
            /*List<WebResource> resources = webResourceDao.findAll();
            for (WebResource resource : resources) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = new SecurityConfig(resource.getValue());// 资源标识
                configAttributes.add(configAttribute);
                resourceMap.put(resource.getUrl(), configAttributes);
            }*/
        }
        LOG.info("security info load success!!");
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null) loadResourceDefine();
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String uri = ((FilterInvocation) object).getRequest().getRequestURI();
        if(requestUrl.contains("?")){
        	requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("?"));//去掉参数部分！
        }
        System.out.println("当前访问的uri="+uri);
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

