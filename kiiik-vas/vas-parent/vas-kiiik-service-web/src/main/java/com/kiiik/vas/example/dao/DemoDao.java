package com.kiiik.vas.example.dao;
import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.vas.example.model.RQ;
import com.kiiik.vas.example.model.User;

import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月16日
 */
@FeignClient(name = "VAS-KIIIK-SERVICE",fallbackFactory=DemoDaoCallBack.class)
public interface DemoDao {
	@PostMapping(value = "/recList")
	public Object paraList(@RequestBody List<User> user);
	@PostMapping(value = "addUser")
	public User addUser(@RequestBody User user);
	@PostMapping(value = "arrStr")
	public List<String> addUser(@RequestBody List<String> arrStr);
	@PostMapping(value = "rqPost")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public ResultBean<RQ> addUserPost(@Valid @RequestBody RQ rq);
	@GetMapping(value = "rqGet")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public ResultBean<RQ> addUserGet(RQ rq);
}
class DemoDaoCallBack implements DemoDao{

	@Override
	public Object paraList(List<User> user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> addUser(List<String> arrStr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean<RQ> addUserPost(RQ rq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean<RQ> addUserGet(RQ rq) {
		// TODO Auto-generated method stub
		return null;
	}
	
} 
