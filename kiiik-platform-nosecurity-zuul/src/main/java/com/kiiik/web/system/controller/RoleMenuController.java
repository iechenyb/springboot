package com.kiiik.web.system.controller;
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

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.web.system.po.RoleMenu;
import com.kiiik.web.system.service.impl.MenuServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月18日
 */
@RestController
@RequestMapping("rolemenu")
@Api("角色菜单管理模块")
public class RoleMenuController {
	Log log = LogFactory.getLog(RoleMenuController.class);
	
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
	public ResultBean<List<RoleMenu>> listMenus(@RequestBody RoleMenu rmenu){
		List<RoleMenu> rmenus = genericService.queryDBEntityList(rmenu);
		return new ResultBean<List<RoleMenu>>(rmenus).success();
	}
	
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 单个信息添加<br>
     *创建时间: 2017年7月15日
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
	@ApiOperation(value="根据主键删除角色菜单")
	public ResultBean<String> delMenu(Integer id){
		RoleMenu rmenu = new RoleMenu();
		rmenu.setId(id);
		int count = genericService.deleteDBEntityByKey(rmenu);
		if(count>0){
			return new ResultBean<String>().success("记录删除成功！");
		}else{
			return new ResultBean<String>().fail("记录删除失败！");
		}
		
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 只保存叶子节点<br>
	 *创建时间: 2017年7月15日
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
	 *创建时间: 2017年7月15日
	 *@param leafIds
	 *@param roleId
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("saveRoleMenuBatch")
	@ApiOperation("批量保存角色菜单信息")
	public ResultBean<String> saveRMBatch(@RequestParam(value = "menuIds[]") Integer[] menuIds,Integer roleId){
		int count = menuService.saveRMBatch(menuIds, roleId);
		if(count>0){
			return new ResultBean<String>().success("角色菜单信息保存成功！");
		}else{
			return new ResultBean<String>().success("角色菜单信息保存失败！");	
		}
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取角色的菜单<br>
	 *创建时间: 2017年7月15日
	 *@param roleId
	 */
	@SuppressWarnings({"unchecked" })
	@GetMapping("getRoleMenu")
	@ApiOperation("获取当前角色的菜单信息")
	public ResultBean<List<RoleMenu>> getAssignedRoleMenu(Integer roleId){
		List<RoleMenu> rm = menuService.getAssignedRoleMenu(roleId);
		return new ResultBean<List<RoleMenu>>(rm).success();
	}
	
	@Autowired
	MenuServiceImpl menuService;
}

