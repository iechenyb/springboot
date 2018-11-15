package com.kiiik.web.department.controller;


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
import com.kiiik.web.department.entity.DepartmentEntity;
import com.kiiik.web.department.service.DepartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 请求控制层
 *
 * 作者: iechenyb
 * 邮件: zzuchenyb@sina.com
 * 日期: 2018-11-08 09:34:39
 */
@RestController
@RequestMapping("department")
@Api(value = "部门信息查询", description = "部门基本信息操作API", tags = "DepartmentApi")
public class DepartmentController {

	Log log = LogFactory.getLog(DepartmentController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private DepartmentService departmentService;

   
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
    public ResultBean<List<DepartmentEntity>> list(@RequestBody DepartmentEntity entity){
       List<DepartmentEntity> entitys = genericService.queryDBEntityList(entity);
        return new ResultBean<List<DepartmentEntity>>(entitys).success();
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
	public ResultBean<List<DepartmentEntity>> listUsersPage(DepartmentEntity entity,@ModelAttribute @Validated Page page) {
		List<DepartmentEntity> entitys = genericService.queryDBEntityList(entity, page.getPageNum(), page.getPageSize(), " id asc");
		return new ResultBean<List<DepartmentEntity>>(entitys).success();
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
	public ResultBean<String> addDepartmentEntity(@RequestBody DepartmentEntity entity){
		return departmentService.addDepartmentEntity(entity);
		
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
	public ResultBean<String> updDepartmentEntity(@RequestBody DepartmentEntity entity){
		return departmentService.updDepartmentEntity(entity);
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
	public ResultBean<String> delDepartmentEntity(@RequestParam(value = "ids") List<Integer> ids){
		return departmentService.delDepartmentEntity(ids);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 单个删除<br>
	 *创建时间: 2018年11月12日
	 *@param id
	 *@return
	 */
	@GetMapping("deleteById")
	@ApiOperation("根据主键删除信息")
	public ResultBean<String> delDepartmentEntity(Integer id){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id);
		return departmentService.delDepartmentEntity(ids);
	}
}
