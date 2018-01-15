<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>登录</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="<%=basePath %>static/css/amazeui.min.css">
  <link rel="stylesheet" href="<%=basePath%>/phone/plan/app.css">
</head>
<body>
<div class="am-g">
	<!-- LOGO -->
	<div class="am-u-sm-12 am-text-center" >
		 <i class="am-icon-twitch myapp-login-logo"></i>
	</div>
	<!-- 登陆框 -->
	<div class="am-u-sm-11 am-u-sm-centered">
	<form class="am-form" action="<%=basePath%>users/login" method="post">
	  <fieldset class="myapp-login-form am-form-set">
		<div class="am-form-group am-form-icon">
			<i class="am-icon-user"></i>
			<input type="text"  name="username" class="myapp-login-input-text am-form-field" placeholder="请输入您的账号">
		</div>
	    <div class="am-form-group am-form-icon">
			<i class="am-icon-lock"></i>
			<input type="text" name="password" class="myapp-login-input-text am-form-field" placeholder="至少6个字符">
		</div>
	  </fieldset>
	  ${msg}
	  <button type="submit" class="am-btn am-btn-primary am-btn-block ">登 陆</button>
	</form>
	</div>
</div>

<script src="<%=basePath%>static/js/jquery.min.js"></script>
<script src="<%=basePath%>/phone/plan/app.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/amazeui.min.js"></script>
</body>
</html>