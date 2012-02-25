<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>social graph</title>
</head>
<body>
  <canvas id="viewport" width="800" height="600"></canvas>
  <span id="account" hidden="true" token="<% out.print(request.getAttribute("token")); %>"
   accountId="<% out.print(request.getAttribute("accountId")); %>"></span>

  <script src="js/lib/jquery.js"></script>
  <script src="js/lib/arbor.js"></script>
  <script src="js/lib/arbor-graphics.js"></script>
  <script src="js/lib/json.js"></script>
  <script src="js/socialGraph.js"></script>
</body>
</html>