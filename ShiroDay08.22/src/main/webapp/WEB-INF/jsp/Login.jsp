<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>用户登录</h2>
<h3 style='color:red'>${msg}</h3>
<form action="${pageContext.request.contextPath}/user/login" method="POST">
  用户名:<input name="username"/> <br/>
  密码:<input name="password" type="password"/> <br/>
  <label>
  	<input type="checkbox" name="rememberMe" value="1">记住密码
  </label>
  <input type="submit" value="登录"/>
</form>
<%
	Object add = session.getId();
	out.print(add);

%>
</body>
</html>