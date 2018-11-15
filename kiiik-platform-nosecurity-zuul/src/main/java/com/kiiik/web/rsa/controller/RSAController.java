package com.kiiik.web.rsa.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.utils.PasswordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年11月6日
 */
@Api
@Controller
@RequestMapping("rsa")
public class RSAController {
	public static Map<String,String> passwords=null;
	static{
		passwords = new HashMap<>();
		passwords.put("111111", "纯数字");//96E79218965EB72C92A549DD5A330112
		passwords.put("abcdef", "纯字母");//E80B5017098950FC58AAD83C8C14978E
		passwords.put("123dfdfg", "字母和数字组合");//522488100A714638782E066A14A1325A
		passwords.put("`!@#$%^&*()_+|?></{}[]", "特殊符号 英文");//转义字符\不可以用作密码 31C16E9EB76E91A6B9F0BF20DCCF7EBA
		passwords.put("·~！@#￥%……&*（）——+、、】【《》", "特殊符号中文");//10E551C3D87981220A9B9E5DED6DB7D9
		passwords.put("96E79218965EB72C92A549DD5A330112", "111111");
		passwords.put("E80B5017098950FC58AAD83C8C14978E", "abcdef");
		passwords.put("522488100A714638782E066A14A1325A", "123dfdfg");
		passwords.put("31C16E9EB76E91A6B9F0BF20DCCF7EBA", "`!@#$%^&*()_+|?></{}[]");
		passwords.put("10E551C3D87981220A9B9E5DED6DB7D9", "·~！@#￥%……&*（）——+、、】【《》中文");
	}
	@Autowired
	PasswordUtils utils;

	@SuppressWarnings("unchecked")
	@GetMapping("getPublicKey")
	@ResponseBody
	@ApiOperation("获取公钥信息")
	public ResultBean<String> getPubValue() throws Exception {
		return new ResultBean<String>(utils.getPublicKey()).success();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("getPrivateKeys")
	@ResponseBody
	@ApiOperation("获取密钥信息")
	public ResultBean<String> getPriValue() throws Exception {
		return new ResultBean<String>(utils.getPrivateKey()).success();
	}
   
	
	@GetMapping("refreshRSA")
	@ApiOperation("刷新公秘钥信息")
	@ResponseBody
	public String refreshRSA() throws Exception {
		utils.genRSASer();
		return "RSA信息刷新成功!";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("validatePasswordSTD")
	@ApiOperation("密码校验")
	public ResultBean<String> validatePasswrod(String encrypted,String decrypted) throws Exception {
		String orginPassword = utils.decodePassword(encrypted);
		String infor = "解析密码:"+orginPassword+"，原密码:"+decrypted;
		if(orginPassword.equals(delSpace(decrypted))){
			return new ResultBean<String>(infor).success("密码解析成功");
		}else{
			return new ResultBean<String>(infor).fail("密码解析失败");
		}
	}
	
	//使用 Java 正则表达式,去除两边空格。
    public static String delSpace(String str) throws Exception {  
        if (str == null) {  
            return null;  
        }  
        String regStartSpace = "^[　 ]*";  
        String regEndSpace = "[　 ]*$";  
        // 连续两个 replaceAll   
        // 第一个是去掉前端的空格， 第二个是去掉后端的空格   
        String strDelSpace = str.replaceAll(regStartSpace, "").replaceAll(regEndSpace, "");  
        return strDelSpace;  
    }
	/*@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping("validatePasswordSTDJson")
	@ApiOperation("密码校验")
	public ResultBean<String> validatePasswrodJson(@RequestBody String data) throws Exception {
		JSONObject dataObj = (JSONObject) JSON.parse(data);
		JSONArray arr = dataObj.getJSONArray("tests");
		for(int i=0;i<arr.size();i++){
			try{
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("encodePassword", URLEncoder.encode(arr.getJSONObject(i).get("encrypted").toString(),KiiikContants.UTF8));
				params.put("password", arr.getJSONObject(i).get("decrypted"));
				ResultBean<String> rs = validatePasswrod(arr.getJSONObject(i).get("encrypted").toString(),arr.getJSONObject(i).get("decrypted").toString());
			    if(!rs.getEc().equals("0")){
			    	return rs;
			    }
			}catch(Exception e){
				System.out.println("错误序号："+i);
			}
		}
		return new ResultBean<String>().success("密码解析成功");
	}
*/
}
