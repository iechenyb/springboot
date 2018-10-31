package com.kiiik.web.system.service;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.kiiik.pub.service.BaseService;
import com.kiiik.web.contant.Contants_Test;
import com.kiiik.web.system.mapper.MenuMapper;
import com.kiiik.web.system.mapper.UserMapper;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.RoleMenu;
import com.kiiik.web.system.utils.TreeUtils;
import com.kiiik.web.system.vo.RoleMenuVo;
@Service
public class MenuServiceImpl extends BaseService {
	Log log = LogFactory.getLog(MenuServiceImpl.class);
	@Autowired
	UserMapper userMapper;
	@Autowired
	MenuMapper menuMapper;
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
	public Menu getUserSystemMenuTree(Integer userId){
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
	
	public Map<String, Collection<ConfigAttribute>> systemRoleMenus(){
			Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
			List<RoleMenuVo> auths = menuMapper.systemRoleMenus();
			auths.addAll(Contants_Test.testRoleMenu());
			if(!CollectionUtils.isEmpty(auths)){
				for(RoleMenuVo rm:auths){
					//同一个url对应多个角色
					if(!StringUtils.isEmpty(rm.getUrl())){
						if(resourceMap.get(rm.getUrl())==null){
							resourceMap.put(rm.getUrl(), new HashSet<ConfigAttribute>());
						}
						ConfigAttribute configAttribute = new SecurityConfig(rm.getRoleName());// 角色标记
						resourceMap.get(rm.getUrl()).add(configAttribute);
					}
				}
			}
			return resourceMap;
	}
	
}
