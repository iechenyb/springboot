package com.kiiik.vas.common.v1.bean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月11日
 */
public class EntityInfoCol implements Cloneable{
	Log log = LogFactory.getLog(EntityInfoCol.class);
	private String entityColName;
	private String dbColName;
	private String insertIfNull;
	private String updateIfNull;
	private Boolean isKeyColumn;
	private Boolean useGeneratedKeys;
	private Object value;
	public String getEntityColName() {
		return entityColName;
	}
	public void setEntityColName(String entityColName) {
		this.entityColName = entityColName;
	}
	public String getDbColName() {
		return dbColName;
	}
	public void setDbColName(String dbColName) {
		this.dbColName = dbColName;
	}
	public String getInsertIfNull() {
		return insertIfNull;
	}
	public void setInsertIfNull(String insertIfNull) {
		this.insertIfNull = insertIfNull;
	}
	public String getUpdateIfNull() {
		return updateIfNull;
	}
	public void setUpdateIfNull(String updateIfNull) {
		this.updateIfNull = updateIfNull;
	}
	public Boolean getIsKeyColumn() {
		return isKeyColumn;
	}
	public void setIsKeyColumn(Boolean isKeyColumn) {
		this.isKeyColumn = isKeyColumn;
	}
	public Boolean getUseGeneratedKeys() {
		return useGeneratedKeys;
	}
	public void setUseGeneratedKeys(Boolean useGeneratedKeys) {
		this.useGeneratedKeys = useGeneratedKeys;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "EntityInfoCol [entityColName=" + entityColName + ", dbColName="
				+ dbColName + ", insertIfNull=" + insertIfNull
				+ ", updateIfNull=" + updateIfNull + ", isKeyColumn="
				+ isKeyColumn + ", useGeneratedKeys=" + useGeneratedKeys
				+ ", value=" + value + "]";
	}
	@Override
	protected EntityInfoCol clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (EntityInfoCol) super.clone();
	}
}
