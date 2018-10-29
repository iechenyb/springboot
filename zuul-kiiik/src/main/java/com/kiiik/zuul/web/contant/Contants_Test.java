package com.kiiik.zuul.web.contant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kiiik.zuul.web.auth.bean.RoleMenu;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月25日
 */
public class Contants_Test {
	public static String TEST_ADD_USERNAME="add";
	public static String TEST_UPD_USERNAME="upd";
	public static String TEST_DEL_USERNAME="del"; 
	public static String TEST_QUERY_USERNAME="query"; 
	
	public static String TEST_ADD_RoleNAME="ROLE_ADD";
	public static String TEST_UPD_RoleNAME="ROLE_UPDATE";
	public static String TEST_DEL_RoleNAME="ROLE_DELETE"; 
	public static String TEST_QUERY_RoleNAME="ROLE_QUERY"; 
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 添加固定的角色菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@return
	 */
	public static List<RoleMenu> testRoleMenu(){
		List<RoleMenu> auths = new ArrayList<RoleMenu>();
		RoleMenu rm1 = new RoleMenu();
		rm1.setRoleName(Contants_Test.TEST_ADD_RoleNAME);
		rm1.setUrl("/api1/api/add");
		
		RoleMenu rm2 = new RoleMenu();
		rm2.setRoleName(Contants_Test.TEST_DEL_RoleNAME);
		rm2.setUrl("/api1/api/delete");
		
		RoleMenu rm3 = new RoleMenu();
		rm3.setRoleName(Contants_Test.TEST_UPD_RoleNAME);
		rm3.setUrl("/api1/api/update");
		
		RoleMenu rm4 = new RoleMenu();
		rm4.setRoleName(Contants_Test.TEST_QUERY_RoleNAME);
		rm4.setUrl("/api1/api/query");
		
		auths.add(rm1);
		auths.add(rm2);
		auths.add(rm3);
		auths.add(rm4);
		return auths;
		
	}
	
	public static SimpleGrantedAuthority testRoles(String empno){
		//测试使用
		if(Contants_Test.TEST_ADD_USERNAME.equals(empno)){
			return new SimpleGrantedAuthority(Contants_Test.TEST_ADD_RoleNAME);
		}else if(Contants_Test.TEST_DEL_USERNAME.equals(empno)){
			return new SimpleGrantedAuthority(Contants_Test.TEST_DEL_RoleNAME);
		} else if(Contants_Test.TEST_UPD_USERNAME.equals(empno)){
			return new SimpleGrantedAuthority(Contants_Test.TEST_UPD_RoleNAME);
		}else if(Contants_Test.TEST_QUERY_USERNAME.equals(empno)){
			return new SimpleGrantedAuthority(Contants_Test.TEST_QUERY_RoleNAME);
		}else{
			return new SimpleGrantedAuthority("");
		}
	}
}
