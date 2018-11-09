package com.kiiik.web.company.service;

import java.util.List;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.web.company.entity.CompanyEntity;

/**
 * 服务接口定义
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
public interface CompanyService  {

/**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 新增记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 ResultBean<String> addCompanyEntity(CompanyEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 更新记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 ResultBean<String> updCompanyEntity(CompanyEntity entity);
 
 /**
 * 
 *作者 :  iechenyb<br>
 *方法描述: 根据主键删除记录<br>
 *创建时间: 2018-11-08 09:34:39
 *@param 
 *@return
 */
 ResultBean<String> delCompanyEntity(List<Integer> ids);
}

