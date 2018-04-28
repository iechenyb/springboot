package com.cyb.app.utils;
import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyb.UUIDUtils;
import com.cyb.app.sk.po.User;
import com.cyb.file.FileUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年4月28日
 */
public class UserGenUtils {
	Log log = LogFactory.getLog(UserGenUtils.class);
	static int userNum = 10000;
	static String userFilePath="d:/data/miaosha/users.txt";
	public static void main(String[] args) {
		System.out.println("prepare to gen user ...");
		genUser();
		System.out.println("gen user over ...");
	}
	
	public static void genUser(){
		File file = new File(userFilePath);
		if(file.exists()){
			file.delete();
		}
		FileUtils.genFileDir(file.getParent());//仅仅创建目录
		User user_ = null;
		for(int i=0;i<userNum;i++){
			user_= new User();
			user_.setAddress(String.valueOf(i+userNum));
			user_.setSex(String.valueOf(i%2==0?0:1));
			user_.setUsername(String.valueOf(userNum+i));
			user_.setPassword("123456");
			user_.setToken(UUIDUtils.getUUID());
			FileUtils.appendString2File(user_.getToken()+","+user_.getUsername()+"\n", file.getAbsolutePath());
			user_=null;
			if(i%1000==0){
				System.out.println("生成总数"+(i+1));
			}
		}
	}
}
