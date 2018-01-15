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


