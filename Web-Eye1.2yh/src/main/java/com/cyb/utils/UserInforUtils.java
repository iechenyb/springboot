package com.cyb.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cyb.file.FileUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年3月5日
 */
public class UserInforUtils {
	Log log = LogFactory.getLog(UserInforUtils.class);
	public static List<String> readFileToList(String path) {
    	System.out.println("操作文件路径："+path);
    	List<String> content = new ArrayList<String>();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	content.add(tempString.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }
	 public static String getAbsolutePathAtMavenClass(Class<?> clss){
	    	String packagePath = System.getProperty("user.dir");
	    	String packageName = clss.getPackage().getName().replaceAll("\\.", "/");
	    	return packagePath+"/src/main/java/"+packageName+"/";
	    }
	public static void main(String[] args) throws IOException {
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		List<String> users = readFileToList(getAbsolutePathAtMavenClass(UserInforUtils.class)+ "/users.txt");
        int id=100;
		for(String user:users){
			String sql = "insert into MS_SECURITY_USER"
					+ " (user_id,username,password,zt) "
					+ "values ('"+id+"','"+user.split("#")[0]+"','"+bpe.encode(user.split("#")[1])+"','0');";
			System.out.println(sql);
			id++;
		}
		/*zk449H#EDC07C
		zk450H#8DBBDC
		zk451H#11CE0D
		zk452H#CD7473
		zk453H#523B04
		zk454H#9371D4
		zk455H#6F8198
		zk456H#1EF732
		zk457H#E8057F
		zk458H#7EE05B
		zk459H#1D0944
		zk460H#CBB86E
		zk461H#DDE536
		zk462H#5BA55D
		zk463H#0A9F48
		zk464H#FDD9E3
		zk465H#D7D669
		zk466H#88D33B*/
	}
}
