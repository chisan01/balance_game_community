<%--
  Created by IntelliJ IDEA.
  User: chisanahn
  Date: 5/30/2022
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그아웃</title>
</head>
<body>
<%
    request.getSession().removeAttribute("memberId");

    response.sendRedirect("/home.jsp");
%>
</body>
</html>
