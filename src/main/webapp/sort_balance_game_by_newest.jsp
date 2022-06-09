<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.DataSource" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.List" %><%--
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
    <link href="css/laundry.css" rel="stylesheet"/>
</head>
<body>
<%
    AppConfig appConfig = new AppConfig(new DataSource());
    MemberDAO memberDAO = appConfig.getMemberDAO();
    BalanceGameVoteDAO balanceGameVoteDAO = appConfig.getBalanceGameVoteDAO();
    BalanceGameDAO balanceGameDAO = appConfig.getBalanceGameDAO();
    BalanceGameCommentDAO balanceGameCommentDAO = appConfig.getBalanceGameCommentDAO();

    List<BalanceGame> newBalanceGames;
    newBalanceGames = balanceGameDAO.findAllSortedBy("id");
%>

<div id="layoutDefault">
    <main>
        <!--nav bar -->
        <nav class="navbar">
            <div class="nav-container">
                <a class="navbar-brand" href="index.jsp">세모밸</a>
                <div class="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <%
                            Long memberId = (Long) session.getAttribute("memberId");

                            // 로그인이 안되어있는 경우
                            if(memberId == null) {
                                response.sendRedirect("/");
                                return;
                            }

                            if (memberDAO.isTempMember(memberId)) {
                        %>
                        <li class="nav-item">
                            <a class="nav-link" href="login.html">로그인</a>
                        </li>
                        <%
                        } else {
                        %>
                        <li class="nav-item">
                            <a class="nav-link" href="/logout.jsp">로그아웃</a>
                        </li>
                        <%
                            }
                        %>
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp">랜덤 시작</a>
                        </li>
                        <li class="nav-item">
                            <!--방울 메뉴창(마이페이지, 글쓰기 등) 띄우는 링크? -->
                            <a class="cloudbtn" href="#">메뉴</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!--위로 바로가기-->
        <nav >
            <img class="go_up_btn" src="image/go_up_btn.png" width="100px" height="100px" />
            <a id="go_up" href="#">맨 위로</a>
        </nav>

        <!--page header : 바로 게임 시작 햇님 버튼 -->
        <header class="page-header">
            <div class="header-start">
                <a href="create_balance_game.jsp">
                    <svg id=sun" height="400" width="400" viewBox="-10 -10 410 410">
                        <circle cx="200" cy="200" r="130" fill="#edaa3b" ></circle>
                        <ellipse cx="200" cy="260" rx="35" ry="30"  fill="red" stroke="red" stroke-width="1" ></ellipse>
                        <rect x="160" y="205" width="80" height="37" style="fill: #edaa3b;"></rect>
                        <circle cx="245" cy="200" r="14"  fill="black" ></circle>
                        <circle cx="155" cy="200" r="14"  fill="black" ></circle>

                        <rect class="rect1" x="200" y="350" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center;"></rect>
                        <rect class="rect2" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(30deg);"></rect>
                        <rect class="rect3" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(60deg);"></rect>
                        <rect class="rect4" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(90deg);"></rect>
                        <rect class="rect5" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(120deg);"></rect>
                        <rect class="rect6" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(150deg);"></rect>
                        <rect class="rect7" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(180deg);"></rect>
                        <rect class="rect8" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(210deg);"></rect>
                        <rect class="rect9" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(240deg);"></rect>
                        <rect class="rect10" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(270deg);"></rect>
                        <rect class="rect11" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(300deg);"></rect>
                        <rect class="rect12" x="200" y="360" width="12" height="50" rx="5" ry="5" style="fill: #edaa3b; transform-origin: center; transform: rotate(330deg);"></rect>
                    </svg>
                </a>
            </div>
        </header>

        <div id="background-wrap">
            <div class="bubble x1"></div>
            <div class="bubble x2"></div>
            <div class="bubble x3">
                <div class="menu">
                    <h1><< 메뉴 >></h1>
                    <a href="create_balance_game.jsp">글쓰기</a>
                    <a href="index.jsp">오늘의 밸런스게임</a>
                    <a href="sort_balance_game_by_like.jsp">인기순 밸런스게임</a>
                    <a href="sort_balance_game_by_newest.jsp">최신순 밸런스게임</a>
                    <a href="sort_balance_game_by_difficulty.jsp">난이도별 밸런스게임</a>
                </div>
            </div>
            <div class="bubble x4"></div>
        </div>

        <span class="svg-border-rounded">
            <!--최신순 정렬-->
        <h2 class="balancegameTitle">따끈따끈한 밸런스 게임들</h2>
            <h2><br/></h2>
            <svg viewBox="0 0 144.54 5.5" preserveAspectRatio="none" fill="white">
                <path d="M144.54, 17.34H144.54ZM0, 0S32.36, 5, 72.27, 5, 144.54, 0, 144.54, 0"
                      fill="transparent"
                      style="stroke:rgb(0, 0, 0);"></path>
            </svg>
        </span>

        <!--모든 밸런스게임-->
        <section class="today-best" style="position: relative;">
            <div class="container">
                <%
                    long maxIndex = balanceGameDAO.getLastBalanceGameId();
                    int index = 4;
                    int flag = 2;
                    int ang = 0;
                    for (int i = 1; i <= newBalanceGames.size(); i++) {
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
                    if (i % 5 == 1) {
                        ang = 6;
                    } else if (i % 5 == 2) {
                        ang = 0;
                    }else if (i % 5 == 3) {
                        ang = -6;
                    } else if (i % 5 == 4) {
                        ang = 5;
                    } else if (i % 5 == 0) {
                        ang = -5;
                    }%>

                <a class="towel-page" href="show_balance_game.jsp?balanceGameId=<%=newBalanceGames.get(newBalanceGames.size() - i).getId()%>" style="transform: rotate(<%=ang%>deg)">
                    <div class="towel">
                        <%
                            Random rand = new Random();
                            int randnum = 0;
                            String[] clothes = {"clothes", "clothes1", "clothes2", "clothes3", "clothes4", "clothes5", "clothes6", "clothes7", "clothes8", "clothes9", "towel"};
                            randnum = rand.nextInt(11);
                            String selectclothes = "./image/" + clothes[randnum] + ".png";
                            int laundryClass = (int)((Math.random()*10)%3)+1;
                            if(laundryClass == 1) {%>
                        <image class="laundry_1" src="<%=selectclothes%>" width="450" height="470"></image> <%}

                        if(laundryClass == 2) {%>
                        <image class="laundry_2" src="<%=selectclothes%>" width="450" height="470"></image> <%}
                        if(laundryClass == 3) {%>
                        <image class="laundry_3" src="<%=selectclothes%>" width="450" height="470"></image> <%}%>
                        <div class="balancegame">
                            <p style="font-size: 22px;"><%=newBalanceGames.get(newBalanceGames.size() - i).getQuestion()%>
                            </p>
                            <h4><br/></h4>
                            <p><%=newBalanceGames.get(newBalanceGames.size() - i).getAnswer1()%>
                            </p>
                            <p style="color: saddlebrown">vs</p>
                            <p><%=newBalanceGames.get(newBalanceGames.size() - i).getAnswer2()%>
                            </p>
                        </div>
                    </div>
                </a>
                <%
                    }
                %>
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

    const go_up = document.querySelector('#go_up');
    go_up.addEventListener('click', function(event) {
        event.preventDefault(); // a tag 눌렀을 때 href 링크로 이동하지 않기 / form의 submit 버튼을 눌렀을 때 새로 실행하지 않음
        const target = event.target; // 이벤트가 발생한 타겟
        const body = document.querySelector('body');
        window.scrollTo({
            top: body.getBoundingClientRect().top + window.pageYOffset,
            behavior: 'smooth'
        })
    })
</script>
</html>