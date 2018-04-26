package com.cyb.app.sk.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyb.app.publish.ResultBean;
import com.cyb.app.sk.po.User;
import com.cyb.app.sk.service.GoodsService;
import com.cyb.app.sk.service.OrderService;
import com.cyb.app.sk.service.UserService;

@RestController
@RequestMapping("/sk")
public class MiaoShaController {
  Log log = LogFactory.getLog(MiaoShaController.class);
  
  //@Autowired
  @Resource(name="redisGoodsServiceImpl")
  GoodsService goodsService;
  
  //@Autowired
  @Resource(name="redisOrderServiceImpl")
  OrderService orderService;
  
  @Autowired
  UserService userService;
  /**
   * 
   *作者 : iechenyb<br>
   *方法描述: 可以通过联合主键控制用户重复下单问题<br>
   *创建时间: 2017年7月15日hj12
   *@param userId
   *@param goodsName
   *@param buys
   *@return
   *@throws Exception
   */
  @SuppressWarnings("unchecked")
@RequestMapping("/order1")
  public ResultBean<String> getId(String token,String goodsName,int buys) throws Exception {
	  
	  //查询当前人员是否存在,可以将token进行索引创建
	  User user = userService.queryUserById(token);
	  
	  if(user==null){
		 return new ResultBean<String>().data("用户不存在").fail();
	  }
	  int stock  = goodsService.queryStock(goodsName);
	  //查询库存，如果库存大于则进入下单逻辑
	  if(stock<buys){
		  return new ResultBean<String>().data("商品["+goodsName+"]已经售完！").fail();
	  }
	  //下单时查询是否已经下单，已经下单不能重复下单
	  if(orderService.hasMakeOrder(goodsName, user.getId())){
		  return new ResultBean<String>().data("你已经秒杀商品["+goodsName+"]成功！").fail();
	  }
	  //开始下单,新增订单信息并且修改库存
	  orderService.makeOrder(goodsName, user.getId(), buys);
	  return new ResultBean<String>().data("购买成功！").fail();
  }
}
