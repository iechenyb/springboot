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
<html ng-app="app">
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
<body ng-controller="indexController">
<!--header-->
<input type="hidden"  value="<%= basePath%>" id="path"/>
<header data-am-widget="header" class="am-header am-header-default">
    <div class="am-header-left am-header-nav">
        <a href="<%=basePath%>static/vip/index.jsp" class="">
            <i class="am-header-icon am-icon-home"></i>
        </a>
    </div>
    <h1 class="am-header-title">
        <a href="#" class="">
           欢迎你&nbsp;{{username}}！
        </a>
    </h1>
    <div class="am-header-right am-header-nav">
        <a href="#right-link" class="" data-am-offcanvas="{target: '#doc-oc-demo3'}">
            <i class="am-header-icon am-icon-bars"></i>
        </a>
    </div>
</header>
<!-- <div data-am-widget="slider" class="am-slider am-slider-default"
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
</div> -->
<div class="am-g">
		<div class="am-u-sm-12">{{curName}}&nbsp;&nbsp;{{curIndex}}</div>
</div>
<div class="am-g" >
  <!-- <div class="am-u-sm-12" style="border:0px solid green;">&nbsp;</div> -->
   <!-- <div class="am-u-sm-2" style="border:0px solid green;">
	<div ng-repeat="var in [1,2,3,4,5,6,7]">{{var}}</div>
    </div> -->
    <div class="am-u-sm-12" style="border:0px solid green;margin-top:2px;">
		<iframe name="center"  width="100%" src="http://player.jidiaose.com/supapi/iframe.php?v=https://v.qq.com/x/cover/fzfi0p4etjrckhh/o0026hwp2r1.html"  frameborder=0 >
		</iframe>
    </div> 
    <div class="am-u-sm-12" style="border:0px solid red;">
	   接口选择： <select  class="am-form-select" data-am-selected ng-model="mySelected" >
			<option value="http://jqaaa.com/jx.php?url=" >万能接口1</option>
			<option value="http://www.662820.com/xnflv/index.php?url=">万能接口2</option>
			<option value="http://jiexi.92fz.cn/player/vip.php?url=">万能接口3</option>
			<option value="http://www.662820.com/xnflv/index.php?url=">万能接口4</option>
			<option value="http://api.91exp.com/svip/?url=">万能接口5</option>
			<option value="http://vip.jlsprh.com/index.php?url=">万能接口6</option>
			<option value="http://player.jidiaose.com/supapi/iframe.php?v=">万能接口7</option>
			<option value="http://www.82190555.com/index/qqvod.php?url=">腾讯视频接口1</option>
			<option value="http://api.pucms.com/?url=">爱奇艺超清接口1</option>
			<option value="http://api.baiyug.cn/vip/index.php?url=">爱奇艺超清接口2</option>
			<option value="https://api.flvsp.com/?url=">爱奇艺超清接口3</option>
			<option value="http://api.xfsub.com/index.php?url=">芒果TV超清接口</option>
			<option value="http://65yw.2m.vc/chaojikan.php?url=">芒果TV手机接口</option>
			<option value="http://www.82190555.com/index/qqvod.php?url=">优酷超清接口</option>
			<option value="http://vip.jlsprh.com/index.php?url=">搜狐视频接口</option>
			<option value="http://2gty.com/apiurl/yun.php?url=">乐视视频接口</option>
		</select>
    </div>
     <div class="am-u-sm-12" style="border:0px solid red;">
	    平台选择： <select data-am-selected ng-model="type" >
	     	<option value="tengxun" >腾讯vip</option>
			<option value="aqy">爱奇艺vip</option>
	     </select>
     </div>
	<div class="am-u-sm-12" style="border:0px solid green;margin:0px;">
		 	<input type="text"  class="am-form-field am-radius"  placeholder="输入电视剧名称" ng-model="nameKey"></input>
	</div> 
	<div class="am-u-sm-12" style="border:0px solid green;margin:0px;"><button type="button"  style="width:100%;" class="am-btn am-btn-primary"  ng-click="search()">腾讯及爱奇艺搜索</button></div>
</div>
<div class="am-g">
<div class="am-u-sm-12" style="border:0px solid green;margin:0px;">
		 	<input type="text"  class="am-form-field am-radius"  placeholder="优酷电视剧地址" ng-model="youkuKey"></input>
	</div> 
	<div class="am-u-sm-12" style="border:0px solid green;margin:0px;"><button type="button"  style="width:100%;" class="am-btn am-btn-primary"  ng-click="searchYouku()">优酷专用搜索</button></div>
</div>
<div class="am-g" >
	<div class="am-u-sm-2 am-u-end" ng-repeat="vo in list" ><a ng-click="play(vo)" href="javascript:void(0);">{{vo.idx}}</a></div>
</div>
<a target='center' id='page9090'></a>
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default "
     id="">
    <ul class="am-navbar-nav am-cf am-avg-sm-4">
        <li >
             <a href="###" class="">
                <span class="am-icon-qq" data-am-popover="{content: 'QQ:}"></span>
                <span class="am-navbar-label" data-am-popover="{content: 'QQ:'}">客服</span>
            </a>
          </li>
        <li data-am-navbar-share>
            <a href="###" class="">
                <span class="am-icon-share-square-o"></span>
                <span class="am-navbar-label">分享</span>
            </a>
        </li>
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
<script type="text/javascript" src="<%=basePath%>static/js/angular.min.js"></script>
<script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
<script src="<%=basePath%>static/js/amazeui.widgets.helper.min.js"></script>
<script src="<%=basePath%>/static/vip/index.js?v=Math.random()"></script>
</html>