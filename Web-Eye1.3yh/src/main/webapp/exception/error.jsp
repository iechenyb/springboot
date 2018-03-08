<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>提示页面</title>
</head>
<body>
  操作异常，请重试！<br>
  可能出现的问题如下：<br>
  你的登录信息已经过期!<br>
  出现非法操作!<br>
  登录失败!<br>
  <a href="<%=basePath%>/phone/plan/login.jsp">重新登陆</a>
</body>
</html>