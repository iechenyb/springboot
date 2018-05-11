package com.cyb.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cyb.rule.ResultBean;
import com.cyb.rule.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
 * @author iechenyb
 * 根据实际环境选择最佳的服务方
 * http://localhost:8884/service-ribbon/m1?a=3&b=2 通过路由进行负载均衡
 * http://localhost:8884/service-ribbon/m2?a=3&b=2 手动设置调用规则
 * 无法进行请求时间添加
 */
@RestController
@Api(tags="miaosha controller rest controller")
@RequestMapping("miaosha2")
public class SecKillController2 {
    Log log = LogFactory.getLog(getClass());
    
    String serviceId="MIAOSHA-SERVICE";
    @Autowired
    private RestTemplate restTemplate;
  
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 名称为SERVICE-B的服务有两个，但是服务名称都为SERVICE-B，
     *另有一个Service-B2工程启动时服务名称也为SERVICE-B,默认采用轮询的方式，
     *所以返回值会在serviceb和servcieb2之间来回切换<br>
     *创建时间: 2017年7月15日hj12
     *@param a
     *@param b
     *@return
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "秒杀接口描述",notes="秒杀接口notes")
    //(tags="miao sha interface",value="秒杀接口描述")//会与controller同层级展示
	@PostMapping(value = "/buys")
    public  ResultBean<Map<String, Object>> miaosha(String token, long userId, String goodsName, int buys,HttpServletRequest req) {
    	//http://localhost:8881/sk/buy?token=1&userId=1&goodsName=1&buys=1 
    	String uri = "/sk/buy?token="+token+"&userId="+userId+"&goodsName="+goodsName+"&buys="+buys;
        return restTemplate.getForEntity("http://"+serviceId+uri, ResultBean.class).getBody();
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("/initStock")
    @ApiOperation(value = "初始化库存")
   /* @ApiImplicitParams({
    	@ApiImplicitParam(value="商品名称",dataType="String",required=true,paramType="query"),
    	@ApiImplicitParam(value="商品数量",dataType="int",required=true,paramType="query"),	
    })*/
	public ResultBean<Map<String, Object>> initGoods(
			@ApiParam(value="商品名称")  @RequestParam String goodsName
			,@ApiParam(value="商品数量")  @RequestParam int prdNum) {
    	String uri = "/sk/initStock?goodsName="+goodsName+"&prdNum="+prdNum;
    	return restTemplate.getForEntity("http://"+serviceId+uri, ResultBean.class).getBody();
	}
    
	@SuppressWarnings("unchecked")
	@GetMapping("/readResult")
    @ApiOperation(value = "读取库存和销售量")
	public ResultBean<Map<String, Object>> result(
			@ApiParam(value="商品名称") @RequestParam String goodsName,HttpServletRequest request) {
    	String uri = "/sk/readResult?goodsName="+goodsName;
    	UserVO user = new UserVO();
    	ResultBean<Map<String, Object>> res = restTemplate.getForEntity("http://"+serviceId+uri, ResultBean.class).getBody();
    	res.getData().put("user", user);
    	return res;
	}
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 测试返回普通的vo ，并进行result包装<br>
	 *创建时间: 2017年7月15日hj12
	 *@param goodsName
	 *@param request
	 *@return
	 */
	@GetMapping("/readVo")
    @ApiOperation(value = "读取库存和销售量")
	public UserVO readVo(
			@ApiParam(value="商品名称") @RequestParam String goodsName,HttpServletRequest request) {
        UserVO vo = new UserVO();
        vo.setName(goodsName);
    	return vo;
	}
	
	
    
}