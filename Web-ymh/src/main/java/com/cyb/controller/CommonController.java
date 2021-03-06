package com.cyb.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.utils.SpringUtils;
import com.google.common.collect.ImmutableMap;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2017年12月7日
 */
@RequestMapping("common")
@Controller
public class CommonController {
	
	static List<Role>  roles = new ArrayList<Role>();
	
	@ResponseBody
	@GetMapping("infor")
	public int infoRoles(){
		return roles.size();
	}
	
	@ResponseBody
	@GetMapping("init")
	public String initRoleList(int num){
		for(int i=0;i<num;i++){
			Role role = new Role(i,"role"+i,"desc"+i);
			roles.add(role);
		}
		return "success";
	}
	
	@ResponseBody
	@GetMapping("webPath")
	public String getPath(){
		return SpringUtils.getWebPath();
	}
	
	@ResponseBody
	@GetMapping("responseEntity")
	public ResponseEntity<Map<String, String>> testResponseEntity(){
		Map<String, String> map = ImmutableMap.of("key", "value");
	    return ResponseEntity.ok(map);
	}
	
	@ResponseBody
	@GetMapping("ImmutableMap")
	public Map<String, String>  ImmutableMap(){
		return ImmutableMap.of("key", "value");
	}
		
	@ResponseBody
	@GetMapping("roles")
	public Map<String,Object> getRoleList(int start,int limit){
		//if(roles.size()==0){initRoleList(100);}
		System.out.println("分页信息："+start+"->"+limit);
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("total", roles.size());
		if((start+limit)>=roles.size()){
			ret.put("data",roles.subList(start, roles.size()));
		}else{
			ret.put("data",roles.subList(start, start+limit));
		}
		return ret;
	}
	
	
	@ResponseBody
	@GetMapping("addRole")
	public Map<String,String>  addRole(String name,String desc){
		Map<String,String> res = new HashMap<String,String>();
		roles.add(new Role(roles.size()+1,name,desc));
		System.out.println("新增角色"+name);
		res.put("status", "1");//成功
		res.put("msg", "增加成功");
		return res;
	}
	
	
	public boolean hasRole(String ids,int curId){
		boolean has = false;
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			if(curId==Integer.valueOf(id[i])){
				System.out.println("delete id is"+curId);
				has = true;
				break;
			}
		}
		return has;
	}
	
	@ResponseBody
	@GetMapping("delRole")
	public Map<String,String>  delRole(String ids){
		Iterator<Role> iter = roles.iterator();
		while(iter.hasNext()){
			if(hasRole(ids,iter.next().getId())){
				iter.remove();
			}
		}
		Map<String,String> res = new HashMap<String,String>();
		res.put("status", "1");//成功
		res.put("msg", "删除成功");
		return res;
	}
	
	@GetMapping("toPage")
	public String toPage(Map<String,String> param){
		param.put("name", "chenyb");
		return "index";
	}
	
	@GetMapping("toPage1")
	public ModelAndView toPage1(){
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb");
		view.setViewName("index");
		return view;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("userinfor")
	@ResponseBody
	public String userInfor(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();  
		Iterator<GrantedAuthority> iter = (Iterator<GrantedAuthority>) userDetails.getAuthorities().iterator();
		while(iter.hasNext()){
			GrantedAuthority g = iter.next();
			System.out.println("用户角色"+g.getAuthority());
		}
		return userDetails.getUsername() ;
	}
	
}
