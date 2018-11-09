package com.kiiik.web.department.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.dao.GenericDao;
import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.department.dao.DepartmentDao;
import com.kiiik.web.department.entity.DepartmentEntity;
import com.kiiik.web.department.service.DepartmentService;
import com.kiiik.web.employee.entity.EmployeeEntity;

/**
 * 业务处理层
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@Service
public class DepartmentServiceImpl  implements DepartmentService {
    //通用的数据库服务接口
    @Autowired
	GenericDao genericDao;
	
	//当前业务数据库服务接口
	@Autowired
	DepartmentDao  departmentDao;
	
    /**
	 * 
	 *作者 :  iechenyb<br>
	 *方法描述: 新增记录<br>
	 *创建时间: 2018-11-08 09:34:39
	 *@param 
	 *@return
	 */
	 @SuppressWarnings("unchecked")
	 public ResultBean<String> addDepartmentEntity(DepartmentEntity entity){
		 //校验名称是否重复
		 DepartmentEntity tmp = new DepartmentEntity();
		 tmp.setDepartmentname(entity.getDepartmentname());
		 if(!CollectionUtils.isEmpty(genericDao.queryDBEntityList(tmp))){
			 return new ResultBean<String>().fail("部门名称["+entity.getDepartmentname()+"]已经存在!");
		 }
		 //校验所在公司是否为空
		 CompanyEntity company = new CompanyEntity();
		 company.setId(entity.getSubcompanyid1());
		 company = genericDao.queryDBEntitySingle(company);
		 if(company==null){
			 return new ResultBean<String>().fail("部门所在的公司信息不存在!");
		 }
		 //校验所在部门是否为空
		if(entity.getSupdepid().intValue()!=KiiikContants.ZERO){//非根节点
			 tmp = new DepartmentEntity();
			 tmp.setId(entity.getSupdepid());//上级部门
			 tmp = genericDao.queryDBEntitySingle(tmp);
			 if(tmp==null){
				 return new ResultBean<String>().fail("上级部门不存在!");
			 }
		}
	 	int count = genericDao.insertDBEntity(entity);
		if(count==0){
			return new ResultBean<String>().fail("新增记录失败!");
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
	 public ResultBean<String> updDepartmentEntity(DepartmentEntity entity){
		 DepartmentEntity tmp = null;
		 //校验名称是否重复
		 tmp = genericDao.queryDBEntitySingleComplex(DepartmentEntity.class, 
					new ComplexCondition().col("id").notIn(entity.getId())
					.and()
					.col("departmentname").eq(entity.getDepartmentname()));
		 if(tmp!=null){
			 return new ResultBean<String>().fail("部门名称["+entity.getDepartmentname()+"]已经存在!");
		 }
		 //校验所在公司是否为空
		 CompanyEntity company = new CompanyEntity();
		 company.setId(entity.getSubcompanyid1());
		 company = genericDao.queryDBEntitySingle(company);
		 if(company==null){
			 return new ResultBean<String>().fail("部门所在的公司信息不存在!");
		 }
		 //校验所在部门是否为空
		if(entity.getSupdepid().intValue()!=KiiikContants.ZERO){//非根节点
			 //校验所在部门是否为空
			 tmp = new DepartmentEntity();
			 tmp.setId(entity.getSupdepid());//上级部门
			 tmp = genericDao.queryDBEntitySingle(tmp);
			 if(tmp==null){
				 return new ResultBean<String>().fail("上级部门不存在!");
			 }
		}
	 	int count = genericDao.updateDBEntityByKey(entity);
		if(count==0){
			return new ResultBean<String>().fail("更新记录失败!");
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
	 public ResultBean<String> delDepartmentEntity(List<Integer> ids){
	    if(!CollectionUtils.isEmpty(
	    		 genericDao.queryDBEntityListComplex(DepartmentEntity.class,
	    		new ComplexCondition()
	    		.and()
	    		.col("supdepid").inList(ids))
	    		)){
	    	return new ResultBean<String>().success("存在子部门信息，不能删除!");
	    }
	    if(!CollectionUtils.isEmpty(genericDao.queryDBEntityListComplex(EmployeeEntity.class,
	    		new ComplexCondition()
	    		.and()
	    		.col("departmentid").inList(ids)))){
	    	return new ResultBean<String>().success("存在员工信息，不能删除!");
	    }	    
	    DepartmentEntity entity = new DepartmentEntity();
		int count = genericDao.deleteDBEntityByKeyBatchs(entity,ids);
		if(count==0){
			return new ResultBean<String>().success("删除记录失败!");
		}else{
			return new ResultBean<String>().success("删除记录成功!");
		}
	 }
 
}
