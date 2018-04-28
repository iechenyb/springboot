package com.cyb.app;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cyb.UUIDUtils;
import com.cyb.app.sk.dao.GoodsRepository;
import com.cyb.app.sk.dao.UserRepository;
import com.cyb.app.sk.po.Goods;
import com.cyb.app.sk.po.User;
import com.cyb.app.sk.service.GoodsService;
import com.cyb.app.sk.service.OrderService;
import com.cyb.app.sk.service.UserService;
import com.cyb.file.FileUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(properties="application.properties")
public class UserTests {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private UserService userService;
	
	//@Autowired
	@Resource(name="redisGoodsServiceImpl")
	private GoodsService goodsService;
	
	//@Autowired
	@Resource(name="redisOrderServiceImpl")
	private OrderService orderService;
	
	
	int userNum = 100;
	
	String userFilePath="d:/data/miaosha/users.txt";
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 生成模拟用户<br>
	 *创建时间: 2017年7月15日hj12
	 */
	@Test
	public void genUsers() {
		userRepository.deleteAll();//情况原有记录
		File file = new File(userFilePath);
		if(file.exists()){
			file.delete();
		}
		FileUtils.genFileDir(file.getParent());//仅仅创建目录
		for(int i=0;i<userNum;i++){
			User user_ = new User();
			user_.setAddress(String.valueOf(i+userNum));
			user_.setSex(String.valueOf(i%2==0?0:1));
			user_.setUsername(String.valueOf(userNum+i));
			user_.setPassword("123456");
			user_.setToken(UUIDUtils.getUUID());
			FileUtils.appendString2File(user_.getToken()+","+user_.getUsername()+"\n", file.getAbsolutePath());
			//userRepository.save(user_);
		}
	}
	
	@Test
	public void makeOrder(){
		 boolean result = orderService.makeOrder("iphoneX", 10L, 10);
		 System.out.println("是否下单成功："+result);
	}
	@Test
	public void decGoodsStock(){
		goodsRepository.decGoods("iphoneX", 10);
	}
	
	@Test
	public void initGoods(){
		Goods goods = new Goods();
		goods.setGoodsName("iphoneX");
		goods.setStock(10);
		goodsRepository.save(goods);
	}
	
	@Test
	public void initRedisGoods(){
		Goods goods = new Goods();
		goods.setGoodsName("iphoneX");
		goods.setStock(10);
		goodsService.initStock(goods.getGoodsName(), goods.getStock());
	}
	
	
	@Test
	public void updateGoods(){
		Goods goods = new Goods();
		goods.setId(1L);
		goods.setGoodsName("iphoneX");
		goods.setStock(1);
		System.out.println("更新记录数："+goodsRepository.updateGoods("iphoneX", 1));
	}
}
