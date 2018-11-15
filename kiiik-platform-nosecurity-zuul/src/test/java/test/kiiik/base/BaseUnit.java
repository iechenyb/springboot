package test.kiiik.base;
import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月28日下午2:15:41
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ZuulAuthServiceStarter.class)
@ActiveProfiles("dev")
@WebAppConfiguration
@ContextConfiguration(locations = { })
@AutoConfigureRestDocs(outputDir = "target/snippets")*/
public class BaseUnit {
	public final String SKIP = "1";//是否跳过请求参数测试
	public final boolean showRQ=true;//是否显示请求参数
	@Autowired
	public WebApplicationContext context;

	/*public MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}*/
	
	public File getFile(String filePath) throws FileNotFoundException{
		return ResourceUtils.getFile("classpath:tests/"+filePath);
	}
	
	
	public void executeRq(String fileUri,String f) throws Exception {/*
		Document doc = Jsoup.parse(getFile(fileUri), "utf-8");
		Elements rqs = doc.select("rq");
		for(int i=0;i<rqs.size();i++){//读取市场信息
		    if(!SKIP.equals(rqs.get(i).attr("skip"))){
		    	if(showRQ){
		    		System.out.println("请求参数："+rqs.get(i).text());
		    	}
		    	MvcResult rs = 
		    	String responseString = mockMvc.perform(
						MockMvcRequestBuilders.post(rqs.get(i).attr("uri"))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(rqs.get(i).text()))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(jsonPath("$.ec", is(BaseResult.SUCCESS)))
						.andExpect(jsonPath("$.f", is(f)))
		    			.andExpect(jsonPath("$.d",notNullValue()))
		    			.andDo(print())
		    			.andReturn()
		    			.getResponse()
		    			.getContentAsString();
		    			//.andReturn();
		    	System.out.println("sever return data is " + responseString);
		    }
		}
	*/}
	
}
