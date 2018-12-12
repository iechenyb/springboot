package test.kiiik.com.khfl.grade.cal;

import org.junit.Test;

import com.kiiik.web.khfl.reportCaculate.vo.CalDate;

import test.kiiik.com.pub.BaseUnit;

public class RequestTest extends BaseUnit{
   CalDate date = new CalDate("aaa","bbbb");
   @Test
   public void testGet() throws Exception{
	   exeAjaxRq("/api/query?name=chenyb", null,GET);
   }
   @Test
   public void testDelete() throws Exception{
	   exeAjaxRq("/api/delete?name=chenyb", null,DELETE);
   }
   @Test
   public void testPut() throws Exception{
	   exeAjaxRq("/api/update?name=chenyb", date,PUT);
   }
   @Test
   public void testPost() throws Exception{
	   exeAjaxRq("/api/add?name=chenyb", date,POST);
   }
   
}
