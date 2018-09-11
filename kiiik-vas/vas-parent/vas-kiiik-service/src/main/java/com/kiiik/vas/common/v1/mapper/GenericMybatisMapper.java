package com.kiiik.vas.common.v1.mapper;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.kiiik.vas.common.v1.bean.EntityInfo;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月11日
 */
public interface GenericMybatisMapper {
	/*public Page<Map<String, Object>> queryDBEntity(EntityInfo entityInfo);
	
	public Page<Map<String, Object>> queryDBEntityComplex(@Param("entityInfo") EntityInfo entityInfo,@Param("complexCond")ComplexCond complexCond);
	*/
	public int insertDBEntity(EntityInfo entityInfo);
	
	public int insertDBEntityBatch(List<EntityInfo> entityInfo);
	
	public int updateDBEntityByKey(EntityInfo entityInfo);
	
	public int deleteDBEntityByKey(EntityInfo entityInfo);
	
	public int deleteDBEntity(EntityInfo entityInfo);

	public Page<Map<String, Object>> queryDBEntity(EntityInfo info);

}
