package com.kiiik.web.system.mapper;

import java.util.List;

import com.kiiik.web.system.po.Menu;
import com.kiiik.web.system.vo.UserRoleVo;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年10月19日
 */
public interface UserMapper {
	List<UserRoleVo> getUserRoles(String userId);//获取用户的角色
	List<Menu> getUserMenus(String userId);//获取用户的菜单
}