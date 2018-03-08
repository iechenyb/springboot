<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>404状态页面</title>
<meta name="keywords" content="404">
<meta name="description" content="404状态页面">
<meta name="author" content="">
<style type="text/css">
body {margin:0;padding:0;font-size:14px;line-height:1.231;color:#555;text-align:center;font-family:"\5fae\8f6f\96c5\9ed1","\9ed1\4f53",tahoma,arial,sans-serif;}
/* body
{
    background: url('../../exception/404.jpg') top center no-repeat; 
    background-size:cover;
} */
.bg{
 background: url('../../exception/404.jpg') top center no-repeat; 
 background-size:cover;
 height:600px;
}
a {color:#555;text-decoration:none;}
a:hover {color:#1abc9c;}
#container {width:80%;height:500px;margin:100px auto 0px auto;border:#2c3e50 solid 6px;background-color:#2c3e50;}
#container #title {overflow:hidden; padding-top:30px;}
#container #title h1 {font-size:36px;text-align:center;color:#FFFFFF;}
#content p{ font-size:18px;height:80px;}
#footer {width:100%;padding:20px 0px;font-size:16px;color:#555;text-align:center;}
</style>
</head>

<body>
<!-- <div id="container">
<div id="title"><h1>请求地址不合法!</h1></div>
<div id="content">
<p><a href="javascript:history.go(-1)" style="color:#F00">尝试返回上一页</a></p>
<br />
<p style="font-size:24px;font-weight:bold;color:#1abc9c">404状态页面</p>

</div>
</div> -->
<div style="height:20rem">&nbsp;</div>
<div class="am-g am-g-fixed " >
  <div class="am-u-sm-12"><h1>{页面丢失} 服务器拒绝请求!</h1></div>
  <div class="am-u-sm-12"><a href="javascript:history.go(-1)" style="color:#F00;font-size:24px;">尝试返回上一页</a></div>
  <div class="am-u-sm-12"><p style="font-size:48px;font-weight:bold;color:#1abc9c">404状态页面</p></div>
  <div class="am-u-sm-12"><a href="<%=basePath%>phone/plan2/login.jsp" style="color:#1abc9c;font-size:24px;">重新登陆</a></div>
</div>
</body>
</html>