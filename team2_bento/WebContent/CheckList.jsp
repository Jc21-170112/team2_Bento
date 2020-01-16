<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="CheckList.css" type="text/css"/>
<title>受け渡しチェックリスト画面</title>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%List<String[]> list = (List<String[]>)request.getAttribute("list");
 %>
<h1>oo部署</h1>
<form action="" method="get">
<table align = "center"border="1" bgcolor="#FFFFFF">
<tr>
<th>名前</th>
<th>ID</th>
<th>注文内容</th>
<th>個数</th>
</tr>
<tr>
<% for(String[] s :list){%>
<th><%= s[0] %></th>
<th><%= s[1] %></th>
<th><%= s[2] %></th>
<th><%= s[3] %>個</th>
<th><% if(s[4].equals("1")){ %>
<input type="checkbox" checked="checked" name=<%= s[5] %> value=<%= s[5] %>>
<% }else{ %>
<input type="checkbox" name=<%= s[5] %> value=<%= s[5] %> >
<% } %>
</th>
</tr>
<% } %>

</table>
<div id="hozon">
<input type="submit" value="保存する" style="width:100px;height:50px"/>
</div>
</form>
</body>
</html>