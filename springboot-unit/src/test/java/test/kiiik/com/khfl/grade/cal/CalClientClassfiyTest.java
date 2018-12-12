package test.kiiik.com.khfl.grade.cal;

import java.util.Calendar;
import java.util.Random;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.kiiik.web.khfl.reportCaculate.utils.DateSectionUtils;
import com.kiiik.web.khfl.reportCaculate.vo.CalDate;
import com.kiiik.web.khfl.reportCaculate.vo.CycleCalVO;

import test.kiiik.com.pub.BaseUnit;
/**
 * 
 * @author chenyuanbao
 * 客户计算用例测试
 *
 */
public class CalClientClassfiyTest extends BaseUnit{
	
	@Test
	public void testFixedCircle() throws Exception{
		String uri = "/dataCal/calFixedCycle";
		//推算月份
		System.out.println("推算月份");
		for (int i = 1; i <= 12; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(1);
			vo.setVal(String.valueOf(i));
			//exePost(uri, JSON.toJSONString(vo));
		}
		System.out.println("推算季度");
		for (int i = 1; i <= 4; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(2);
			vo.setVal(String.valueOf(i));
			//exePost(uri, JSON.toJSONString(vo));
		}
		System.out.println("推算半年");
		for (int i = 1; i <= 2; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(3);
			vo.setVal(String.valueOf(i));
			exePost(uri, JSON.toJSONString(vo));
		}
		System.out.println("按照年份推算上一年");
		for (int i = 1; i <= 1; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(4);
			vo.setVal(String.valueOf(i));
			exePost(uri, JSON.toJSONString(vo));
		}
		while(true){}//先不退出
	}
	@Test
    public void testDisableAllTask(){
		
	}
	
	@Test
	public void testNotFixedCircle() throws Exception{
		String uri = "/dataCal/calFixedCycle";
		CycleCalVO vo = new CycleCalVO();
		vo.setType(0);//固定周期 后端设置
		CalDate cd = new CalDate();
		cd.setBeginDate("20171001");//起始时间为默认有的时间值
		for(int i=1;i<5;){
			int months = new Random().nextInt(12-3+1)+3;
			DateSectionUtils.calendar(cd.getBeginDate()).add(Calendar.MONTH,months );//12个月
			exeAjaxRq(uri, JSON.toJSONString(vo));
		}
	}
	@Test
	public void testCalDate() throws Exception{
		//推算月份
		System.out.println("推算月份");
		for (int i = 1; i <= 12; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(1);
			vo.setVal(String.valueOf(i));
			exeAjaxRq("/dataCal/lastCycleSection", JSON.toJSONString(vo));
		}
		System.out.println("推算季度");
		for (int i = 1; i <= 4; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(2);
			vo.setVal(String.valueOf(i));
			exeAjaxRq("/dataCal/lastCycleSection", JSON.toJSONString(vo));
		}
		System.out.println("推算半年");
		for (int i = 1; i <= 2; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(3);
			vo.setVal(String.valueOf(i));
			exeAjaxRq("/dataCal/lastCycleSection", JSON.toJSONString(vo));
		}
		System.out.println("按照年份推算上一年");
		for (int i = 1; i <= 1; i++) {
			CycleCalVO vo = new CycleCalVO();
			vo.setYear(String.valueOf(2018));
			vo.setType(4);
			vo.setVal(String.valueOf(i));
			exeAjaxRq("/dataCal/lastCycleSection", JSON.toJSONString(vo));
		}
	}
	
	@Test
	public void testGetID() throws Exception{
		/*ClassifyCalLogEntity log = new ClassifyCalLogEntity();
		log.setCircleType(1);
		log.setNote("test");
		log.setOperator("system");
		log.setYear(2018);
		log.setCircleType(1);
		log.setValue(1);
		genericService.insertDBEntity(log);
		System.out.println("获取刚刚插入的主键："+log.getId());*/
		
	}

}
