package com.aiyi.base.pojo;

import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.beans.Po;

@TableName(name="q_test_table2")
public class TestImgResourcePo extends Po {

	private int id;
	
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
