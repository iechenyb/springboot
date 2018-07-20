package test.kiiik;

	import static org.hamcrest.core.Is.is;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cyb.file.FileUtils;
import com.kiiik.KiiikVasServiceStarter;

/**
 * 作者 : iechenyb<br>
 * 类描述: 需要单独打开h2服务器才能访问<br>
 * 修复hibernate更新不显示bug
 * spring.jpa.properties.hibernate.current_session_context_class
 * =org.springframework.orm.hibernate4.SpringSessionContext 创建时间: 2017年12月4日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KiiikVasServiceStarter.class)
@ActiveProfiles("prod")
@WebAppConfiguration
@ContextConfiguration(locations = { })
// @ImportResource("classpath:application-tansaction.xml")
public class UpdateExceptionTest {
	Log logger = LogFactory.getLog(getClass());


	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void init() {
		System.out.println("init...");
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	// 判断返回值，是否达到预期，测试示例中的返回值的结构如下{"errcode":0,"errmsg":"OK","p2pdata":null}

	/*public void get() throws UnsupportedEncodingException, Exception {
		// 模拟发送请求
		String result = mockMvc
				.perform(MockMvcRequestBuilders.get("/user") // 发往/user的get请求,可以换成post，put，delete方法执行相应请求
						.param("username", "xxx") // get请求时填写参数的位置
						.contentType(MediaType.APPLICATION_JSON_UTF8) // utf编码
						.content("")) // post和put请求填写参数的位置
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(3)) // 期望的json返回结果
				.andReturn().getResponse().getContentAsString(); // 对返回字符串的json内容进行判断
		System.out.println(result);
	}

	@Test
	public void print() throws Exception {
		// 调用接口，传入添加的用户参数
		mockMvc.perform(
				MockMvcRequestBuilders.post("/user/adduser")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(""))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				// 使用jsonPath解析返回值，判断具体的内容
				.andExpect(jsonPath("$.errcode", is(0)))
				.andExpect(jsonPath("$.p2pdata", notNullValue()))
				.andExpect(jsonPath("$.p2pdata.id", not(0)))
				.andExpect(jsonPath("$.p2pdata.name", is("testuser2")));
		System.out.println("execute...");
	}*/
	
	@Test
	public void postStudy() throws Exception {
		String rq = FileUtils.readContentFromFile("d:/data/test/post.txt");
		System.out.println("请求参数"+rq);
		//Predicate ;
		// 调用接口，传入添加的用户参数
		mockMvc.perform(
				MockMvcRequestBuilders.post("/json/rqPost")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(rq))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// 使用jsonPath解析返回值，判断具体的内容
				.andExpect(jsonPath("$.code", is(1)))
				/*.andExpect(jsonPath("$.p2pdata", notNullValue()))
				.andExpect(jsonPath("$.p2pdata.id", not(0)))*/
				.andExpect(jsonPath("$.data.id", is("99")));
		System.out.println("execute...");
	}
}
