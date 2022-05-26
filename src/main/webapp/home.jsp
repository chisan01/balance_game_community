<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.DataSource" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %>
<%@ page import="com.example.balance_game_community.TestDataSource" %><%--
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
<script src="./swing.js"></script>
<%
    AppConfig appConfig = new AppConfig(new TestDataSource());
    MemberDAO memberDAO = appConfig.getMemberDAO();
    BalanceGameVoteDAO balanceGameVoteDAO = appConfig.getBalanceGameVoteDAO();
    BalanceGameDAO balanceGameDAO = appConfig.getBalanceGameDAO();
    BalanceGameCommentDAO balanceGameCommentDAO = appConfig.getBalanceGameCommentDAO();

%>

<div id="layoutDefault">
    <div id="layoutDefault-content">
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
                                <a class="cloudbtn" href="index.jsp">svg</a>
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

            <!--빨래 -->
            <div class="svg-border-rounded">
                <svg viewBox="0 0 144.54 18" preserveAspectRatio="none" fill="white">
                    <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 17.34, 72.27, 17.34, 144.54, 0, 144.54, 0" fill="transparent" style="stroke:rgb(0, 0, 0);" ></path>
                </svg>
            </div>

            <!--모든 밸런스게임-->
            <section class="today-best">
                <div class="container">
                    <div class="dropdown_wrapper">
                        <%
                            long maxIndex = balanceGameDAO.getLastBalanceGameId();
                            for (int i = 1; i <= maxIndex; i++) {
                                BalanceGame balanceGame = balanceGameDAO.findById((long) i);
                        %>
                        <div class="dropdown" id="dropdown">
                            <div class="dropdown-trigger">
                                <button class="button" aria-haspopup="true" aria-controls="dropdown-menu2"
                                        id="dropdown_btn">
                                    <span><%=balanceGame.getQuestion()%></span>
                                    <span class="icon is-small">
                                        <i class="fa fa-angle-down" aria-hidden="true"></i>
                                    </span>
                                </button>
                            </div>

                            <div class="dropdown-menu" id="dropdown-menu2" role="menu">
                                <div class="dropdown-content animate__animated dropdown_content_hang"
                                     id="dropdown_item1">
                                    <div class="dropdown-item">
                                        <img src="<%=balanceGame.getAnswer1PictureUrl()%>" width="30px">
                                        <span><%=balanceGame.getAnswer1()%></span>
                                    </div>
                                    <span>vs</span>
                                    <div class="dropdown-item">
                                        <img src="<%=balanceGame.getAnswer1PictureUrl()%>" width="30px">
                                        <span><%=balanceGame.getAnswer2()%></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%}%>
                    </div>

                </div>
            </section>
            <div class="svg-border-rounded">
                <svg viewBox="0 0 144.54 18" preserveAspectRatio="none" fill="white">
                    <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 17.34, 72.27, 17.34, 144.54, 0, 144.54, 0" fill="transparent" style="stroke:rgb(0, 0, 0);"></path>
                </svg>
            </div>

            <!--지금 사람들이 많이 하고 있는 밸런스 게임-->
            <section class="this-time-best">
                <div class="container">

                </div>
            </section>
            <div class="svg-border-rounded">
                <svg viewBox="0 0 144.54 18" preserveAspectRatio="none" fill="white">
                    <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 17.34, 72.27, 17.34, 144.54, 0, 144.54, 0" fill="transparent" style="stroke:rgb(0, 0, 0);"></path>
                </svg>
            </div>
        </main>
    </div>
</div>
</body>
</html>
