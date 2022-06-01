<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.member.Member" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.example.balance_game_community.DataSource" %>
<%--
  Created by IntelliJ IDEA.
  User: chisanahn
  Date: 5/20/2022
  Time: 1:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>세상의 모든 밸런스 게임</title>
</head>
<body>
<%
    String email = request.getParameter("emailInput");
    String nickname = request.getParameter("nicknameInput");
    String password = request.getParameter("passwordInput");
    String passwordCheck = request.getParameter("passwordCheck");

    System.out.println("passwordCheck = " + passwordCheck);

    if (!password.equals(passwordCheck)) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('password check failed')");
        script.println("history.back()");
        script.println("</script>");
    }

    AppConfig testAppConfig = new AppConfig(new DataSource());
    MemberDAO memberDAO = testAppConfig.getMemberDAO();
    Member member = new Member();
    member.setEmail(email);
    member.setNickname(nickname);
    member.setPassword(password);

    PrintWriter script = response.getWriter();
    try {
        memberDAO.signIn(member);
        script.println("<script>");
        script.println("location.href = 'index.jsp'");
        script.println("</script>");
    } catch (Exception e) {
        e.printStackTrace();
        script.println("<script>");
        script.println("alert('" + e.getMessage() + "')");
        script.println("history.back()");
        script.println("</script>");
    }
%>
</body>
</html>