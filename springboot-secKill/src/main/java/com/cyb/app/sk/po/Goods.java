package com.cyb.app.sk.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 商品信息
 * @author DHUser
 */
@Entity  
@Table(name="t_sk_goods")  
public class Goods {  
	@Column
    private Long id ;  
	
	@Column
    private String goodsName;//商品名称
	
	@Column
    private int stock;//库存  
    
    @Id  
    @GeneratedValue(strategy=GenerationType.AUTO)  
    @Column(name="id")  
    public Long getId() {  
        return id;  
    }  
    public void setId(Long id) {  
        this.id = id;  
    }
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}  
}  