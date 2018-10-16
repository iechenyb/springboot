package com.aiyi.base.pojo;

import com.aiyi.core.annotation.po.FieldName;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.annotation.po.TempField;
import com.aiyi.core.beans.Po;

@TableName(name="q_test_table1")
public class TestUserPo extends Po {

	private int id;
	
	@FieldName(name="img_id")
	private int imgId;
	
	private String name;
	
	@TempField
	private String imgUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
