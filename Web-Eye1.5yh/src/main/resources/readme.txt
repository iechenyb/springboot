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

与1.2的主要区别：1.3新增分布式session共享！
nginx做负载均衡和反向代理
登录密码安全加密
1.4复制异常，直接升级1.5版本
与1.3的主要区别：1.5新增集成cas单点登录认证！

与nginx做负载均衡时，重定向casserver时，因为是走nginx反向代理，出现跨域问题，导致负载均衡不能正常使用！
考虑将casserver作负载均衡