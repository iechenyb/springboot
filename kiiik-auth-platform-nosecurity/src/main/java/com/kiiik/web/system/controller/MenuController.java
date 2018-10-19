package com.kiiik.web.system.controller;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.Menu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("menu")
@Api("菜单管理模块")
public class MenuController {
	Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	GenericService genericService;
	
	
    /**
     * 
     *作者 : iechenyb<br>
     *<br>
     *创建时间: 2017年7月15日hj12
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
	

	@SuppressWarnings({ "unchecked"})
	@PostMapping("add")
	@ApiOperation("菜单信息新增")
	public ResultBean<String> addMenu(@RequestBody Menu menu){
		Menu menu_tmp = null;
		menu_tmp = genericService.queryDBEntitySingleComplex(Menu.class, 
				new ComplexCondition()
				.and()
				.col("url")
				.eq(menu.getUrl()));
		int count = 0 ;
		if(menu_tmp==null){
			count = genericService.insertDBEntity(menu);
			return new ResultBean<Integer>(count).fail("菜单插入成功!");
		}
		return new ResultBean<Integer>(count).success("菜单已经存在！");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("deleteById")
	@ApiOperation(value="根据主键删除菜单")
	public ResultBean<String> delMenu(Integer id){
		Menu menu = new Menu();
		menu.setId(id);
		int count = genericService.deleteDBEntityByKey(menu);
		return new ResultBean<String>("删除"+count+"记录！").success();
		
	}

	@SuppressWarnings("unchecked")
	@PostMapping("update")
	@ApiOperation(value="更新菜单信息")
	public ResultBean<String> updMenu(@RequestBody Menu menu){
		Menu menu_tmp = null;
		menu_tmp = genericService.queryDBEntitySingleComplex(Menu.class, 
				new ComplexCondition().col("id").notIn(menu.getId()).and().col("url").eq(menu.getUrl()));
		if(menu_tmp!=null){
			return new ResultBean<String>().success("角色名已经存在！");
		}else{
			int count = genericService.updateDBEntityByKey(menu);
			return new ResultBean<String>().success("更新成功！更新记录数"+count);
		}
	}
}
