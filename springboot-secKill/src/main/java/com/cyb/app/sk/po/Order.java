package com.cyb.app.sk.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 作者 : iechenyb<br>
 * 类描述: 订单信息<br>
 * 创建时间: 2018年4月26日
 */
@Entity
@Table(name = "t_sk_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	
	@Column
	private long userId;// 用户id

	@Column
	private String goodsName;// 商品id

	@Column
	private long buys;// 购买数量

	@Column
	private long orderTime;// 下单时间

	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public long getBuys() {
		return buys;
	}

	public void setBuys(long buys) {
		this.buys = buys;
	}

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}

}
