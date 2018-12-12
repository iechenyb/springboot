package com.kiiik.vas.common.v1.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月10日
 */

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.utils.RandomUtils;
import com.kiiik.vas.common.v1.dao.GenericDaoImpl;
import com.kiiik.vas.common.v1.po.MyUserPO;

@RestController
@RequestMapping("generic")
public class GenericController {
	Log log = LogFactory.getLog(GenericController.class);

	@Autowired
	GenericDaoImpl userDao;

	@SuppressWarnings("unchecked")
	@GetMapping("/addUser")
	public ResultBean<String> addUser(Integer total) {
		MyUserPO po = null;
		if (total == null) {
			total = 1000;
		}
		for (int i = 0; i < total; i++) {
			po = new MyUserPO();
			po.setId(i);
			po.setName(RandomUtils.getChineaseName());
			po.setAccount(RandomUtils.getPingYin(po.getName()));
			po.setPassword(RandomUtils.getPassWord(8));
			try{
			   userDao.insertDBEntity(po);
			}catch(Exception e){
				//忽略生产中文乱码的问题
			}
		}
		return new ResultBean<String>("执行成功！").success();
	}
 
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据主键删除记录<br>
	 *创建时间: 2017年7月15日hj12
	 *@param id
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("delUserByKey")
	public ResultBean<String> delUserByKey(Integer id) {
		MyUserPO po = new MyUserPO();
		po.setId(id);
		userDao.deleteDBEntityByKey(po);
		return new ResultBean<String>("执行成功！").success();
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据属性删除记录<br>
	 *创建时间: 2017年7月15日hj12
	 *@param po
	 *@return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("delUser")
	public ResultBean<Integer> delUser(MyUserPO po) {
		int count = userDao.deleteDBEntity(po);
		return new ResultBean<Integer>(count).success();
	}

	@GetMapping("updUser")
	@SuppressWarnings("unchecked")
	public ResultBean<String> updUser(MyUserPO po) {
		userDao.updateDBEntityByKey(po);
		return new ResultBean<String>("执行成功！").success();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("queryMapping")
	public ResultBean<List<MyUserPO>> queryUser(MyUserPO po) {
		List<MyUserPO> data = userDao.queryDBEntityList(po);
		return new ResultBean<List<MyUserPO>>(data).success();
	}
	@SuppressWarnings("unchecked")
	@GetMapping("queryMappingOrder")
	public ResultBean<List<MyUserPO>> queryUserOrder(MyUserPO po) {
		List<MyUserPO> data = userDao.queryDBEntityList(po,"password asc","name desc");
		return new ResultBean<List<MyUserPO>>(data).success();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("queryUserByPage")
	public ResultBean<List<MyUserPO>> queryUserByPage(String account,int page, int pageSize) {
		MyUserPO po = new MyUserPO();
		po.setAccount(account);
		List<MyUserPO> data = userDao.queryDBEntityList(po,page,pageSize,"password asc","name desc");
		return new ResultBean<List<MyUserPO>>(data).success();
	}
}
