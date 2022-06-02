<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.DataSource" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: chisanahn
  Date: 6/2/2022
  Time: 10:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>새로운 게임 등록</title>
    <link href="css/home_styles.css" rel="stylesheet"/>
</head>
<body>
<%
    AppConfig appConfig = new AppConfig(new DataSource());
    MemberDAO memberDAO = appConfig.getMemberDAO();
    BalanceGameVoteDAO balanceGameVoteDAO = appConfig.getBalanceGameVoteDAO();
    BalanceGameDAO balanceGameDAO = appConfig.getBalanceGameDAO();
    BalanceGameCommentDAO balanceGameCommentDAO = appConfig.getBalanceGameCommentDAO();

    Long memberId = (Long) session.getAttribute("memberId");

    if(memberDAO.isTempMember(memberId)) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('" + "You should login to create game" + "')");
        script.println("location.href='login.html'");
        script.println("</script>");
    }
%>
<div id="layoutDefault">
    <main>
        <!--nav bar -->
        <nav class="navbar">
            <div class="nav-container">
                <a class="navbar-brand" href="index.jsp">세모밸</a>
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
                    <a href="create_balance_game.jsp">인기순</a>
                    <a href="index.jsp">마이페이지</a>
                    <a href="create_balance_game.jsp">글쓰기</a>
                </div>
            </div>
            <div class="bubble x4"></div>
        </div>

        <div id="newWriting">
            <form action="/addBalanceGameServlet" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
                <table border="1">
                    <tr height="50px">
                        <td colspan="2"><p>난이도 선택 : </p>
                            <ul style="list-style: none;">
                                <li class="nav-item">
                                    <input type="radio" name="difficulty" value="top">상
                                </li>
                                <li class="nav-item">
                                    <input type="radio" name="difficulty" value="middle">중
                                </li>
                                <li class="nav-item">
                                    <input type="radio" name="difficulty" value="bottom">하
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <tr height="50px">
                        <td colspan="2" ><input class="data" type="text" name="question" placeholder="Question"/></td>
                    </tr>
                    <tr height="50px">
                        <td>Answer 1</td>
                        <td>Answer 2</td>
                    </tr>
                    <tr height="400px">
                        <td><input class="data" type="file" name="picture1" size="50"/></td>
                        <td><input class="data" type="file" name="picture2" size="50"/></td>
                    </tr>
                    <tr height="200px">
                        <td><input class="data" type="text" name="answer1" placeholder="Answer1"/></td>
                        <td><input class="data" type="text" name="answer2" placeholder="Answer2"/></td>
                    </tr>
                    <tr height="50px">
                        <td colspan="2"><input class="data" type="submit" value="Save"></td>
                    </tr>
                </table>
            </form>
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
