package com.kiiik.web.company.controller;


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
import com.kiiik.web.company.entity.CompanyEntity;
import com.kiiik.web.company.service.CompanyService;

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
@RequestMapping("company")
@Api(value = "公司信息查询", description = "公司基本信息操作API", tags = "CompanyApi")
public class CompanyController {

	Log log = LogFactory.getLog(CompanyController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private CompanyService companyService;

   
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
    //@ApiOperation(value = "/list",notes="列表信息")
    public ResultBean<List<CompanyEntity>> list(@RequestBody CompanyEntity entity){
       List<CompanyEntity> entitys = genericService.queryDBEntityList(entity);
        return new ResultBean<List<CompanyEntity>>(entitys).success();
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
	public ResultBean<List<CompanyEntity>> listUsersPage(CompanyEntity entity, @ModelAttribute @Validated Page page) {
		List<CompanyEntity> entitys = genericService.queryDBEntityList(entity, page.getPageNum(), page.getPageSize(), " id asc");
		return new ResultBean<List<CompanyEntity>>(entitys).success();
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
	public ResultBean<String> addCompanyEntity(@RequestBody CompanyEntity entity){
		return companyService.addCompanyEntity(entity);
		
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
	public ResultBean<String> updCompanyEntity(@RequestBody CompanyEntity entity){
		return companyService.updCompanyEntity(entity);
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
	public ResultBean<String> delCompanyEntity(@RequestParam("ids") List<Integer> ids){
		return companyService.delCompanyEntity(ids);
	}

}
