<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head lang="zh-cn">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>首页<%-- <sec:authentication property="name"/>
	<sec:authentication property="principal.username"></sec:authentication>  --%></title>
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
        <a href="<%=basePath%>plan/index" class="">
            <i class="am-header-icon am-icon-home"></i>
        </a>
    </div>
    <h1 class="am-header-title">
        <a href="#" class="">
            欢迎你！${username}
        </a>
    </h1>
    <div class="am-header-right am-header-nav">
        <a href="#right-link" class="" data-am-offcanvas="{target: '#doc-oc-demo3'}">
            <i class="am-header-icon am-icon-bars"></i>
        </a>
    </div>
</header>
<div data-am-widget="slider" class="am-slider am-slider-default"
     data-am-slider='{&quot;animation&quot;:&quot;slide&quot;,&quot;slideshow&quot;:false}' >
    <ul class="am-slides">
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-1.jpg">
            <div class="am-slider-desc">这是标题标题标题标题标题标题标题0</div>

        </li>
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-2.jpg">
            <div class="am-slider-desc">这是标题标题标题标题标题标题标题1</div>
        </li>
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-3.jpg">
            <div class="am-slider-desc">这是标题标题标题标题标题标题标题2</div>

        </li>
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-4.jpg">
            <div class="am-slider-desc">这是标题标题标题标题标题标题标题3</div>

        </li>
    </ul>
</div>
<!-- 侧边栏内容 -->
<div id="doc-oc-demo3" class="am-offcanvas">
    <div class="am-offcanvas-bar am-offcanvas-bar-flip">
        <div class="am-offcanvas-content">
			<ul><img src="<%=basePath%>phone/plan/sscb.png"></img>重庆
              <c:forEach items="${t1}" var="tmp">  
                <li>
	                 <a href="<%=basePath%>plan/getPlan?jhbh=<c:out value="${tmp.jhbh}" />&jhlx=<c:out value="${tmp.jhlx}" />"> 
	               		<c:out value="${tmp.jhmc}" /> 
	                </a>
               </li>
               </c:forEach>
            </ul>
            <ul><img src="<%=basePath%>phone/plan/PK10b.png"></img>PK10
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
    <dl class="am-accordion-item am-active">
        <dt class="am-accordion-title">
            <img src="<%=basePath%>phone/plan/1.jpg"/>就这样恣意的活着
        </dt>
        <dd class="am-accordion-bd am-collapse am-in">
            <!-- 规避 Collapase 处理有 padding 的折叠内容计算计算有误问题， 加一个容器 -->
            <div class="am-accordion-content">
                置身人群中 <br/> 你只需要被淹没 享受 沉默 <br/> 退到人群后 <br/> 你只需给予双手 微笑 等候
            </div>
        </dd>
    </dl>
    <dl class="am-accordion-item">
        <dt class="am-accordion-title">
            <img src="<%=basePath%>phone/plan/1.jpg"/>让生命去等候，去等候，去等候，去等候
        </dt>
        <dd class="am-accordion-bd am-collapse ">
            <!-- 规避 Collapase 处理有 padding 的折叠内容计算计算有误问题， 加一个容器 -->
            <div class="am-accordion-content">
                走在忠孝东路 <br/> 徘徊在茫然中 <br/> 在我的人生旅途 <br/> 选择了多少错误 <br/> 我在睡梦中惊醒 <br/> 感叹悔言无尽 <br/> 恨我不能说服自己 <br/> 接受一切教训 <br/> 让生命去等候 <br/> 等候下一个漂流 <br/> 让生命去等候 <br/>等候下一个伤口
            </div>
        </dd>
    </dl>
    <dl class="am-accordion-item">
        <dt class="am-accordion-title">
            <img src="<%=basePath%>phone/plan/1.jpg"/> 我就这样告别山下的家
        </dt>
        <dd class="am-accordion-bd am-collapse ">
            <!-- 规避 Collapase 处理有 padding 的折叠内容计算计算有误问题， 加一个容器 -->
            <div class="am-accordion-content">
                我就这样告别山下的家，我实在不愿轻易让眼泪留下。我以为我并不差不会害怕，我就这样自己照顾自己长大。我不想因为现实把头低下，我以为我并不差能学会虚假。怎样才能够看穿面具里的谎话？别让我的真心散的像沙。如果有一天我变得更复杂，还能不能唱出歌声里的那幅画？
            </div>
        </dd>
    </dl>
</section>
<!--工具栏-->
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default "
     id="">
    <ul class="am-navbar-nav am-cf am-avg-sm-4">
        <li >
          <!--   <a href="tel:123456789" class="">
                <span class="am-icon-phone"></span>
                <span class="am-navbar-label">呼叫</span>
            </a> -->
             <a href="###" class="">
                <span class="am-icon-qq" data-am-popover="{content: 'QQ:466074875'}"></span>
                <span class="am-navbar-label" data-am-popover="{content: 'QQ:466074875'}">客服</span>
            </a>
           <!--  <a href="tencent://message/?uin=466074875&Site=QQ交谈&Menu=yes" target="blank"><img border="0" src="http://wpa.qq.com/pa?p=1:你的QQ号:7" alt="图片不正常时显示的文字" width="71" height="24" />123</a>
      -->   </li>
        <li data-am-navbar-share>
            <a href="###" class="">
                <span class="am-icon-share-square-o"></span>
                <span class="am-navbar-label">分享</span>
            </a>
        </li>
       <!--  <li data-am-navbar-qrcode>
            <a href="###" class="">
                <span class="am-icon-qrcode"></span>
                <span class="am-navbar-label">二维码</span>
            </a>
        </li> -->
        <!-- <li >
            <a href="https://github.com/allmobilize/amazeui" class="">
                <span class="am-icon-github"></span>
                <span class="am-navbar-label">GitHub</span>
            </a>
        </li> -->
      <%--   <li >
            <a href="<%=basePath%>phone/plan/login.jsp"" class="">
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
<script src="<%=basePath%>static/js/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
<script src="<%=basePath%>static/js/amazeui.widgets.helper.min.js"></script>
</html>