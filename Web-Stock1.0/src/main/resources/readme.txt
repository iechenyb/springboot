1 注册 
 http://localhost:8080/users/signup
 请求数据  raw
 {
    "username": "admin",
    "password": "password"
}

2 默认登录login 自定义登录 users/login
 http://localhost:8080/login
  请求参赛  raw
 {
    "username": "admin",
    "password": "password"
}

3 访问url 
http://localhost:8080/home  admin和user都能访问
http://localhost:8080/admin 只有admin能够访问
http://localhost:8080/user 只有user能够访问
4 http://localhost:8080/exit 退出系统
1.1 mysql
1.2 h2


1.4自定义资源访问策略：
http://localhost/need/needOtherPremission.jsp  无权限  
http://localhost/need/needAdminPremission.jsp admin
http://localhost/common/toPage1 admin
http://localhost/common/toPage2 user
http://localhost/common/toPage3  需要other权限无 


打war包时，需要启动h2服务！


遗留问题：
hibernate 的更新操作和更新或者保存操作不能显示sql并提交事务。
druid数据连接池有不释放的连接，知道具体的类，但是不知道产生原因。

***********新增jsr303校验******
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-validation</artifactId>
</dependency> 

自定义 endpoint

********开启h2-console
http://localhost:8080/stock/h2-console
*******接口管理
http://localhost:8080/stock/swagger-ui.html
*******登录日志 在线人数统计

*******添加访问限制    ！！！ redis+expire
*******新增MyResponseBodyAdvice  进行时间统计
