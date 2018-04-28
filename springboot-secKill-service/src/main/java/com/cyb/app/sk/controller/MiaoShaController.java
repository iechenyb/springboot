package com.cyb.app.sk.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.app.publish.ResultBean;
import com.cyb.app.sk.service.GoodsService;
import com.cyb.app.sk.service.OrderService;
import com.cyb.app.sk.service.UserService;
import com.cyb.app.sk.service.impl.OptimisticLockService;

@RestController
@RequestMapping("/sk")
public class MiaoShaController {
	Log log = LogFactory.getLog(MiaoShaController.class);

	// @Autowired
	@Resource(name = "redisGoodsServiceImpl")
	GoodsService goodsService;

	// @Autowired
	@Resource(name = "redisOrderServiceImpl")
	OrderService orderService;

	@Autowired
	UserService userService;

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 可以通过联合主键控制用户重复下单问题<br>
	 * 创建时间: 2017年7月15日hj12
	 * 
	 * @param userId
	 * @param goodsName
	 * @param buys
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/buy")
	public ResultBean<Map<String, Object>> getId(String token, long userId, String goodsName, int buys,HttpServletRequest request) throws Exception {
		// 查询当前人员是否存在,可以将token进行索引创建
		/*
		 * User user = userService.queryUserById(token);
		 * 
		 * if(user==null){ return new ResultBean<String>().data("用户不存在").fail();
		 * }
		 */
		int stock = goodsService.queryStock(goodsName);
		// 查询库存，如果库存大于则进入下单逻辑
		if (stock < buys) {
			return new ResultBean<String>().success("商品[" + goodsName + "]已经售完！");
		}
		// 下单时查询是否已经下单，已经下单不能重复下单
		/*
		 * if(orderService.hasMakeOrder(goodsName, user.getId())){ return new
		 * ResultBean<String>().data("你已经秒杀商品["+goodsName+"]成功！").fail(); }
		 */
		// 开始下单,新增订单信息并且修改库存
		boolean buyRS = orderService.makeOrder(goodsName, userId, buys,request.getServerPort());
		Map<String, Object> res =  getServerInfor(request);
		res.put("购买成功标志", buyRS==true?"成功":"失败");
		//userId + "购买数量" + buys + ",是否成功购买" + buyRS + "！"
		res.put("购买数量",buys);
		res.put("商品名称", goodsName);
		res.put("人员ID", userId);
		return new ResultBean<Map<String, Object>>().data(res).success();
	}

	@Autowired
	OptimisticLockService optimisticLockService;

	// http://localhost:8080/sk/initStock?goodsName=iphone&prdNum=3
	@SuppressWarnings("unchecked")
	@GetMapping("/initStock")
	public ResultBean<Map<String, Object>> initGoods(String goodsName, int prdNum,HttpServletRequest request) {
		optimisticLockService.initPrduct(goodsName, prdNum);
		Map<String, Object> res =  getServerInfor(request);
		res.put("stock", optimisticLockService.readPrduct(goodsName));
		return new ResultBean<Map<String, Object>>().success("初始化信息").data(res);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/readResult")
	public ResultBean<Map<String, Object>> result(String goodsName,HttpServletRequest request) {
		Map<String, Object> res =  getServerInfor(request);
		res.put("stock", optimisticLockService.readPrduct(goodsName));
		return new ResultBean<Map<String, Object>>().success("库存信息").data(res);
	}
	
	public Map<String, Object> getServerInfor(HttpServletRequest request){
		Map<String, Object> rs = new LinkedHashMap<String,Object>();
		rs.put("server port",request.getServerPort() );
		rs.put("server ip", request.getServletPath());
		return rs;
	}
}
