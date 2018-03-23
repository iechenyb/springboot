package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cyb.MyBootStarter;

/**
 * 作者 : iechenyb<br>
 * 类描述: 需要单独打开h2服务器才能访问<br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyBootStarter.class)
@ActiveProfiles("prod")
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:application-tansaction.xml", "classpath:application-bean.xml" })
public class MvcControllerTest {
	Log logger = LogFactory.getLog(getClass());

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void init() {
		System.out.println("init...");
		 //mvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	// 创建对象更新
	@Test
	public void getTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/common/name")
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .param("p1", "123.123")
	                .param("p2", "456.456")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(MockMvcResultMatchers.content()
	                		.string(Matchers.containsString("cyb")));
	}
}
