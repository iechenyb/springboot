package com.kiiik.web.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kiiik.web.system.mapper.OrganizationMapper;
import com.kiiik.web.system.utils.OrganizationTreeUtils;
import com.kiiik.web.system.utils.TreeNode;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年11月7日
 */
@Service
public class OrganizationServiceImpl {
	
	@Autowired
	OrganizationMapper orgMapper;
	
	public TreeNode getOranizationTree(){
		List<TreeNode> departs = orgMapper.getDepartmentList();
		List<TreeNode> companys = orgMapper.getCompanyList();
		TreeNode rootDept = new TreeNode("0","树根","0");
		TreeNode rootComp = new TreeNode("0","树根","0");
		OrganizationTreeUtils.madeTree(departs, rootDept);//部门组装好
		//遍历公司，如果一级部门的公司id等于当前公司，则添加到公司的部门列表上去
		for(TreeNode company:companys){
			List<TreeNode> departments = rootDept.getChildren();
			for(TreeNode department:departments){
				//判断一级部门节点的所在公司信息
				if(department.getReservedField().equals(company.getId())){
					if(CollectionUtils.isEmpty(company.getDeparments())){
						company.setDeparments(new ArrayList<TreeNode>());
					}
					company.getDeparments().add(department);
				}
			}
		}
		OrganizationTreeUtils.madeTree(companys, rootComp);//部门组装好
		return rootComp;	
	}
}
