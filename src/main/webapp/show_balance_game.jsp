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
                    <p><br/></p>
                    <a href="create_balance_game.html">글쓰기</a>
                    <a href="index.jsp">오늘의 밸런스게임</a>
                    <a href="create_balance_game.html">인기순 밸런스게임</a>
                    <a href="create_balance_game.html">최신순 밸런스게임</a>
                    <a href="create_balance_game.html">난이도별 밸런스게임</a>
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
            Long balanceGameId = Long.parseLong(request.getParameter("balanceGameId"));
            BalanceGame balanceGame = balanceGameDAO.findById(balanceGameId);

            Long balanceGameVoteId = balanceGameVoteDAO.findByMemberIdAndBalanceGameId(memberId, balanceGameId);
            BalanceGameVote balanceGameVote = balanceGameVoteDAO.findById(balanceGameVoteId);
        %>

        <div id="newWriting">
            <!--사진 내부-->
            <div class="game-result">
                <!--제목-->
                <div class="bgtitle-w" style="height: 100px; top: 0;">
                </div>
                <div class="bgtitle" style="top: 0;">
                    <p style="font-size: 50px;"><%=balanceGame.getQuestion()%>
                    </p>
                    <div style="padding-left: 40px; padding-top: 5px; display: flex;">
                        <p style="font-size: 40px; padding-right: 15px;">난이도:  </p>
                        <img src="image/difficulty.png" width="40px" height="40px" alt=""/>
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
                       style="background-color: white">
                        이전 게임</a>
                    <a href="show_balance_game.jsp?balanceGameId=<%=balanceGameDAO.getOtherRandomBalanceGameId(balanceGameId)%>"
                       style="background-color: white">다음
                        게임</a>
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
                        <div class="result-explanation-font" style="font-size: 100px;">
                            <p><%=balanceGameResult.getAnswer1percent()%> %</p>
                            <p><%=balanceGameResult.getAnswer2percent()%> %</p>
                        </div>
                        <!-- 선택한 사람 수 -->
                        <div class="result-explanation-font" style="font-size: 90px;">
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
                <div class="bgtitle" style="top: 700px; padding-top: 50px;">
                    <%
                        if (balanceGameVote != null) {
                            BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
                            if (balanceGameVote.getAnswerNumber() == 1) {
                    %>
                    <div class="result-explanation-font" style="font-size: 50px; color: #4d5058;">
                        <p><%=balanceGame.getAnswer1()%>
                        </p>
                        <p><%=balanceGame.getAnswer2()%>
                        </p>
                    </div>
                    <%
                    } else {
                    %>
                    <div class="result-explanation-font" style="font-size: 50px; color: #4d5058;">
                        <p><%=balanceGame.getAnswer1()%>
                        </p>
                        <p><%=balanceGame.getAnswer2()%>
                        </p>
                    </div>
                    <%
                        }

                    } else {  //투표 안한 경우
                    %>

                    <form action="/chooseAnswerServlet" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>

                        <div class="result-explanation-font" style="font-size: 50px; color: #4d5058;">
                            <input type="radio" name="answer" value="1"><%=balanceGame.getAnswer1()%>
                            <input type="radio" name="answer" value="2"><%=balanceGame.getAnswer2()%>
                        </div>

                        <button type="submit">선택 완료</button>
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
                <form action="/voteBalanceGamePreferenceServlet" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                    <div class="post-feedback-buttons">
                        <div class="like-btn">
                            <%
                                if (balanceGameVote.getPreference() != null && balanceGameVote.getPreference().equals(Preference.LIKE)) {
                            %>
                            <input type="radio" name="preference" value="<%=Preference.LIKE.name()%>"
                                   checked="checked">좋아요
                            <%
                            } else {
                            %>
                            <input type="radio" name="preference" value="<%=Preference.LIKE.name()%>" >좋아요
                            <%
                                }
                            %>
                        </div>
                        <div class="dislike-btn">
                            <%
                                if (balanceGameVote.getPreference() != null && balanceGameVote.getPreference().equals(Preference.DISLIKE)) {
                            %>
                            <input type="radio" name="preference" value="<%=Preference.DISLIKE.name()%>"
                                   checked="checked">싫어요
                            <%
                            } else {
                            %>
                            <input type="radio" name="preference" value="<%=Preference.DISLIKE.name()%>">싫어요
                            <%
                                }
                            %>
                        </div>

                    </div>
                    <button type="submit" >좋아요/싫어요 투표</button>
                </form>
                <%
                    } else { }  //사용자가 아직 투표하지 않은 경우
                %>
            </div>

            <%-- 난이도 투표--%>
            <%-- TODO 이미 투표한 경우 투표한 위치에 표시--%>
            <%
                if (balanceGameVote != null) {
                    BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
            %>
            <div class="difficulty">
                <p>난이도 선택 : </p>
                <form action="/voteBalanceGameDifficultyServlet" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                    <div class="post-difficulty-buttons">
                        <div class="difficulty-level">
                            <%
                                if (balanceGameVote.getDifficulty() != null && balanceGameVote.getDifficulty().equals(Difficulty.HARD)) {
                            %>
                            <input type="radio" name="difficulty" value="<%=Difficulty.HARD.name()%>"
                                   checked="checked">상
                            <%
                            } else {
                            %>
                            <input type="radio" name="difficulty" value="<%=Difficulty.HARD.name()%>">상
                            <%
                                }
                            %>
                        </div>
                        <div class="difficulty-level">
                            <%
                                if (balanceGameVote.getDifficulty() != null && balanceGameVote.getDifficulty().equals(Difficulty.NORMAL)) {
                            %>
                            <input type="radio" name="difficulty" value="<%=Difficulty.NORMAL.name()%>"
                                   checked="checked">중
                            <%
                            } else {
                            %>
                            <input type="radio" name="difficulty" value="<%=Difficulty.NORMAL.name()%>">중
                            <%
                                }
                            %>
                        </div>
                        <div class="difficulty-level">
                            <%
                                if (balanceGameVote.getDifficulty() != null && balanceGameVote.getDifficulty().equals(Difficulty.EASY)) {
                            %>
                            <input type="radio" name="difficulty" value="<%=Difficulty.EASY.name()%>"
                                   checked="checked">하
                            <%
                            } else {
                            %>
                            <input type="radio" name="difficulty" value="<%=Difficulty.EASY.name()%>">하
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <button type="submit">난이도 투표</button>
                </form>
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
                        <input class="data" type="text" name="content" placeholder="댓글 입력" style="height: 100px;" />
                    </label>
                    <button type="submit" style="width: 100%; height: 30px;">확인 ( Enter )</button>
                </form>
                <%
                    }
                %>
            </div>

            <%--            댓글 출력    --%>
            <%
                List<BalanceGameComment> balanceGameComments = balanceGameCommentDAO.findAllByBalanceGameId(balanceGameId);
            %>
            <div class="show-comment">
                <table class="commentTable" border="1" width="100%" >
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