<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>404</title>
</head>
<body>
<h1>404</h1> 
页面不存在 &nbsp; /  &nbsp;page not found
<a href="#"><h4  onclick='toIndex()'>返回首页</h4></a> 
</body>
<script>
var WebVar = 
{
	VarPath : "${pageContext.request.contextPath}",
	ImgOcxBg: 0x8c8d8b,
	TimeOut	: 10000
};
var http = window.location.protocol;
var host = window.location.host;
var port = window.location.port;
function toIndex(){
	window.location =window.location.protocol +"//"+window.location.host+WebVar.VarPath
}
</script>
</html>