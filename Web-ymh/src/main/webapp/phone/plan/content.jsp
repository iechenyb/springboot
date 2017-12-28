<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="zh-cn">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>计划内容</title>
    <meta name="renderer" content="webkit">
    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <link rel="alternate icon" type="image/png" href="i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="AMUI React">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath %>static/css/amazeui.min.css">
    <style type="text/css">
        li{font-size: 20px;margin-left:0px;}
        ul{font-size: 30px;}
    </style>
</head>
<body>
<!--header-->
<header data-am-widget="header" class="am-header am-header-default">
    <div class="am-header-left am-header-nav">
        <a href="<%=basePath%>phone/plan/index.jsp" class="">
            <i class="am-header-icon am-icon-home"></i>
        </a>
    </div>
    <h1 class="am-header-title">
        <a href="#title-link" class="">
            蓝海密剑${username}
        </a>
    </h1>
    <div class="am-header-right am-header-nav">
        <a href="#right-link" class="" data-am-offcanvas="{target: '#doc-oc-demo3'}">
            <i class="am-header-icon am-icon-bars"></i>
        </a>
    </div>
</header>
<!-- 侧边栏内容 -->
<div id="doc-oc-demo3" class="am-offcanvas">
    <div class="am-offcanvas-bar am-offcanvas-bar-flip">
        <div class="am-offcanvas-content">
             <ul>重庆
              <c:forEach items="${t1}" var="tmp">  
                <li>
	                 <a href="<%=basePath%>plan/getPlan?jhbh=<c:out value="${tmp.jhbh}" />&jhlx=<c:out value="${tmp.jhlx}" />"> 
	               		<c:out value="${tmp.jhmc}" /> 
	                </a>
               </li>
               </c:forEach>
            </ul>
            <ul>PK10
               <c:forEach items="${t2}" var="tmp">  
                <li>
	                 <a href="<%=basePath%>plan/getPlan?jhbh=<c:out value="${tmp.jhbh}" />&jhlx=<c:out value="${tmp.jhlx}" />"> 
	               		<c:out value="${tmp.jhmc}" /> 
	                </a>
               </li>
               </c:forEach>
            </ul>
        </div>
    </div>
</div>
<section data-am-widget="accordion" class="am-accordion am-accordion-gapped" data-am-accordion='{  }'>
    <c:forEach items="${data}" var="t">  
    <dl class="am-accordion-item am-active">
        <dt class="am-accordion-title">
            <img src="<%=basePath %>phone/plan/1.jpg"/> <c:out value="${t.jhlx}" ></c:out> 
        </dt>
        <dd class="am-accordion-bd am-collapse am-in">
            <div class="am-accordion-content" id="1234">
            	<c:out value="${t.content}" escapeXml="false"> </c:out> 
            </div>
        </dd>
    </dl>
    </c:forEach>
</section> 
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default "
     id="">
    <ul class="am-navbar-nav am-cf am-avg-sm-4">
        <li >
            <a href="tel:123456789" class="">
                <span class="am-icon-phone"></span>
                <span class="am-navbar-label">呼叫</span>
            </a>
        </li>
        <li data-am-navbar-share>
            <a href="###" class="">
                <span class="am-icon-share-square-o"></span>
                <span class="am-navbar-label">分享</span>
            </a>
        </li>
        <li data-am-navbar-qrcode>
            <a href="###" class="">
                <span class="am-icon-qrcode"></span>
                <span class="am-navbar-label">二维码</span>
            </a>
        </li>
        <%-- <li >
            <a href="https://github.com/allmobilize/amazeui" class="">
                <span class="am-icon-github"></span>
                <span class="am-navbar-label">GitHub</span>
            </a>
        </li>
        <li >
            <a href="<%=basePath%>phone/plan/login.jsp" class="">
                <span class="am-icon-location-arrow"></span>
                <span class="am-navbar-label">登录</span>
            </a>
        </li> --%>
        <li >
           <a href="<%=basePath%>users/logout" class="">
                <span class="am-icon-download"></span>
                <span class="am-navbar-label">退出</span>
            </a>
        </li>
    </ul>
</div>
</body>
<script src="<%=basePath %>static/js/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
<script src="<%=basePath %>static/js/amazeui.widgets.helper.min.js"></script>
<script type="text/javascript" src="jquery.zclip.min.js"></script>
<script type="text/javascript">
    $(function() {
        $("#cp-btn").zclip({
            path: 'ZeroClipboard.swf', //è®°å¾æZeroClipboard.swfå¼å¥å°é¡¹ç®ä¸­
            copy: function () {
                alert($('#inviteUrl').val());
                return $('#inviteUrl').val();
            }
        });
    });
</script>
</html>