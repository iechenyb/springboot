package com.kiiik.web.employee.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kiiik.pub.bean.Page;
import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.employee.service.EmployeeService;

/**
 * 请求控制层
 *
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@RestController
@RequestMapping("employee")
@Api(value = "职工信息查询", description = "职工基本信息操作API", tags = "EmployeeApi")
public class EmployeeController {

	Log log = LogFactory.getLog(EmployeeController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private EmployeeService employeeService;

   
    /**
     * 
     *作者 : iechenyb<br>
     *数据列表<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/list")
    @ApiOperation("列表信息")
    public ResultBean<List<EmployeeEntity>> list(@RequestBody EmployeeEntity entity){
       List<EmployeeEntity> entitys = genericService.queryDBEntityList(entity);
        return new ResultBean<List<EmployeeEntity>>(entitys).success();
    }
    
     /**
     * 
     *作者 : iechenyb<br>
     *数据列表分页查询<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
    @SuppressWarnings("unchecked")
	@GetMapping("listPage")
	@ApiOperation("分页查询")
	public ResultBean<List<EmployeeEntity>> listUsersPage(EmployeeEntity entity, @ModelAttribute @Validated Page page) {
		List<EmployeeEntity> entitys = genericService.queryDBEntityList(entity, page.getPageNum(), page.getPageSize(), " id asc");
		return new ResultBean<List<EmployeeEntity>>(entitys).success();
	}
    
    /**
     * 
     *作者 : iechenyb<br>
     *新增记录<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@PostMapping("add")
	@ApiOperation("新增信息")
	public ResultBean<String> addEmployeeEntity(@RequestBody EmployeeEntity entity){
		return employeeService.addEmployeeEntity(entity);
		
	}
	
	/**
     * 
     *作者 : iechenyb<br>
     *更新记录<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@PostMapping("update")
	@ApiOperation("更新信息")
	public ResultBean<String> updEmployeeEntity(@RequestBody EmployeeEntity entity){
		return employeeService.updEmployeeEntity(entity);
	}
	
	 /**
     * 
     *作者 : iechenyb<br>
     *根据主键删除记录<br>
     *创建时间: 2018-11-08 09:34:39
     *@param 
     *@return
     */
	@GetMapping("deleteByIds")
	@ApiOperation("根据主键删除信息")
	public ResultBean<String> delEmployeeEntity(@RequestParam("ids") List<Integer> ids){
		return employeeService.delEmployeeEntity(ids);
	}
	
	@GetMapping("deleteById")
	@ApiOperation("根据主键删除信息")
	public ResultBean<String> delCompanyEntity(Integer id){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id);
		return employeeService.delEmployeeEntity(ids);
	}

}
