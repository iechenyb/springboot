<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String jhlx =  request.getParameter("jhlx");
String jhbh = request.getParameter("jhbh");
String idx = request.getParameter("idx");
if(idx==null||idx=="") idx="0";
%>
<!DOCTYPE html>
<html ng-app="app">
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
    <script type="text/javascript" src="<%=basePath %>phone/plan/ZeroClipboard.js"></script>
    <style type="text/css">
        li{font-size: 20px;margin-left:0px;}
        ul{font-size: 30px;}
    </style>
    <script> 
    function CurentTime()
    { 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds()      //分
       
        var clock = year + "-";
       
        if(month < 10)
            clock += "0";
       
        clock += month + "-";
       
        if(day < 10)
            clock += "0";
           
        clock += day + " ";
       
        if(hh < 10)
            clock += "0";
           
        clock += hh + ":";
        
        if (mm < 10) clock += '0'; 
        clock += mm+ ":";
        
        if (ss < 10) clock += '0'; 
        clock += ss; 
        
        return(clock); 
    } 
    var idx=<%=idx%>;
    function setVar(i){
    	console.log("idx="+i);
    	idx=i;
    }
	//定时器 异步运行 
	function hello(){ 
		//console.log("refresh..."+CurentTime());
	//	var href="<%=basePath%>plan/getPlan?jhbh=<%=jhbh%>&jhlx=<%=jhlx%>&idx="+idx;
		//window.location.replace(href);
	} 
	var curTxt="";
	function setValue(val){
		curTxt = val;
		console.log(curTxt);
	}
		//使用方法名字执行方法 
		//var t1 = window.setInterval(hello,1000*10);//毫秒 
		//var t2 = window.setTimeout("hello()",3000);//使用字符串执行方法 
		//window.clearTimeout(t1);//去掉定时器 
		
		function init(id) {
			console.log("id:"+id);
			clip = new ZeroClipboard.Client();
			clip.setHandCursor(true);
			clip.addEventListener('mouseOver', function(client) {
				// update the text on mouse over
				console.log("inner id:"+id);
				clip.setText($('id').value+"版权申明iechenyb！");
			});

			clip.addEventListener('complete', function(client, text) {
				//debugstr("Copied text to clipboard: " + text );
				alert("该地址已经复制，你可以使用Ctrl+V 粘贴。");
			});
			clip.glue('clip_button', 'clip_container');
		}
</script> 
</head>
<body ng-controller="contentController" oncontextmenu='return false;' >
<!--  oncontextmenu='return false;'  
onselectstart="return false;"  
ondragstart="return false;" 
onselect='document.selection.empty()'   
oncopy='document.selection.empty()'   
onbeforecopy='return false' 
onmouseup='document.selection.empty()' -->
<!--header-->
<input type="hidden"  value="<%= basePath%>" id="path"/>
<input type="hidden"  value="<%= jhlx%>" id="jhlx"/>
<input type="hidden"  value="<%= jhbh%>" id="jhbh"/>
<header data-am-widget="header" class="am-header am-header-default">
    <div class="am-header-left am-header-nav">
        <a href="<%=basePath%>phone/plan2/index.jsp" class="" >
            <i class="am-header-icon am-icon-home"></i>
        </a>
    </div>
    <h1 class="am-header-title">
        <a href="#title-link" class="">
            3A信誉团队${username}
        </a>
    </h1>
    <div class="am-header-right am-header-nav">
        <a href="#right-link" class="" data-am-offcanvas="{target: '#doc-oc-demo3'}">
<<<<<<< HEAD
           人工计划 <i class="am-header-icon am-icon-bars"></i>
=======
            人工计划<i class="am-header-icon am-icon-bars"></i>
>>>>>>> 2af0b915207c21d06314351034f00b5f976ed8e1
        </a>
    </div>
</header>
<!-- 侧边栏内容 -->
<div id="doc-oc-demo3" class="am-offcanvas">
    <div class="am-offcanvas-bar am-offcanvas-bar-flip">
        <div class="am-offcanvas-content">
             <ul><img src="<%=basePath%>phone/plan/sscb.png"></img>重庆
                <li ng-repeat="cq in cqList">
	                  <a href="<%=basePath%>phone/plan2/content.jsp?jhbh={{cq.jhbh}}&jhlx={{cq.jhlx}}"> 
	               		{{cq.jhmc}}
	                </a> 
               </li>
            </ul>
            <ul><img src="<%=basePath%>phone/plan/PK10b.png"></img>PK10
                <li ng-repeat="cq in pkList">
	                   <a href="<%=basePath%>phone/plan2/content.jsp?jhbh={{cq.jhbh}}&jhlx={{cq.jhlx}}"> 
	               		{{cq.jhmc}} 
	                </a> 
               </li>
            </ul>
        </div>
    </div>
</div>
<<<<<<< HEAD
<!-- <input type="text" value="1" ng-model="opidx"/><button name="open"  ng-click="show()">显示 	</button> -->
=======
当前查看面板：{{indexP}}--{{time}}
>>>>>>> 2af0b915207c21d06314351034f00b5f976ed8e1
<section data-am-widget="accordion"  id="collapse-nav" class="am-accordion am-accordion-gapped" data-am-accordion='{  }'>
    	<div class="am-panel-group" id="accordion">
		  <div class="am-panel am-panel-default" ng-repeat="t in planList" repeat-finish>
			    <div class="am-panel-hd">
			      <h4 class="am-panel-title" data-am-collapse="{parent: '#accordion', target: '#do-not-say-{{$index}}}" ng-click="open($index)">
			        <img width="50px"  height="50px" src="{{t.pic}}"/> {{t.name}}
			      </h4>
			    </div><!-- class="am-panel-collapse am-collapse am-in" -->
			    <div id="do-not-say-{{$index}}"  ng-class="{'am-panel-collapse am-collapse am-in':curid==$index,'am-panel-collapse am-collapse' :curid!=$index}">
			      <div class="am-panel-bd">
			        	<p ng-bind-html="t.content | to_trusted"></p>
			      </div>
			    </div>
		  </div>
	</div>
</section> 
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default "
     id="">
    <ul class="am-navbar-nav am-cf am-avg-sm-4">
        <li >
           <a href="###" class="">
                <span class="am-icon-qq" data-am-popover="{content: 'QQ:'}"></span>
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
           <a href="javascript:void(0)" class=""  ng-click="exit()">
                <span class="am-icon-download"></span>
                <span class="am-navbar-label">退出</span>
            </a>
        </li>
    </ul>
</div>
</body>
<script src="<%=basePath %>static/js/jquery.min.js"></script>
<script src="<%=basePath %>static/js/amazeui.widgets.helper.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/angular.min.js"></script>
<script type="text/javascript" src="http://cdn.amazeui.org/amazeui/2.7.2/js/amazeui.min.js"></script>
<script src="<%=basePath %>static/js/angular-sanitize.min.js"></script>
<script src="<%=basePath%>/phone/plan2/content.js"></script>
</html>