package com.kiiik.web.system.service.impl;
import java.util.ArrayList;
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

import com.kiiik.pub.bean.ResultBean;
import com.kiiik.pub.contant.Contants_Test;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.service.BaseService;
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
	
	public int saveRMBatch(Integer[] menuIds,Integer roleId){
		RoleMenu rm = new RoleMenu();
		rm.setRoleId(roleId);
		genericDao.deleteDBEntity(rm);//清除历史记录
		List<Object> entitys= new ArrayList<Object>();
		RoleMenu rm_tmp ;
		for(int i=0;i<menuIds.length;i++){
			rm_tmp = new RoleMenu();
			rm_tmp.setMenuId(menuIds[i]);
			rm_tmp.setRoleId(roleId);
			entitys.add(rm_tmp);
			rm_tmp = null;
		}
		return this.genericDao.insertDBEntityBatch(entitys);
	}
	
	@SuppressWarnings("unchecked")
	public ResultBean<String> saveMenu(Menu menu){
		Menu menu_tmp = null;
		if(KiiikContants.ROOTID!=menu.getParentId()){//不是根节点id值
			//从数据库查询id值对应父菜单记录
			menu_tmp = new Menu();
			menu_tmp.setId(menu.getParentId());
			menu_tmp = genericDao.queryDBEntitySingle(menu_tmp);
			if(menu_tmp==null){
				return new ResultBean<String>().fail("父菜单信息不存在！");
			}
		}
		menu_tmp = genericDao.queryDBEntitySingleComplex(Menu.class, 
				new ComplexCondition()
				.and()
				.col("url")
				.eq(menu.getUrl()));
		if(menu_tmp==null){
			genericDao.insertDBEntity(menu);
			return new ResultBean<String>().success("菜单插入成功!");
		}else{
			return new ResultBean<String>().fail("菜单地址已经存在！");
		}
	}
	
	@SuppressWarnings("unchecked")
	public ResultBean<String> updMenu(Menu menu){
		Menu menu_tmp = null;
		menu_tmp = genericDao.queryDBEntitySingleComplex(Menu.class, 
				new ComplexCondition().col("id").notIn(menu.getId()).and().col("url").eq(menu.getUrl()));
		if(menu_tmp!=null){
			return new ResultBean<String>().fail("角色名已经存在！");
		}else{
			int count = genericDao.updateDBEntityByKey(menu);
			return new ResultBean<String>().success("更新成功！更新记录数"+count);
		}
	}
	
}
