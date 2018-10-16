package com.aiyi.core.util.hibernate.jdbc.util;

/*    */ 
/*    */ import java.util.StringTokenizer;
/*    */ import junit.framework.TestCase;
/*    */ 
/*    */ public class BasicFormatterTest extends TestCase
/*    */ {
/*    */   public BasicFormatterTest(String name)
/*    */   {
/* 29 */     super(name);
/*    */   }
/*    */ 
/*    */   public void testNoLoss() {
/* 33 */     assertNoLoss("insert into Address (city, state, zip, \"from\") values (?, ?, ?, 'insert value')");
/* 34 */     assertNoLoss("delete from Address where id = ? and version = ?");
/* 35 */     assertNoLoss("update Address set city = ?, state=?, zip=?, version = ? where id = ? and version = ?");
/* 36 */     assertNoLoss("update Address set city = ?, state=?, zip=?, version = ? where id in (select aid from Person)");
/* 37 */     assertNoLoss(
/* 38 */       "select p.name, a.zipCode, count(*) from Person p left outer join Employee e on e.id = p.id and p.type = 'E' and (e.effective>? or e.effective<?) join Address a on a.pid = p.id where upper(p.name) like 'G%' and p.age > 100 and (p.sex = 'M' or p.sex = 'F') and coalesce( trim(a.street), a.city, (a.zip) ) is not null order by p.name asc, a.zipCode asc");
/*    */ 
/* 40 */     assertNoLoss(
/* 41 */       "select ( (m.age - p.age) * 12 ), trim(upper(p.name)) from Person p, Person m where p.mother = m.id and ( p.age = (select max(p0.age) from Person p0 where (p0.mother=m.id)) and p.name like ? )");
/*    */ 
/* 43 */     assertNoLoss(
/* 44 */       "select * from Address a join Person p on a.pid = p.id, Person m join Address b on b.pid = m.id where p.mother = m.id and p.name like ?");
/*    */ 
/* 46 */     assertNoLoss(
/* 47 */       "select case when p.age > 50 then 'old' when p.age > 18 then 'adult' else 'child' end from Person p where ( case when p.age > 50 then 'old' when p.age > 18 then 'adult' else 'child' end ) like ?");
/*    */ 
/* 49 */     assertNoLoss(
/* 50 */       "/* Here we' go! */ select case when p.age > 50 then 'old' when p.age > 18 then 'adult' else 'child' end from Person p where ( case when p.age > 50 then 'old' when p.age > 18 then 'adult' else 'child' end ) like ?");
/*    */   }
/*    */ 
/*    */   private void assertNoLoss(String query)
/*    */   {
/* 55 */     String formattedQuery = FormatStyle.BASIC.getFormatter().format(query);
/* 56 */     StringTokenizer formatted = new StringTokenizer(formattedQuery, " \t\n\r\f()");
/* 57 */     StringTokenizer plain = new StringTokenizer(query, " \t\n\r\f()");
/*    */ 
/* 59 */     System.out.println("Original: " + query);
/* 60 */     System.out.println("Formatted: " + formattedQuery);
/* 61 */     while ((formatted.hasMoreTokens()) && (plain.hasMoreTokens())) {
/* 62 */       String plainToken = plain.nextToken();
/* 63 */       String formattedToken = formatted.nextToken();
/* 64 */       assertEquals("formatter did not return the same token", plainToken, formattedToken);
/*    */     }
/* 66 */     assertFalse(formatted.hasMoreTokens());
/* 67 */     assertFalse(plain.hasMoreTokens());
/*    */   }
/*    */ }

/* Location:           C:\Users\AnLuTong\Desktop\
 * Qualified Name:     org.hibernate.jdbc.util.BasicFormatterTest
 * JD-Core Version:    0.6.2
 */