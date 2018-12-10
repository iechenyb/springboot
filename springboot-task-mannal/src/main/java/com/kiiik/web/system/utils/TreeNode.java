package com.kiiik.web.system.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年1月30日
 */

public class TreeNode {
	Log log = LogFactory.getLog(TreeNode.class);
	private String id;
	private String pid;
	private String name;
	private String icon;
	private List<TreeNode> children;
	
	public TreeNode(String recId,String name,String pid) {
		this.id = recId;
		this.pid = pid;
		this.name = name;
		this.children = new ArrayList<TreeNode>();
	}

	public TreeNode(String recId, String pid, String nodeName, String icon) {
		this.id = recId;
		this.pid = pid;
		this.name = nodeName;
		this.icon = icon;
		this.children = new ArrayList<TreeNode>();
	}

	public TreeNode(String id, String pid, String nodeName, String icon, List<TreeNode> children) {
		this.id = id;
		this.pid = pid;
		this.name = nodeName;
		this.icon = icon;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String recId) {
		this.id = recId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String nodeName) {
		this.name = nodeName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}
