<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.DataSource" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %>
<%@ page import="com.example.balance_game_community.TestDataSource" %>
<%@ page import="java.util.Random" %><%--
  Created by IntelliJ IDEA.
  User: kmj
  Date: 2022-05-22
  Time: 오후 7:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>세상의 모든 밸런스 게임</title>
    <link href="css/home_styles.css" rel="stylesheet"/>
</head>
<body>
<%
    AppConfig appConfig = new AppConfig(new TestDataSource());
    MemberDAO memberDAO = appConfig.getMemberDAO();
    BalanceGameVoteDAO balanceGameVoteDAO = appConfig.getBalanceGameVoteDAO();
    BalanceGameDAO balanceGameDAO = appConfig.getBalanceGameDAO();
    BalanceGameCommentDAO balanceGameCommentDAO = appConfig.getBalanceGameCommentDAO();

%>

<div id="layoutDefault">
    <main>
        <!--nav bar -->
        <nav class="navbar">
            <div class="nav-container">
                <a class="navbar-brand" href="home.jsp">세모밸</a>
                <div class="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp">로그인</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp">회원가입?</a>
                        </li>
                        <li class="nav-item">
                            <!--방울 메뉴창(마이페이지, 글쓰기 등) 띄우는 링크? -->
                            <a class="cloudbtn" href="#">svg</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!--page header : 바로 게임 시작 햇님 버튼 -->
        <header class="page-header">
            <div class="header-start">
                <a href="create_balance_game.html">
                    <img class="startimgcontent" src="img/sun.jpg"/>
                </a>
            </div>
        </header>

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

        <span class="svg-border-rounded">
                        <svg viewBox="0 0 144.54 5.5" preserveAspectRatio="none" fill="white">
                            <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 5, 72.27, 5, 144.54, 0, 144.54, 0"
                                  fill="transparent"
                                  style="stroke:rgb(0, 0, 0);"></path>
                        </svg>
                    </span>

        <!--모든 밸런스게임-->
        <section class="today-best">
            <div class="container">
                <%
                    long maxIndex = balanceGameDAO.getLastBalanceGameId();
                    int index = 4;
                    int flag = 2;
                    for (int i = 1; i <= maxIndex; i++) {
                        BalanceGame balanceGame = balanceGameDAO.findById((long) i);
                        if (i == index) {
                            index += flag;
                            if (flag == 2) {
                                flag = 3;
                            } else {
                                flag = 2;
                            }
                %>
                <span class="svg-border-rounded">
                        <svg width="100vw" height="auto" viewBox="0 0 144.54 5.5" preserveAspectRatio="none"
                             fill="white">
                            <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 5, 72.27, 5, 144.54, 0, 144.54, 0"
                                  fill="transparent"
                                  style="stroke:rgb(0, 0, 0);"></path>
                        </svg>
                    </span>
                <%
                    }
                %>
                <a class="towel-page" href="show_balance_game.jsp?balanceGameId=<%=i%>">
                    <div class="towel">
                        <%
                            Random rand = new Random();
                            int randnum = 0;
                            String[] clothes = {"clothes", "clothes1", "clothes2", "clothes3", "clothes4", "clothes5", "clothes6", "clothes7", "clothes8", "clothes9", "towel"};
                            randnum = rand.nextInt(11);
                            String selectclothes = "./img/" + clothes[randnum] + ".png";

                        %>
                        <image class="towel" src="<%=selectclothes%>" width="450" height="470"></image>
                        <div class="balancegame">
                            <p style="font-size: 22px;"><%=balanceGame.getQuestion()%>
                            </p>
                            <h4><br/></h4>
                            <p><%=balanceGame.getAnswer1()%>
                            </p>
                            <p style="color: saddlebrown">vs</p>
                            <p><%=balanceGame.getAnswer2()%>
                            </p>
                        </div>
                    </div>
                </a>
                <%}%>
            </div>
        </section>

        <div class="svg-border-rounded">
            <svg viewBox="0 0 144.54 5.5" preserveAspectRatio="none" fill="white">
                <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 5, 72.27, 5, 144.54, 0, 144.54, 0" fill="transparent"
                      style="stroke:rgb(0, 0, 0);"></path>
            </svg>
        </div>

        <!--지금 사람들이 많이 하고 있는 밸런스 게임-->
        <section class="this-time-best">
            <div class="container">

            </div>
        </section>
        <div class="svg-border-rounded">
            <svg viewBox="0 0 144.54 5.5" preserveAspectRatio="none" fill="white">
                <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 5, 72.27, 5, 144.54, 0, 144.54, 0" fill="transparent"
                      style="stroke:rgb(0, 0, 0);"></path>
            </svg>
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
