<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>复制测试！</title>
</head>
<!-- http://demo.jb51.net/js/ZeroClipboard/ -->
<!-- oncontextmenu='return false' onselectstart="return false"  ondragstart="return false" -->
<body>
	<script type="text/javascript" src="ZeroClipboard.js"></script>
	<script language="JavaScript">
		var clip = null;
		function $(id) {
			return document.getElementById(id);
		}
		function init() {
			clip = new ZeroClipboard.Client();
			clip.setHandCursor(true);
			clip.addEventListener('mouseOver', function(client) {
				// update the text on mouse over
				clip.setText($('fe_text').value+"版权申明iechenyb！");
			});

			clip.addEventListener('complete', function(client, text) {
				//debugstr("Copied text to clipboard: " + text );
				alert("该地址已经复制，你可以使用Ctrl+V 粘贴。");
			});
			clip.glue('clip_button', 'clip_container');
		}
	</script>
</head>
<body onLoad="init()"
 oncontextmenu='return false;'  
onselectstart="return false;"  
ondragstart="return false;" 
onselect='document.selection.empty()'   
oncopy='document.selection.empty()'   
onbeforecopy='return false'   
onmouseup='document.selection.empty()'>
	<input id="fe_text" cols=50 rows=5 value=复制内容文本1>
	<span id="clip_container"><span id="clip_button"><b>复制</b></span></span>
</body>


</html>