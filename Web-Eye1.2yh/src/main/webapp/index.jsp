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
<script type="text/javascript">
function click() {
	//alert('禁止你的左键复制！');
	return;
	}
	function click1() {
	if (event.button==2) {
		//alert('禁止右键点击！');
		return ;
		}
	}
	function CtrlKeyDown(){
		
	//if (event.ctrlKey) {alert('禁止复制！') }
	return;
	
	}
	document.onkeydown=CtrlKeyDown;
	document.onselectstart=click;
	document.onmousedown=click1;
function copy(){
	   var text="csldfjsdf";
	   window.clipboardData.setData("Text",text);
	   alert("已经复制到剪贴板！"); 
}
</script>
<!-- oncontextmenu='return false' onselectstart="return false"  ondragstart="return false" -->
<body oncontextmenu='return false;'  onselectstart="return false;"  ondragstart="return false;" 
onselect='document.selection.empty()'   
oncopy='document.selection.empty()'   
onbeforecopy='return false'   
onmouseup='document.selection.empty()'
 >
security标签使用！走过滤器可以使用，不走过滤器直接访问jsp是不可以用的！<br>
<hr>
1 <sec:authentication property="name"/>
2 <sec:authentication property="principal.username"></sec:authentication> 
 <%-- <sec:authorize ifAllGranted="ROLE_ADMIN">  
 		<sec:authentication property="principal.username"></sec:authentication>   
    </sec:authorize> --%>
 3 ${session.SPRING_SECURITY_CONTEXT.authentication.principal.username} 
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
	<!-- 角色不存在，不可见 -->
	此处隐藏一个连接，用户ROLE_USER1角色都能看的<br>
	<sec:authorize access="hasRole('ROLE_USER1')">
		<div>
			<p class="bg-info">所以用户都能看到</p>
		</div>
	</sec:authorize>
	<sec:authorize url="/common/toPage1">
      <a href="/common/toPage1">page1</a>
   </sec:authorize>
   page3没有权限，被隐藏！
   <sec:authorize url="/common/toPage3">
      <a href="/common/toPage3">page3</a>
   </sec:authorize>
</body>
</html>