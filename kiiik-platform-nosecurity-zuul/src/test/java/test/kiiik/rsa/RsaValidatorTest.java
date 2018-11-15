package test.kiiik.rsa;
import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月28日下午3:26:47
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyb.file.FileUtils;
import com.kiiik.utils.PasswordUtils;
import com.kiiik.utils.RSAUtil;
public class RsaValidatorTest  {
	Log log = LogFactory.getLog(RsaValidatorTest.class);
	PasswordUtils utils= new PasswordUtils();
	String publicKey;
	String privateKey;
	String caseFile = "D:\\data\\rsa\\testcase\\localcase.json";
	// web端生成的密钥对可以是再java服务器端进行加密解密使用
	static String[] passwords = new String[] {"111111", 
			"123456", "abcdef", "123abc","中文测试",
				"!@#$%^&*()_+{}|:<>?", "！@#￥%……&*（）——+|“：？》《" };
	@Before
	public void init() throws Exception{
		 publicKey = utils.getPublicKey();
		 privateKey = utils.getPrivateKey();
	}
	
	@Test
	public void checkReq() throws IOException{
		File file  = new File("d:/data/rsa/log.txt");
		file.delete();
		file.createNewFile();
		String data = FileUtils.readContentFromFile(caseFile);
		JSONObject dataObj = (JSONObject) JSON.parse(data);
		JSONArray arr = dataObj.getJSONArray("tests");
		for(int i=0;i<arr.size();i++){
			System.out.println(arr.getJSONObject(i).get("decrypted").toString()+","+arr.getJSONObject(i).get("encrypted").toString());
			/*try{
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("encodePassword", URLEncoder.encode(arr.getJSONObject(i).get("encrypted").toString(),KiiikContants.UTF8));
				params.put("password", arr.getJSONObject(i).get("decrypted"));
				System.out.println("参数："+params);
				JSONObject ret = HttpsUtils.doGet("http://192.168.16.211/rsa/validatePasswordSTD", params);
				System.out.println(ret);
				FileUtils.appendString2File(params.toString()+"\n"+ret.toString()+"\n", "d:/data/rsa/log.txt");
			}catch(Exception e){
				System.out.println("错误序号："+i);
			}*/
		}
		
	}
	
	
	
	
	public void checkParams() throws Exception {
		pubKeyEncodeBatch();
	}
	
	public  void pubKeyEncodeBatch() throws Exception {
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
		pubKeyEncodeBatchTest(publicKey, privateKey);

	}
	
	public  void pubKeyEncodeBatchTest(String pub, String pri)
			throws IOException {
		for (int i = 0; i < passwords.length; i++) {
			String finalString = RSAUtil.encryptedDataOnJava(passwords[i],publicKey);
			System.out.println("password=[" + passwords[i]+ "]\tencodeString=[" + finalString + "]");
			String res = RSAUtil.decryptDataOnJava(finalString, privateKey);
			System.out.println("解密成功？" + res.equals(passwords[i]) + "-----------");
		}
	}
}
