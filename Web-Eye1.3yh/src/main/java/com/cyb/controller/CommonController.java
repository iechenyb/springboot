package com.cyb.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyb.aop.ResultBean;
import com.cyb.condition.ConditionService;
import com.cyb.jiami.MD5Util;
import com.cyb.service.AsyncTaskService;
import com.cyb.service.AwareService;
import com.cyb.utils.DESUtil;
import com.cyb.utils.SpringUtils;
import com.google.common.collect.ImmutableMap;

import sun.security.provider.SHA;

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
		service.outputInfo();
		return SpringUtils.getWebPath();
	}
	@Autowired
	 AsyncTaskService asyncTaskService;
	@ResponseBody
	@GetMapping("asyncTaskService")
	public void asyncTaskService(){
		for (int i=0;i<10;i++){
            asyncTaskService.executeAsyncTask(i);
            asyncTaskService.executeAsyncTaskPlus(i);
        }
	}
	@Autowired
	AwareService service;
	@ResponseBody
	@GetMapping("springcontain")
	public void springcontain(){
		service.outputInfo();
	}
	@Autowired
	ConditionService conditionService;
	@ResponseBody
	@GetMapping("conditionService")
	public String conditionService(){
		return conditionService.show();
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
		view.addObject("name", "chenyb1");
		view.setViewName("index");
		return view;
	}
	@GetMapping("toPage2")
	public ModelAndView toPage2(){
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb2");
		view.setViewName("index");
		return view;
	}
	
	@GetMapping("toPage3")
	public ModelAndView toPage3(){
		ModelAndView view = new ModelAndView();
		view.addObject("name", "chenyb3");
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
	//==========mvc单元测试用例=====================
	@GetMapping("name")
	@ResponseBody
	public String myName(String p1,String p2){
		System.out.println(p1+","+p2);
		return "cyb";
	}
	//测试负载均衡时，会话一致性使用
	@GetMapping(value="/getSessionId")  
    @ResponseBody  
    public String getSessionId(HttpServletRequest request){  
        Object o = request.getSession().getAttribute("springboot");  
        if(o == null){  
            o = "spring boot 牛逼了!!!有端口"+request.getLocalPort()+"生成";  
            request.getSession().setAttribute("springboot", o);  
        }  
        return "端口=" + request.getLocalPort() +  " sessionId=" + request.getSession().getId() +"<br/>"+o;  
    }  
	//测试负载均衡使用
	@GetMapping(value="/getServerInfor")  
    @ResponseBody  
    public String getServerInfor(HttpServletRequest request){  
		String str =  " sessionId=" + request.getSession().getId() +",localaddr="+request.getLocalAddr()+",localport="+request.getLocalPort()+",remotehost="+request.getRemoteHost()+",remoteport="+request.getRemotePort()+",remoteaddr="+request.getRemoteAddr();
		System.out.println(str);
        return "端口=" + request.getServerPort() +  " ip=" + request.getServerName()+" username="+request.getSession().getAttribute("userid")+"---->"+str;  
    }  
	//登录加密使用
	@SuppressWarnings("unchecked")
	@GetMapping(value="/getDesString")
    @ResponseBody  
	public ResultBean<Map<String,String>> getDesString(HttpServletRequest req) throws Exception{
        String key1 = DESUtil.jdkSHA1(String.valueOf(System.currentTimeMillis()));
        String key2 = DESUtil.jdkBase64(key1+"chenyuanbao");
        String key3 =MD5Util.md5Encode(key2+"iechenyb");
        System.out.println(key1+","+key2+","+key3);
        req.getSession().setAttribute("k1", key1);
        req.getSession().setAttribute("k2", key2);
        req.getSession().setAttribute("k3", key3);
        Map<String,String> tmp = new HashMap<String,String>();
        tmp.put("a", key1);  tmp.put("b", key2);  tmp.put("c", key3);
        return new ResultBean<Map<String,String>>().data(tmp).success();
	}
	@SuppressWarnings("unchecked")
	@GetMapping(value="/getDesStringFromReq")
    @ResponseBody  
	public ResultBean<Map<String,Object>> getDesStringFromReq(HttpServletRequest req) throws Exception{
        Map<String,Object> tmp = new HashMap<String,Object>();
        tmp.put("a",   req.getSession().getAttribute("k1")); 
        tmp.put("b",  req.getSession().getAttribute("k2")); 
        tmp.put("c",  req.getSession().getAttribute("k3"));
        return new ResultBean<Map<String,Object>>().data(tmp).success();
	}
}
