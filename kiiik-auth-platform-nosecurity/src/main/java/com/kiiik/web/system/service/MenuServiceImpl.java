package com.kiiik.web.system.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiiik.pub.service.BaseService;
import com.kiiik.web.system.mapper.UserMapper;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.RoleMenu;
import com.kiiik.web.system.utils.TreeUtils;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月22日
 */
@Service
public class MenuServiceImpl extends BaseService {
	Log log = LogFactory.getLog(MenuServiceImpl.class);
	@Autowired
	UserMapper userMapper;
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取系统菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 */
	public Menu getSystemMenuTree(){
		List<Menu> menusAll = this.genericDao.queryDBEntityList(new Menu());
		Menu root = TreeUtils.getRoot();
		TreeUtils.madeTree(menusAll,root);
		return root;
	}
	
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取系统菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 */
	public Menu getUserSystemMenuTree(String userId){
		List<Menu> menusAll = userMapper.getUserMenus(userId);
		Menu root = TreeUtils.getRoot();
		TreeUtils.madeTree(menusAll,root);
		return root;
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 获取角色指派的菜单<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleId
	 * @return 
	 */
	public List<RoleMenu> getAssignedRoleMenu(Integer roleId){
		RoleMenu rm = new RoleMenu();
		rm.setRoleId(roleId);
		return this.genericDao.queryDBEntityList(rm);
	}
	
}
