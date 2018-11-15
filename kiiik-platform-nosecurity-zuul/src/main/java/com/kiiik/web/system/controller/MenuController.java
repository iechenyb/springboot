package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.service.impl.MenuServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("menu")
@Api(value = "菜单管理模块", description = "菜单基本信息操作API", tags = "MenuApi")
public class MenuController {
	Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	GenericService genericService;
	
	
    /**
     * 
     *作者 : iechenyb<br>
     *<br>
     *创建时间: 2017年7月15日
     *@param user
     *@return
     */
	@SuppressWarnings("unchecked")
	@ApiOperation("菜单信息查询")
	@PostMapping("list")
	public ResultBean<List<Menu>> listMenus(@RequestBody Menu menu){
		List<Menu> menus = genericService.queryDBEntityList(menu);
		return new ResultBean<List<Menu>>(menus).success();
	}
	

	@PostMapping("add")
	@ApiOperation("菜单信息新增")
	public ResultBean<String> addMenu(@RequestBody Menu menu){
		return menuService.saveMenu(menu); 
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("deleteById")
	@ApiOperation("根据主键删除信息")
	public ResultBean<String> delMenu(Integer id){
		Menu menu = new Menu();
		menu.setParentId(id);
		if(!CollectionUtils.isEmpty(genericService.queryDBEntityList(menu))){
			return new ResultBean<String>().fail("当前菜单存在子菜单，不能删除！");
		}
		menu = new Menu();
		menu.setId(id);
		int count = genericService.deleteDBEntityByKey(menu);
		if(count>0){
			return new ResultBean<String>().success("删除记录成功！");
		}else{
			return new ResultBean<String>().fail("删除记录失败！");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("deleteByIds")
	@ApiOperation(value="根据主键删除菜单")
	public ResultBean<String> delMenu(@RequestParam("ids") List<Integer> ids){
		//查询子菜单或者目录
		if(!CollectionUtils.isEmpty(
				genericService.queryDBEntityListComplex(Menu.class,
	    		new ComplexCondition()
	    		.and()
	    		.col("parentId").inList(ids))
	    		)){
			return new ResultBean<String>().fail("存在子菜单信息，不能删除！");
		}
		int count = genericService.deleteDBEntityByKeyBatchs(new Menu(),ids);
		if(count>0){
			return new ResultBean<String>().success("删除记录成功！");
		}else{
			return new ResultBean<String>().fail("删除记录失败！");
		}
	}

	@PostMapping("update")
	@ApiOperation(value="更新菜单信息")
	public ResultBean<String> updMenu(@RequestBody Menu menu){
		return menuService.updMenu(menu);
	}
	
	
	@Autowired
	MenuServiceImpl menuService;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取系统菜单<br>
	 *创建时间: 2017年7月15日
	 *@param roleId
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("getSystemMenuTree")
	@ApiOperation(value="系统菜单树")
	public ResultBean<Menu> getSystemMenuTree(){
		return new ResultBean<Menu>(menuService.getSystemMenuTree()).success();
	}
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取用户的系统菜单<br>
	 *创建时间: 2017年7月15日
	 *@param roleId
	 */
	@GetMapping("getUserSystemMenuTree")
	@ApiOperation(value="用户系统菜单")
	public ResultBean<Menu> getUserSystemMenuTree(Integer userId){
		return new ResultBean<Menu>(menuService.getUserSystemMenuTree(userId));
	}
	
}

