<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.TestDataSource" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %>
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
    <title>밸런스 게임</title>
    <link href="css/home_styles.css" rel="stylesheet"/>
</head>
<body>
<div id="layoutDefault">
    <main>
        <!--nav bar -->
        <nav class="navbar">
            <div class="nav-container">
                <a class="navbar-brand" href="home.jsp">세모밸</a>
                <div class="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <!--방울 메뉴창(마이페이지, 글쓰기 등) 띄우는 링크? -->
                            <a class="cloudbtn" href="#">svg</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div id="background-wrap">
            <div class="bubble x1"></div>
            <div class="bubble x2"></div>
            <div class="bubble x3">
                <div class="menu">
                    <h1>메뉴</h1>
                    <a href="create_balance_game.html">인기순</a>
                    <a href="index.jsp">마이페이지</a>
                    <a href="create_balance_game.html">글쓰기</a>
                </div>
            </div>
            <div class="bubble x4"></div>
        </div>

        <%
            Long balanceGameId = (Long) request.getAttribute("balanceGameId");
            if (balanceGameId == null) balanceGameId = 1L;

            AppConfig testAppConfig = new AppConfig(new TestDataSource());
            MemberDAO memberDAO = testAppConfig.getMemberDAO();
            BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();
            BalanceGameDAO balanceGameDAO = testAppConfig.getBalanceGameDAO();
            BalanceGameCommentDAO balanceGameCommentDAO = testAppConfig.getBalanceGameCommentDAO();

            BalanceGame balanceGame = balanceGameDAO.findById(balanceGameId);
        %>

        <div id="newWriting">
            <table border="1">
                <tr height="50px">
                    <td colspan="2"><%=balanceGame.getQuestion()%></td>
                </tr>
                <tr height="50px">
                    <td>Answer 1</td>
                    <td>Answer 2</td>
                </tr>
                <tr height="900px">
                    <td><img src="/files/<%=balanceGame.getAnswer1PictureUrl()%>" alt="picture1" width="800px"></td>
                    <td><img src="/files/<%=balanceGame.getAnswer2PictureUrl()%>" alt="picture2" width="800px"></td>
                </tr>
                <tr height="200px">
                    <td><%=balanceGame.getAnswer1()%></td>
                    <td><%=balanceGame.getAnswer2()%></td>
                </tr>
            </table>
        </div>
    </main>
</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
    const cloudbtn = $(".cloudbtn");
    const bubbles = $(".bubble");
    $(document).ready(function () {
        cloudbtn.click(function () {
            $("#background-wrap").show("slow");
        });
        $(".x4").click(function () {
            $("#background-wrap").hide("slow");
        });
    });

</script>
</html>
