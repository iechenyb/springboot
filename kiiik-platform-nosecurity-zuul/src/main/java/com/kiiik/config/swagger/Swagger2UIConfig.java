package com.kiiik.config.swagger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年7月9日
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.kiiik.pub.contant.KiiikContants;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
/**
 * Swagger2 UI配置
 * 
 * <pre>
 * 通过访问http://your ip:8090/api/swagger-ui.html查看发布的REST接口;
 * </pre>
 * 
 * @author 许畅
 * @since JDK1.7
 * @version 2017年10月19日 许畅 新建
 */
@Configuration
@PropertySources({ @PropertySource(value = "classpath:swagger2.properties",
ignoreResourceNotFound = true, encoding = "UTF-8") })
@EnableSwagger2
public class Swagger2UIConfig {
	@Autowired
	Environment env;
    
    /**
     * 获取API标题
     */
    @Value("${swagger2.title}")
    public String title = "Spring Boot中使用Swagger2构建Restful API";
    
    /**
     * Swagger2创建Docket的Bean
     * 
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
    	initIngoreControllers();
        return new Docket(DocumentationType.SWAGGER_2)
        	.apiInfo(apiInfo()).select()
            .apis(basePackage("com.kiiik.web"))
            .paths(PathSelectors.any()).build();
    }
    
    /**
     * Predicate that matches RequestHandler with given base package name for the class of the handler method.
     * This predicate includes all request handlers matching the provided basePackage
     *
     * @param basePackage - base package of the classes
     * @return this
     */
    public  Predicate<RequestHandler> basePackage(final String basePackage) {
        return new Predicate<RequestHandler>() {
            
            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
            }
        };
    }
   public  Map<String,String> ignoreController = null;
   public  synchronized void  initIngoreControllers() {
    	ignoreController=new HashMap<>();
    	if(!KiiikContants.DEV.equals(env.getProperty("spring.profiles.active"))){
	    	ignoreController.put("GenericController",KiiikContants.BLANK);
	    	ignoreController.put("JSRController", KiiikContants.BLANK);
	    	ignoreController.put("ApiController", KiiikContants.BLANK);
	    	ignoreController.put("RSAController", KiiikContants.BLANK);
	    	ignoreController.put("VerityCodeController", KiiikContants.BLANK);
    	}
    	
    }
    /**
     * 处理包路径配置规则,支持多路径扫描匹配以逗号隔开
     * 
     * @param basePackage 扫描包路径
     * @return Function
     */
    private  Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
    	
        return new Function<Class<?>, Boolean>() {
            
            @Override
            public Boolean apply(Class<?> input) {
            	//如果生产环境，直接返回false,不生成接口信息
            	if(KiiikContants.PROD.equals(env.getProperty("spring.profiles.active"))){
            		return false;
            	}
                for (String strPackage : basePackage.split(",")) {
                    boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                    if(ignoreController.containsKey(input.getSimpleName())){
                    	return false;
                    }
                    if (isMatch) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
    
   
    
    /**
     * @param input RequestHandler
     * @return Optional
     */
    private  Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
    
    /**
     * Swagger2创建该Api的基本信息
     * 
     * @return ApiInfo
     */
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title)
            .description("更多Swagger2配置相关文章请关注：https://springfox.github.io/springfox/docs/current/")
            .termsOfServiceUrl("https://springfox.github.io/springfox/docs/current/").version("1.0").build();
    }
    
}