<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVote" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameResult" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameComment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.Difficulty" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.Preference" %>
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
    <title>밸런스 게임</title>
    <link href="css/home_styles.css" rel="stylesheet"/>
</head>
<body>
<div id="layoutDefault">
    <main>
        <!--nav bar -->
        <nav class="navbar">
            <div class="nav-container">
                <a class="navbar-brand" href="index.jsp">세모밸</a>
                <div class="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <img src="image/menu_btn.png" width="65px" height="65px"
                             style="position: absolute; top:-5px; right: 110px; opacity: 70%; z-index:101;"/>
                        <li class="nav-item">
                            <!--방울 메뉴창(마이페이지, 글쓰기 등) 띄우는 링크? -->
                            <a class="cloudbtn" href="#redirect" style="top:23px;">메뉴</a>
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
                    <h1><< 메뉴 >></h1>
                    <a href="create_balance_game.jsp">글쓰기</a>
                    <a href="index.jsp">오늘의 밸런스게임</a>
                    <a href="sort_balance_game_by_like.jsp">인기순 밸런스게임</a>
                    <a href="sort_balance_game_by_newest.jsp">최신순 밸런스게임</a>
                    <a href="sort_balance_game_by_difficulty.jsp">난이도별 밸런스게임</a>
                </div>
            </div>
            <div class="bubble x4">
                <img src="image/white%20x.png" width="100px" height="100px" style="opacity: 70%;" alt=""/>
            </div>
        </div>

        <%
            AppConfig testAppConfig = new AppConfig(new DataSource());
            MemberDAO memberDAO = testAppConfig.getMemberDAO();
            BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();
            BalanceGameDAO balanceGameDAO = testAppConfig.getBalanceGameDAO();
            BalanceGameCommentDAO balanceGameCommentDAO = testAppConfig.getBalanceGameCommentDAO();

            Long memberId = (Long) request.getSession().getAttribute("memberId");

            // 로그인이 안되어있는 경우
            if (memberId == null) {
                response.sendRedirect("/");
                return;
            }

            Long balanceGameId = Long.parseLong(request.getParameter("balanceGameId"));
            BalanceGame balanceGame = balanceGameDAO.findById(balanceGameId);

            Long balanceGameVoteId = balanceGameVoteDAO.findByMemberIdAndBalanceGameId(memberId, balanceGameId);
            BalanceGameVote balanceGameVote = balanceGameVoteDAO.findById(balanceGameVoteId);
        %>

        <!--위로 바로가기-->
        <nav >
            <img class="go_up_btn" src="image/go_up_btn.png" width="100px" height="100px" />
            <a id="go_up" href="#">맨 위로</a>
        </nav>

        <div id="newWriting" style="top:60px;">
            <!--사진 내부-->
            <div class="game-result">
                <!--제목-->
                <div class="bgtitle-w" style="height: 100px; top: 0;">
                </div>
                <div class="bgtitle" style="top: 0;">
                    <p style="font-size: 50px;"><%=balanceGame.getQuestion()%>
                    </p>
                    <div style="padding-left: 40px; padding-top: 5px; display: flex;">
                        <p style="font-size: 40px; padding-right: 15px;">난이도: </p>
                        <img src="image/difficulty.png" width="40px" height="40px" alt=""/>
                        <%
                            if (balanceGame.getDifficulty() == Difficulty.NORMAL) {
                                %>
                        <img src="image/difficulty.png" width="40px" height="40px" alt=""/>
                        <%
                            } else if (balanceGame.getDifficulty() == Difficulty.HARD) {
                        %>
                        <img src="image/difficulty.png" width="40px" height="40px" alt=""/>
                        <img src="image/difficulty.png" width="40px" height="40px" alt=""/>
                        <%
                            }
                        %>
                    </div>
                </div>

                <!--사진-->
                <div class="create-game-vs">
                    <img src="/files/<%=balanceGame.getAnswer1PictureUrl()%>" alt="picture1"
                         width="950px" height="900px">
                    <img src="/files/<%=balanceGame.getAnswer2PictureUrl()%>" alt="picture2"
                         width="950px" height="900px">
                </div>

                <!--이전 질문, 다음 질문-->
                <div class="next">
                    <a href="show_balance_game.jsp?balanceGameId=<%=balanceGameDAO.getOtherRandomBalanceGameId(balanceGameId)%>"
                       >
                        <img src="image/back_svg.svg" width="80px" height="80px"/></a>
                    <a href="show_balance_game.jsp?balanceGameId=<%=balanceGameDAO.getOtherRandomBalanceGameId(balanceGameId)%>"
                       ><img src="image/next_svg.svg" width="80px" height="80px"/></a>
                </div>

                <%--게임 결과 출력--%>
                <%
                    if (balanceGameVote != null) {
                        BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);

                %>
                <div class="darkness" style="background: #000000; opacity: 0.6;"></div>
                <div class="result-explanation" style="opacity: 1; transform: scale(1);">
                    <div class="create-game-answer">
                        <!-- 선택 비율 -->
                        <div class="result-content-font" style="font-size: 100px;">
                            <p><%=balanceGameResult.getAnswer1percent()%> %</p>
                            <p><%=balanceGameResult.getAnswer2percent()%> %</p>
                        </div>
                        <!-- 선택한 사람 수 -->
                        <div class="result-content-font" style="font-size: 90px; top: 450px;">
                            <p><%=balanceGameResult.getAnswer1voteCount()%> 명
                            </p>
                            <p><%=balanceGameResult.getAnswer2voteCount()%> 명
                            </p>
                        </div>
                    </div>
                </div>
                <%}%>

                <div class="bgtitle-w" style=" height: 200px; top: 700px;">
                </div>
                <div class="result-content">
                    <%
                        if (balanceGameVote != null) {
                            BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
                            if (balanceGameVote.getAnswerNumber() == 1) {
                    %>
                    <div class="result-explanation-font">
                        <p><%=balanceGame.getAnswer1()%>
                        </p>
                        <p><%=balanceGame.getAnswer2()%>
                        </p>
                    </div>
                    <%
                    } else {
                    %>
                    <div class="result-explanation-font">
                        <p><%=balanceGame.getAnswer1()%>
                        </p>
                        <p><%=balanceGame.getAnswer2()%>
                        </p>
                    </div>
                    <%
                        }

                    } else {  //투표 안한 경우
                    %>

                    <div class="result-explanation-font" style="font-size: 50px; color: #4d5058;">
                        <p><%=balanceGame.getAnswer1()%>
                        </p>
                        <p><%=balanceGame.getAnswer2()%>
                        </p>
                    </div>
                    <form action="/chooseAnswerServlet" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>

                        <div class="chooseAnswer">
                            <input id="chooseAnswer1" type="submit" name="answer" value="1"
                                   style="width: 1035px; height: 900px; z-index: 100; opacity: 0.1;">
                            <input id="chooseAnswer2" type="submit" name="answer" value="2"
                                   style="width: 1035px; height: 900px; z-index: 100; opacity: 0.1;">
                        </div>
                    </form>
                </div>

                <%
                    }
                %>
            </div>
        </div>


        <!--게임 결과 출력 -->
        <div class="show-game-feedback">
            <%--좋아요/싫어요 투표 --%>
            <%
                if (balanceGameVote != null) {
            %>
            <%--                    TODO 이미 투표한 경우 투표한 위치에 표시--%>
            <div class="preference">
                <div class="preference-btn">
                    <div class="preference-icon" id="preference-icon-good">
                        <img src="image/good_icon.png" width="40px" height="40px"/>
                        <p>좋아요</p>
                    </div>
                    <div class="preference-icon" id="preference-icon-bad">
                        <img src="image/bad_icon.png" width="40px" height="40px"/>
                        <p>싫어요</p>
                    </div>
                </div>
                <div class="preference-btn" style="top:-80px">
                    <form action="/voteBalanceGamePreferenceServlet" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                        <div class="choosepref">
                            <div class="like-btn">
                                <%
                                    if (balanceGameVote.getPreference() != null && balanceGameVote.getPreference().equals(Preference.LIKE)) {
                                %>
                                <script>
                                    var target= document.getElementById("preference-icon-good");
                                    target.style.backgroundColor="#AAD361";
                                    target.style.opacity="80%";
                                </script>
                                <input type="submit" name="preference" value="<%=Preference.LIKE.name()%>"
                                       checked="checked" style="width: 140px; height: 70px; z-index: 100; opacity: 0%;" >
                                <%
                                } else {
                                %>
                                <input type="submit" name="preference" value="<%=Preference.LIKE.name()%>"
                                       style="width: 140px; height: 70px; z-index: 100; opacity: 0;">
                                <%
                                    }
                                %>
                            </div>
                            <div class="dislike-btn">
                                <%
                                    if (balanceGameVote.getPreference() != null && balanceGameVote.getPreference().equals(Preference.DISLIKE)) {
                                %>
                                <script>
                                    var target= document.getElementById("preference-icon-bad");
                                    target.style.backgroundColor="#E33C30";
                                    target.style.opacity="90%";
                                </script>
                                <input type="submit" name="preference" value="<%=Preference.DISLIKE.name()%>"
                                       checked="checked" style="width: 140px; height: 70px; z-index: 100; opacity: 0;">
                                <%
                                } else {
                                %>
                                <input type="submit" name="preference" value="<%=Preference.DISLIKE.name()%>"
                                       style="width: 140px; height: 70px; z-index: 100; opacity: 0;">
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </form>
                    <%
                        } else {
                        }  //사용자가 아직 투표하지 않은 경우
                    %>
                </div>
            </div>


            <%-- 난이도 투표--%>
            <%-- TODO 이미 투표한 경우 투표한 위치에 표시--%>
            <%
                if (balanceGameVote != null) {
                    BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
            %>
            <div class="difficulty">
                <div class="difficulty-btn">
                    <p>난이도 선택 : </p>
                    <div class="difficulty-icon" id="difficult-easy">
                        <img src="image/difficult_icon.png" width="25px" height="25px"/>
                        <p>어려워요</p>
                    </div>
                    <div class="difficulty-icon" id="difficult-middle" >
                        <img src="image/middle_icon.png" width="25px" height="25px"/>
                        <p>보통이에요</p>
                    </div>
                    <div class="difficulty-icon" id="difficult-hard">
                        <img src="image/easy_icon.png" width="25px" height="25px"/>
                        <p>쉬워요</p>
                    </div>
                </div>

                <div class="difficulty-btn">
                    <form action="/voteBalanceGameDifficultyServlet" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                        <div class="choosediff">
                            <div class="difficulty-level">
                                <%
                                    if (balanceGameVote.getDifficulty() != null && balanceGameVote.getDifficulty().equals(Difficulty.HARD)) {
                                %>
                                <script>
                                    var target= document.getElementById("difficult-easy");
                                    target.style.backgroundColor="#F4E6A2";
                                    target.style.opacity="80%";
                                </script>
                                <input type="submit" name="difficulty" value="<%=Difficulty.HARD.name()%>"
                                       checked="checked" style="width: 100px; height: 40px; z-index: 100; opacity: 0;">
                                <%
                                } else {
                                %>
                                <input type="submit" name="difficulty" value="<%=Difficulty.HARD.name()%>"  style="width: 100px; height: 40px; z-index: 100; opacity: 0;">
                                <%
                                    }
                                %>
                            </div>
                            <div class="difficulty-level">
                                <%
                                    if (balanceGameVote.getDifficulty() != null && balanceGameVote.getDifficulty().equals(Difficulty.NORMAL)) {
                                %>
                                <script>
                                    var target= document.getElementById("difficult-middle");
                                    target.style.backgroundColor="#F4E6A2";
                                    target.style.opacity="80%";
                                </script>
                                <input type="submit" name="difficulty" value="<%=Difficulty.NORMAL.name()%>"
                                       checked="checked"  style="width: 100px; height: 40px; z-index: 100; opacity: 0;">
                                <%
                                } else {
                                %>
                                <input type="submit" name="difficulty" value="<%=Difficulty.NORMAL.name()%>"  style="width: 100px; height: 40px; z-index: 100; opacity: 0;">
                                <%
                                    }
                                %>
                            </div>
                            <div class="difficulty-level">
                                <%
                                    if (balanceGameVote.getDifficulty() != null && balanceGameVote.getDifficulty().equals(Difficulty.EASY)) {
                                %>
                                <script>
                                    var target= document.getElementById("difficult-hard");
                                    target.style.backgroundColor="#F4E6A2";
                                    target.style.opacity="80%";
                                </script>
                                <input type="submit" name="difficulty" value="<%=Difficulty.EASY.name()%>"
                                       checked="checked" style="width: 100px; height: 40px; z-index: 100; opacity: 0;">
                                <%
                                } else {
                                %>
                                <input type="submit" name="difficulty" value="<%=Difficulty.EASY.name()%>" style="width: 100px; height: 40px; z-index: 100; opacity: 0;">
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </form>
                </div>
                <%
                    }
                %>
            </div>

            <%--            댓글 작성    --%>
            <%
                if (balanceGameVote != null) {
                    BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
            %>
            <div class="create-comment">
                <form action="/addBalanceGameCommentServlet" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                    <label>
                        <input class="data" type="text" name="content" placeholder="댓글 입력"
                               style="height: 100px; border-bottom-left-radius: 0px; border-bottom-right-radius:0px; opacity: 80%;"/>
                    </label>
                    <button type="submit" style="width: 100%; height: 40px; border: 0px; border-bottom-right-radius: 10px; border-bottom-left-radius: 10px;">확인 ( Enter )</button>
                </form>
                <%
                    }
                %>
            </div>

            <%--            댓글 출력    --%>
            <%
                List<BalanceGameComment> balanceGameComments = balanceGameCommentDAO.findAllByBalanceGameId(balanceGameId);
                if (balanceGameVote != null) {
                    BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
            %>
            <div class="show-comment">
                <table class="commentTable" border="1" width="100%">
                    <tr height="50px" style="border-left: none; border-right: none;">
                        <td style="border-left: none; border-right: none;">댓글 : <%=balanceGameComments.size()%>개</td>
                    </tr>
                    <tr height="100%">
                        <%
                            for (BalanceGameComment balanceGameComment : balanceGameComments) {
                        %>
                        <td style="padding: 30px; padding-left: 10px; border-left: none; border-right: none; border-bottom: none;"><%=balanceGameComment.getContent()%>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>

            <% } else { %>
            <div class="layout-center">
                <div class="layout-width">
                    <div class="show-comment">
                        <table class="commentTable" border="1" width="100%">
                            <tr height="50px" style="border-left: none; border-right: none;">
                                <td style="border-left: none; border-right: none;">댓글 : <%=balanceGameComments.size()%>개
                                </td>
                            </tr>
                            <tr height="100%">
                                <%
                                    for (BalanceGameComment balanceGameComment : balanceGameComments) {
                                %>
                                <td style="padding: 30px; padding-left: 10px; border-left: none; border-right: none; border-bottom: none;"><%=balanceGameComment.getContent()%>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>

            <%
                }%>
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