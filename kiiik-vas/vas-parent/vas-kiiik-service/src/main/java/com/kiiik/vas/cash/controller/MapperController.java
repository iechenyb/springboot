package com.kiiik.vas.cash.controller;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.exception.VasException;
import com.kiiik.vas.example.model.MyUser;
import com.kiiik.vas.example.service.MyUserServiceImpl2;
/**
 * 
 * @author DHUser
 *  样例控制层代码
 */
@RestController
@RequestMapping("mapper")
public class MapperController {
	Log log = LogFactory.getLog(MapperController.class);
	@Autowired
	MyUserServiceImpl2 userService;
	
	@GetMapping("/hello")
	@ResponseBody
	public String sayHello(String name,boolean hasEx) throws VasException{
		if(hasEx){
			throw new VasException("除数不能为零！");
		}
		return "hello "+name;
	}
	@GetMapping("get")
	@ResponseBody
	public MyUser getUser(String acc) {
		return userService.getUser(acc);
	}
	
	@GetMapping("list")
	@ResponseBody
	public List<MyUser> listUser() {
		return userService.getUserList();
	}
	
	@GetMapping("save")
	@ResponseBody
	public MyUser save() {
		MyUser user = new MyUser();
		user.setId(UUID.randomUUID().toString());
		user.setAccount("sssss");
		user.setPassword("sfsddf");
		userService.save(user);
		return user;
	}
	
	@GetMapping("update")
	@ResponseBody
	public MyUser update(MyUser user) {
		userService.update(user);
		return user;
	}
	
	@GetMapping("delete")
	@ResponseBody
	public String delete(Integer id) {
		userService.delete(id);
		return "删除成功";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("page")
	@ResponseBody
	public ResultBean<List<MyUser>> queryByPage(int pageIndex,int pageSize) {
		if(pageIndex<=0){
			return new ResultBean<>().fail("页面序号必须大于0");
		}
		if(pageSize<=0){
			return new ResultBean<>().fail("没页记录数必须大于0");
		}
		
		return new ResultBean<>().data(userService.selectMyUserByPage(pageIndex,pageSize));
	}
	
}
