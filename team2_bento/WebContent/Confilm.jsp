<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="css/Confilm.css" rel="stylesheet" />
<title>注文確定画面</title>
<style type="text/css">
{
	display: inline-block;
}
</style>
</head>

<body>
<%@include file="./AdminHeader.jsp" %>
<%
List <String[]> list = (List<String[]>)request.getAttribute("list");
%>
<h3>全部署一覧</h3>

<table align = "center">
<tr>
<% for(String[] s :list){%>
<th><%= s[0] %></th>
<th><%= s[1] %>個</th>
<th><%= s[2] %>円</th>
<% } %></tr><br/>

</table>
<br>
<pre>
本日分の注文は確定してもよろしいですか
</pre>
<br>
<pre>
<button>キャンセル</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button>確定</button>
</pre>
</body>
</html>
