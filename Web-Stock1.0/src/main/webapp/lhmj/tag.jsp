<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>security标签使用！</title>
</head>
<body>
security标签使用！<br>
<hr>
<sec:authentication property="name"/>
	<sec:authentication property="principal.username"></sec:authentication> 
 <%-- <sec:authorize ifAllGranted="ROLE_ADMIN">  
 		<sec:authentication property="principal.username"></sec:authentication>   
    </sec:authorize> --%>
    ${session.SPRING_SECURITY_CONTEXT.authentication.principal.username} 
	<sec:authorize access="hasRole('ADMIN')">
		<div>
			<!-- 3 -->
			<p>只有管理员可以看到！</p>
		</div>
	</sec:authorize>
	<sec:authorize access="hasRole('USER')">
		<div>
			<!-- 3ROLE_ -->
			<p class="bg-info">只有USER角色的人员才能看到</p>
		</div>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER)">
		<div>
			<p class="bg-info">所以用户都能看到</p>
		</div>
	</sec:authorize>
	   <sec:authorize url="/admin.jsp">
      <a href="admin.jsp">admin</a>
   </sec:authorize>
   
<a href="tencent://message/?uin=466074875&Site=QQ交谈&Menu=yes" target="blank"><img border="0" src="http://wpa.qq.com/pa?p=1:你的QQ号:7" alt="图片不正常时显示的文字" width="71" height="24" />123</a>
</body>
</html>