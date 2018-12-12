package test.kiiik.com.khfl.grade.cal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kiiik.web.khfl.reportCaculate.bean.KhflMidDateEntity;
import com.kiiik.web.khfl.reportCaculate.service.impl.KhflMidDataCalSericeImpl;

import test.kiiik.com.pub.BaseUnit;
/**
 * 中间数据计算
 * @author chenyuanbao
 *
 */
public class KhflMidDataCalTeat extends BaseUnit{
	
	@Autowired
    KhflMidDataCalSericeImpl service;
	
	/**
	 * 计算所有人员所有的交易记录中间值
	 */
	@Test
	public void calAll(){
		service.calMidData();
	}
	
	/**
	 * 计算某一天的累计值
	 */
	@Test
	public void calSomeDay(){
		service.calMidData("20171012");
	}
	/**
	 * 计算某一天起所有的累计值
	 */
	@Test
	public void calMidDataFromSomeDay(){
		service.calMidDataFromSomeDay("20180105");
	}
	
	@Test
	public void testSelect(){
		genericService.queryDBEntityList(new KhflMidDateEntity());
	}
}
