package com.cyb.app.user.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cyb.app.user.pojo.User;
import com.cyb.app.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends WebMvcConfigurerAdapter {
   //static ApplicationContext ctx = null;
  //@Autowired
  @Resource(name="userService")
  UserService userService;
  @Autowired  
  private UserService userService0; 
  @RequestMapping("/query")
  public String query(String name) {
	//ctx.getBean("userService");
	System.out.println("service="+this.userService+","+this.userService0);
	User user = userService.getUserByName(name);
    return user==null?"no data":user.getUsername();
  }

 /* public static void main(String[] args) {
	//��ȡcontext.  -- Angel -�ػ���ʹ
	  ctx =  (ApplicationContext) SpringApplication.run(UserController.class, args);
	  //��ȡBeanFactory
	  DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)ctx.getAutowireCapableBeanFactory();
	//����bean��Ϣ.
	  BeanDefinitionBuilder beanDefinitionBuilder =BeanDefinitionBuilder.genericBeanDefinition(UserService.class);
	  beanDefinitionBuilder.addPropertyValue("name","����");
	        
	  //��̬ע��bean.
	  defaultListableBeanFactory.registerBeanDefinition("userService",beanDefinitionBuilder.getBeanDefinition());  
	  UserService testService =ctx.getBean(UserService.class);
	  testService.print();
	 //defaultListableBeanFactory.removeBeanDefinition("userService");
	  //SpringApplication.run(UserController.class, args);
  }*/
}
