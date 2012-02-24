<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <!-- Framework CSS -->  
    <link rel="stylesheet" href="/omedia/css/blueprint/screen.css" type="text/css" media="screen, projection" />  
    <link rel="stylesheet" href="/omedia/css/blueprint/print.css" type="text/css" media="print" />  
    <!--[if IE]><link rel="stylesheet" href="/omedia/css/blueprint/ie.css" type="text/css" media="screen, projection" /><![endif]-->  
  
    <!-- Import fancy-type plugin. -->  
    <link rel="stylesheet" href="/omedia/css/blueprint/plugins/fancy-type/screen.css" type="text/css" media="screen, projection" />  
	<link rel="stylesheet" href="/omedia/css/blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection" /> 
		<link rel="stylesheet" href="/omedia/css/blueprint/plugins/tabs/screen.css" type="text/css" media="screen, projection" />  
	 

<title>omedia</title>
</head>
<body>
<div class="container">
		<div class="span-24 last">
          <h1><img src="/omedia/icon/omedia-logo.png" width="50"/>Omedia</h1>
        </div>
        <div class="prepend-2 span-6">
          <h4>清华大学 omedia-ccn</h4>
        </div>
        <div class="prepend-15 span-1 last">
          <a href="/omedia">退出</a>
        </div>
        <hr />
		<div class="span-24 last">
       		<ul class='tabs'>
				<li><a href='#text1' class="selected">我的文件夹</a></li>
				<li><a href='#text2'>好友</a></li>
				<li><a href='#text3'>设置</a></li>
			</ul>
        </div>
		<div class="prepend-2 span-20 last">
			<table border=5>
				<tr>
					<th>文件</th>
					<th>大小</th>
					<th>上传时间</th>
				</tr>
			</table>
        </div>

	<script src="/omedia/js/lib/jquery.js"></script>
	<script src="/omedia/js/lib/jquery_cookie.js"></script>
	<script src="/omedia/js/const.js"></script>
	<script src="/omedia/js/utils.js"></script>
	<script src="/omedia/js/common.js"></script>
	<script src="/omedia/js/main.js"></script>
</div>
</body>
</html>