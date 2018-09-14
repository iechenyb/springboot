package com.kiiik.vas.base.dao;

import java.util.ArrayList;
import java.util.List;

import com.kiiik.vas.base.vo.MainContract;
/**
 * 
 *作者 : iechenyb<br>
 *类描述: 基础数据接口<br>
 *创建时间: 2018年5月18日 08:50:24
 */
public interface BaseDataDao {
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 查询最新的主力合约信息<br>
	 *创建时间: 2018年5月18日 08:50:20
	 *@return
	 */
	List<MainContract> getNewlyMainContract();
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 查询所有的合约代码信息<br>
	 *创建时间: 2018年5月18日 08:50:16
	 *@return
	 */
	List<String> getAllContract();
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 查询期货会员公司信息<br>
	 *创建时间: 2018年5月18日 08:50:12
	 *@return
	 */
	List<String> getCompanyList();
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 查询最近days个交易日数据<br>
	 *创建时间: 2018年5月24日上午9:11:17
	 *@param days 系统配置好天数
	 *@return
	 */
	ArrayList<String> getTransDaysList(String days);
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据天数倒推指定日期<br>
	 *创建时间: 2018年7月5日下午3:03:42
	 *@param v
	 *@return
	 */
	String getDayByV(String v);
}
