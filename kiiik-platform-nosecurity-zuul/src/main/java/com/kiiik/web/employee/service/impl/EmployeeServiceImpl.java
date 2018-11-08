package com.kiiik.web.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.dao.GenericDao;
import com.kiiik.web.employee.dao.EmployeeDao;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.employee.service.EmployeeService;

/**
 * 业务处理层
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@Service
public class EmployeeServiceImpl  implements EmployeeService {
    //通用的数据库服务接口
    @Autowired
	GenericDao genericDao;
	
	//当前业务数据库服务接口
	@Autowired
	EmployeeDao  employeeDao;
	
    /**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 新增记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public ResultBean<String> addEmployeeEntity(EmployeeEntity entity){
	    EmployeeEntity tmp = new EmployeeEntity();
		tmp.setLoginid(entity.getLoginid());
		if(!CollectionUtils.isEmpty(genericDao.queryDBEntityList(tmp))){
			 return new ResultBean<String>().fail("职工登录账号"+entity.getLoginid()+"已经存在!");
		}
	 	int count = genericDao.insertDBEntity(entity);
		if(count==0){
			return new ResultBean<String>().success("新增记录失败!");
		}else{
			return new ResultBean<String>().success("新增记录成功!");
		}
	 }
	 
	/**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 更新记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public ResultBean<String> updEmployeeEntity(EmployeeEntity entity){
		EmployeeEntity tmp = new EmployeeEntity();
		tmp = genericDao.queryDBEntitySingleComplex(EmployeeEntity.class, 
					new ComplexCondition()
					.and()
					.col("loginid")
					.eq(entity.getLoginid())
		 			.and().col("id").notIn(entity.getId()));
		if(tmp!=null){
			 return  new ResultBean<String>().fail("职工登录账号["+entity.getLoginid()+"]已经存在!");
		} 
	 	int count = genericDao.updateDBEntityByKey(entity);
		if(count==0){
			return new ResultBean<String>().success("更新记录失败!");
		}else{
			return new ResultBean<String>().success("更新记录成功!");
		}
	 }
	 
	/**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 删除记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public ResultBean<String> delEmployeeEntity(Integer id){
	    EmployeeEntity entity = new EmployeeEntity();
		entity.setId(id);
		int count = genericDao.deleteDBEntityByKey(entity);
		if(count==0){
			return new ResultBean<String>().success("删除记录失败!");
		}else{
			return new ResultBean<String>().success("删除记录成功!");
		}
	 }
 
}
