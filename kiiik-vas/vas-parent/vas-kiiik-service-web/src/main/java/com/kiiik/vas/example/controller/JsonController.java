package com.kiiik.vas.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.utils.ParamterUtils;
import com.kiiik.vas.example.dao.DemoDao;
import com.kiiik.vas.example.model.RQ;
import com.kiiik.vas.example.model.User;

import io.swagger.annotations.ApiOperation;

/**
 * 作者 : iechenyb<br>
 * 类描述:get 请求没有 request body 所以 json请求只能用post<br>
 * 创建时间: 2018年5月15日
 */
@RestController
public class JsonController {
	
	@Autowired
	DemoDao demoDao;
	Log log = LogFactory.getLog(JsonController.class);

	/**
	 * [ {"name":"test0","password":"gz0"} ,{"name":"test1","password":"gz1"} ]
	 * 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param list
	 * @return
	 * @throws JSONException 
	 */
	@PostMapping(value = "recListFegin")
	public Object recListFegin(@RequestBody List<User> list) throws JSONException {
		return demoDao.paraList(list);
	}
	/**
	 * {"name":"test0","password":"gz0"} 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping(value = "addUser")
	public User addUser(@RequestBody User user) {
		return demoDao.addUser(user);
	}

	/**
	 * ["a","b"] 作者 : iechenyb<br>
	 * 方法描述: 说点啥<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param arrStr
	 * @return
	 */
	@PostMapping(value = "arrStr")
	public List<String> addUser(@RequestBody List<String> arrStr) {
		return demoDao.addUser(arrStr);
	}

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 复杂的请求对象 RequestBody jsr校验<br>
	 * 创建时间: 2017年7月15日hj12
	 * {
		  "abCdDe": "string",
		  "f": "getUser",
		  "id": "99",
		  "pc": ["ag","cu"],
		  "v": "大于0的整数"
		}
	 * @param arrStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "rqPost")
	@ApiOperation(value = "趋势规模数据接口", notes = "注意问题点")
	public ResultBean<RQ> addUserPost(@Valid @RequestBody RQ rq) {
		return new ResultBean<RQ>(rq).success();
		//return demoDao.addUserPost(rq);
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: get请求时，json正常，通过feign调用服务的时候出现转义异常！！！！！<br>
	 *创建时间: 2017年7月15日hj12
	 *@param rq
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "rqGet")
	@ApiOperation(value = "趋势规模数据接口(存在问题，建议使用post)", notes = "注意问题点")
	public ResultBean<RQ> addUserGet(RQ rq) {
		return new ResultBean<RQ>(rq).success()
				;//demoDao.addUserGet(rq)
	}
}
