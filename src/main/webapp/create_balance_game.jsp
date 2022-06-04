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

    // 로그인이 안되어있는 경우
    if(memberId == null) response.sendRedirect("/");

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
                            <img src="image/menu_btn.png" width="65px" height="65px"
                                 style="position: absolute; top:-5px; right: 110px; opacity: 70%; z-index:101;"/>
                        </li>

                        <li class="nav-item">
                            <!--방울 메뉴창(마이페이지, 글쓰기 등) 띄우는 링크? -->
                            <p class="cloudbtn" href="#redirect" style="top:23px;">메뉴</p>
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
                    <p><br/></p>
                    <a href="create_balance_game.html">글쓰기</a>
                    <a href="index.jsp">오늘의 밸런스게임</a>
                    <a href="create_balance_game.html">인기순 밸런스게임</a>
                    <a href="create_balance_game.html">최신순 밸런스게임</a>
                    <a href="create_balance_game.html">난이도별 밸런스게임</a>
                </div>
            </div>
            <div class="bubble x4">
                <img src="image/white%20x.png" width="100px" height="100px" style="opacity: 70%;"/>
            </div>
        </div>

        <div id="newWriting">
            <form action="/addBalanceGameServlet" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
                <h2 style="padding-top: 20px; padding-bottom: 20px;">질문? </h2>
                <input class="data" type="text" name="question" placeholder="Question" style=" padding-left: 20px; height: 50px; opacity: 50%; border-top: none;
    border-left: none;
    border-right: none;"/>

                <div class="create-game-vs">
                    <div class="create-game-answer" style="padding-right: 50px;">
                        <h2 style="padding-top: 20px;">Choice 1</h2>
                        <div class="button">
                            <label for="chooseFile">
                                👉 사진 선택 👈
                            </label>
                        </div>
                        <input class="data chooseFile" type="file" id="chooseFile" name="picture1" size="50" accept="image/*"
                               onchange="loadFile1(this)">
                        <label id="fileName" style="padding-bottom: 10px;"></label>
                        <img id="img" src="image/preview.svg" style="width: 500px; height: 500px;"/>

                        <input class="data" type="text" name="answer1" placeholder="Choice1" style="padding-left: 20px; height: 100px; margin-top: 20px; margin-bottom: 20px; opacity: 50%;"/>
                    </div>

                    <div class="create-game-answer" style="padding-left: 50px;">
                        <h2 style="padding-top: 20px;">Choice 2</h2>
                        <div class="button">
                            <label for="chooseFile1">
                                👉 사진 선택 👈
                            </label>
                        </div>
                        <input class="data chooseFile" type="file" id="chooseFile1" name="picture2" size="50" accept="image/*"
                               onchange="loadFile2(this)">
                        <label id="fileName2" style="padding-bottom: 10px;"></label>
                        <img id="img2" src="image/preview.svg" style="width: 500px; height: 500px;"/>

                        <input class="data" type="text" name="answer2" placeholder="Choice2" style="padding-left: 20px; height: 100px; margin-top: 20px; margin-bottom: 20px; opacity: 50%;"/>
                    </div>
                </div>

                <div class="create-game-vs">
                    <p style="padding-right: 20px;">난이도 선택 : </p>
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
                </div>
                <input class="data" type="submit" value="Save" style="height: 50px; margin-top: 30px; margin-bottom: 100px; opacity: 50%;">
            </form>
        </div>
    </main>
</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>

    //메뉴버튼
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

    //이미지 업로드
    function loadFile1(input) {
        var file = input.files[0];	//선택된 파일 가져오기

        //파일 이름 추가
        var name = document.getElementById('fileName');
        name.textContent = file.name;

        var newImage = document.getElementById("img");
        newImage.src = URL.createObjectURL(file);  //이미지 source 가져오기
        newImage = document.getElementById('img').lastElementChild;
        newImage.style.visibility = "visible";
    };

    //이미지 업로드
    function loadFile2(input) {
        var file = input.files[0];	//선택된 파일 가져오기

        //파일 이름 추가
        var name = document.getElementById('fileName2');
        name.textContent = file.name;

        var newImage = document.getElementById("img2");
        newImage.src = URL.createObjectURL(file);  //이미지 source 가져오기
        newImage = document.getElementById('img2').lastElementChild;
        newImage.style.visibility = "visible";
    };

</script>
</html>
