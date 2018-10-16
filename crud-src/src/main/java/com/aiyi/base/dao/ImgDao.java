package com.aiyi.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.aiyi.base.pojo.TestImgResourcePo;
import com.aiyi.core.dao.impl.DaoImpl;

/**
 * ImgDao的实现类，该类继承了公共Dao的原始方法。当你要给爱易REUD框架增加新功能时，不用去改源码，爱易CRUD提供了可编程式插件接口的方案。
 * 你可以编写一个接口类并实现他，以此来扩展爱易CRUD
 * @author 郭胜凯
 * @time 2016年6月28日下午12:52:06
 * @email 719348277@qq.com
 *
 */
@Repository
public class ImgDao extends DaoImpl<TestImgResourcePo, Integer> implements ImgDaoInterface /*ImgDaoInterface这个接口是一个插件实现的演示。他完善了爱易CRUD的批量添加方案*/ {

	@Resource
	private SqlSessionTemplate sqlSessionTemplateASS;
	
	/**
	 * 重载Add方法，批量添加
	 * @param list
	 * @return
	 */
	public int add(List<TestImgResourcePo> list){
		
		long startId = nextId();
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setId(Integer.valueOf(startId + i + ""));
		}
		
		return sqlSessionTemplateASS.insert("img_addList", list);
	}
}
