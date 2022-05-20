<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.TestDataSource" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %><%--
  Created by IntelliJ IDEA.
  User: chisanahn
  Date: 5/20/2022
  Time: 1:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>밸런스 게임</title>
</head>
<body>
<%
    Long balanceGameId = (Long) request.getAttribute("balanceGameId");
    if(balanceGameId == null) balanceGameId = 1L;

    AppConfig testAppConfig = new AppConfig(new TestDataSource());
    MemberDAO memberDAO = testAppConfig.getMemberDAO();
    BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();
    BalanceGameDAO balanceGameDAO = testAppConfig.getBalanceGameDAO();
    BalanceGameCommentDAO balanceGameCommentDAO = testAppConfig.getBalanceGameCommentDAO();

    BalanceGame balanceGame = balanceGameDAO.findById(balanceGameId);
%>
<table border="1">
    <tr>
        <td>Question</td>
        <td colspan="2"><%=balanceGame.getQuestion()%></td>
    </tr>
    <tr>
        <td>Answer 1</td>
        <td><%=balanceGame.getAnswer1()%></td>
        <td><img src="/files/<%=balanceGame.getAnswer1PictureUrl()%>" alt="picture1"></td>
    </tr>
    <tr>
        <td>Answer 2</td>
        <td><%=balanceGame.getAnswer2()%></td>
        <td><img src="/files/<%=balanceGame.getAnswer2PictureUrl()%>" alt="picture1"></td>
    </tr>
</table>
</body>
</html>
