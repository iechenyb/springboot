package com.cyb.app.sk.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyb.app.sk.dao.GoodsRepository;
import com.cyb.app.sk.service.GoodsService;
/**
 *作者 : iechenyb<br>
 *类描述: db中操作库存<br>
 *创建时间: 2018年4月26日
 */
@Service("goodsServiceImpl")
public class GoodsServiceImpl implements GoodsService{
	Log log = LogFactory.getLog(GoodsServiceImpl.class);
	
	@Autowired
    GoodsRepository goodsDao;
	
	public void initStock(String goodsName, int stock) {
		goodsDao.updateGoods(goodsName,stock);
	}

	public boolean decStock(String goodsName, int buys) {
		if(goodsDao.decGoods(goodsName, buys)>0) {
			return true;
		}else{
			return false;
		}
	}

	public int queryStock(String goodsName) {
		return goodsDao.queryGoodsStock(goodsName).getStock();
	}
}
