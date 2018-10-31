package com.kiiik.web.system.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiiik.pub.service.BaseService;
import com.kiiik.web.system.mapper.UserMapper;
import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.po.UserRole;
import com.kiiik.web.system.utils.TreeUtils;
import com.kiiik.web.system.vo.UserRoleVo;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
@Service
public class UserServiceImpl extends BaseService {
	Log log = LogFactory.getLog(UserServiceImpl.class);
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 保存用户角色信息<br>
	 *创建时间: 2017年7月15日hj12
	 *@param roleIds
	 *@param userId
	 *@return
	 */
	public int saveUserRoles(String[] roleIds,String userId){
		
		UserRole ur=null;
		ur = new UserRole();
		ur.setUserId(userId);
		this.genericDao.deleteDBEntity(ur);//删除之前的角色信息
		ur = null;
		
		int total = roleIds.length;
		List<Object> urs = new ArrayList<Object>(total);
		for(int i=0;i<total;i++){
			ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleIds[i]);
			urs.add(ur);
		}
		return this.genericDao.insertDBEntityBatch(urs);
	}
	
	@Autowired
	UserMapper userMapper;
	
	public List<UserRoleVo> getUserRoles(Integer userId){
		return userMapper.getUserRoles(userId);
	}
	public Menu getUserMenus(Integer userId){
		List<Menu> menusAll = userMapper.getUserMenus(userId);
		Menu root = TreeUtils.getRoot();
		TreeUtils.madeTree(menusAll,root);
		return root;
	}
}
