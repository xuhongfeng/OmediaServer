<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>login</title>
</head>
<body>
	<table>
		<tr>
			<td>用户名:</td>
			<td><input id="username"/></td>
		</tr>
		<tr>
			<td>密码:</td>
			<td><input type="password" id="password"/></td>
		</tr>
		<tr>
			<td><input id="btn-login" type="button" value="登陆" /></td>
			<td></td>
		</tr>
	</table>
	
	<script src="/omedia/js/lib/jquery.js"></script>
	<script src="/omedia/js/lib/jquery_cookie.js"></script>
	<script src="/omedia/js/const.js"></script>
	<script src="/omedia/js/utils.js"></script>
	<script src="/omedia/js/common.js"></script>
	<script src="/omedia/js/login.js"></script>
</body>
</html>