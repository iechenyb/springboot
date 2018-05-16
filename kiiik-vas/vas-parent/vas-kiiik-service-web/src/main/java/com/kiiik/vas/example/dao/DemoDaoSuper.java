package com.kiiik.vas.example.dao;
import java.util.List;

import javax.validation.Valid;

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
public interface DemoDaoSuper {
	@PostMapping(value = "/json/recList")
	public Object paraList(@RequestBody List<User> user);
	@PostMapping(value = "/json/addUser")
	public User addUser(@RequestBody User user);
	@PostMapping(value = "/json/arrStr")
	public List<String> addUser(@RequestBody List<String> arrStr);
	@PostMapping(value = "/json/rqPost")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public ResultBean<RQ> addUserPost(@Valid @RequestBody RQ rq);
	@GetMapping(value = "/json/rqGet")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public ResultBean<RQ> addUserGet(RQ rq);
}