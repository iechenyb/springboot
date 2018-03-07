package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cyb.MyBootStarter;
import com.cyb.date.DateUtil;
import com.cyb.po.UserLoginLog;
import com.cyb.service.LoginLogServiceImpl;

/**
 * 作者 : iechenyb<br>
 * 类描述: 需要单独打开h2服务器才能访问<br>
 * 修复hibernate更新不显示bug
 * spring.jpa.properties.hibernate.current_session_context_class
 * =org.springframework.orm.hibernate4.SpringSessionContext
 * 创建时间: 2017年12月4日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
@ActiveProfiles("prod")
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-tansaction.xml"
		,"classpath:application-bean.xml"})
//@ImportResource("classpath:application-tansaction.xml")
public class UpdateExceptionTest {
	Log logger = LogFactory.getLog(getClass());

	@Autowired
	LoginLogServiceImpl service;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void init() {
		System.out.println("init...");
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void print() {
		/*//调用接口，传入添加的用户参数  
        mockMvc.perform(post("/user/adduser")  
                .contentType(MediaType.APPLICATION_JSON_UTF8)  
                .content(mapper.writeValueAsString(userInfo)))  
        //判断返回值，是否达到预期，测试示例中的返回值的结构如下{"errcode":0,"errmsg":"OK","p2pdata":null}  
        .andExpect(status().isOk())  
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
        //使用jsonPath解析返回值，判断具体的内容  
        .andExpect(jsonPath("$.errcode", is(0)))  
        .andExpect(jsonPath("$.p2pdata", notNullValue()))  
        .andExpect(jsonPath("$.p2pdata.id", not(0)))  
        .andExpect(jsonPath("$.p2pdata.name", is("testuser2")));
		System.out.println("execute...");*/
	}
    //创建对象更新
	@Test
	public void updateTest() throws Exception {
		UserLoginLog my = service.getLog(1L);
		System.out.println("查询id为1的用户名："+my.getUsername());
		UserLoginLog log = new UserLoginLog();
		log.setId(33L);
		log.setCount(100L);
		log.setLoginTime(DateUtil.timeToMilis());
		log.setLastTime(DateUtil.timeToMilis());
		service.updateTest(log);
		System.out.println("*****************");
		//Thread.sleep(10*1000);
	}
}
