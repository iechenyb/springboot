package com.kiiik.web.example.controller;
import java.io.File;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.utils.RSAUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月6日
 */
@Api
@Controller
@RequestMapping("rsa")
public class RSAController {
	private static String RSAKeyStore = "d:/data/rsa/RSAKey.txt";  
	Log log = LogFactory.getLog(RSAController.class);
	static Map<String,String> passwords=null;
	static{
		passwords = new HashMap<>();
		passwords.put("111111", "纯数字");
		passwords.put("abcdef", "纯字母");
		passwords.put("123dfdfg", "字母和数字组合");
		passwords.put("`!@#$%^&*()_+|?></{}[]", "特殊符号 英文");//转义字符\不可以用作密码
		passwords.put("·~！@#￥%……&*（）——+、、】【《》", "特殊符号中文");
	}
	
	@GetMapping("toLogin")
	@ApiOperation("跳转到rsa登录页面")
	public ModelAndView  toRSALogin() throws Exception{
		ModelAndView view = new ModelAndView();
		view.setViewName("/rsa/rsa");
		view.addObject("m", getPublicKey());
		view.addObject("e",RSAUtil.getKeyPair(RSAKeyStore)
    			.getPublic()
    			.toString()
    			.replace("\n", "#")
    			.split("#")[2].trim()
    			.split(":")[1].trim());
		return view;
	}
	
	@GetMapping("refreshRSA")
	@ApiOperation("获取公钥")
	@ResponseBody
	public String  refreshRSA() throws Exception{
		File file = new File(RSAKeyStore);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		RSAUtil.generateKeyPair(RSAKeyStore);
		return "RSA信息刷新成功!";
	}
	
	@GetMapping("getPublicKey")
	@ApiOperation("获取公钥")
	@ResponseBody
	public String  getPublicKey(){
		try {
			return RSAUtil.getKeyPair(RSAKeyStore)
					.getPublic()
					.toString()
					.replace("\n", "#")
					.split("#")[1].trim()
					.split(":")[1].trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@GetMapping("putNewPassword")
	@ApiOperation("新增一个验证密码")
	@ResponseBody
	public Map<String,String>  putNewPassword(String password){
		passwords.put(password, "");
		return passwords;
	}
	
	@GetMapping("listPassword")
	@ResponseBody
	@ApiOperation("查看已有密码列表")
	public Map<String,String>  listPassword(){
		return passwords;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("validatePassword")
	@ApiOperation("密码校验失败")
	public ResultBean<String> validatePasswrod(String password){
		String encodeStr = new String(Base64.decode(password.getBytes()));
    	byte[] en_result = new BigInteger(encodeStr, 16).toByteArray();
    	byte[] de_result;
		try {
			de_result = RSAUtil.decrypt(RSAUtil.getKeyPair(RSAKeyStore).getPrivate(),
					en_result);
			//倒叙输出123456789-> 987654321 先倒置 再解密
			StringBuilder orignPassword = new StringBuilder(new String(de_result)).reverse();
			System.out.println("将请求进行解密:"+orignPassword.toString());
			String realPassword = URLDecoder.decode(orignPassword.toString(),"UTF-8");
	    	System.out.println("将请求进行解密:"+realPassword);
	    	if(passwords.containsKey(realPassword)){
	    		return new ResultBean<String>().success("密码校验成功，密文是"+realPassword);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<String>().fail("密码校验失败！");
		}
		return new ResultBean<String>().fail("密码校验失败.");
		
	}
	
}
