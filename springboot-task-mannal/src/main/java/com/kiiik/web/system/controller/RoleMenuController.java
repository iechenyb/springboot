package com.kiiik.web.system.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiiik.pub.bean.res.ResultBean;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.RoleMenu;
import com.kiiik.web.system.service.MenuServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("rolemenu")
@Api("菜单管理模块")
public class RoleMenuController {
	Log log = LogFactory.getLog(RoleMenuController.class);
	
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
	public ResultBean<List<RoleMenu>> listMenus(@RequestBody RoleMenu rmenu){
		List<RoleMenu> rmenus = genericService.queryDBEntityList(rmenu);
		return new ResultBean<List<RoleMenu>>(rmenus).success();
	}
	
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 单个信息添加<br>
     *创建时间: 2017年7月15日hj12
     *@param rmenu
     *@return
     */
	@SuppressWarnings({ "unchecked"})
	@PostMapping("add")
	@ApiOperation("角色菜单信息新增")
	public ResultBean<String> addMenu(@RequestBody RoleMenu rmenu){
		genericService.deleteDBEntity(rmenu);
		genericService.insertDBEntity(rmenu);
		return new ResultBean<String>().success("新增成功！");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("deleteById")
	@ApiOperation(value="根据主键删除菜单")
	public ResultBean<String> delMenu(Integer id){
		RoleMenu menu = new RoleMenu();
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
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 只保存叶子节点<br>
	 *创建时间: 2017年7月15日hj12
	 *@param leafIds
	 *@param roleId
	 */
	@GetMapping("saveRoleMenu")
	@ApiOperation("预留方法，暂时未开发。")
	public void saveRM(@RequestParam(value = "leafIds[]") String[] leafIds,String roleId){
		//根据叶子节点查询所有父类目录，将不重复节点存入，保证每角色读取的都会一颗子树。
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 只保存叶子节点<br>
	 *创建时间: 2017年7月15日hj12
	 *@param leafIds
	 *@param roleId
	 */
	@GetMapping("saveRoleMenuBatch")
	@ApiOperation("批量保存角色菜单信息")
	public void saveRMBatch(@RequestParam(value = "menuIds[]") Integer[] menuIds,Integer roleId){
		//根据叶子节点查询所有父类目录，将不重复节点存入，保证每角色读取的都会一颗子树。
		RoleMenu rm = new RoleMenu();
		rm.setRoleId(roleId);
		genericService.deleteDBEntity(rm);//清除历史记录
		List<Object> entitys= new ArrayList<Object>();
		RoleMenu rm_tmp ;
		for(int i=0;i<menuIds.length;i++){
			rm_tmp = new RoleMenu();
			rm_tmp.setMenuId(menuIds[i]);
			rm_tmp.setRoleId(roleId);
			entitys.add(rm_tmp);
			rm_tmp = null;
		}
		this.genericService.insertDBEntityBatch(entitys);
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取角色的菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("getRoleMenu")
	public ResultBean<List<RoleMenu>> getAssignedRoleMenu(Integer roleId){
		List<RoleMenu> rm = menuService.getAssignedRoleMenu(roleId);
		return new ResultBean(rm).success();
	}
	
	@Autowired
	MenuServiceImpl menuService;
}

