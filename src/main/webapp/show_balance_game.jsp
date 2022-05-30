<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.TestDataSource" %>
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
            AppConfig testAppConfig = new AppConfig(new TestDataSource());
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
            <table border="1">
                <tr height="50px">
                    <td colspan="4"><%=balanceGame.getQuestion()%>
                    </td>
                </tr>
                <tr height="50px">
                    <td colspan="2">Answer 1</td>
                    <td colspan="2">Answer 2</td>
                </tr>
                <tr height="100px">
                    <td colspan="2"><img src="/files/<%=balanceGame.getAnswer1PictureUrl()%>" alt="picture1"
                                         width="100px"></td>
                    <td colspan="2"><img src="/files/<%=balanceGame.getAnswer2PictureUrl()%>" alt="picture2"
                                         width="100px"></td>
                </tr>
                <tr height="100px">
                    <%
                        if (balanceGameVote != null) {
                            BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
                            if (balanceGameVote.getAnswerNumber() == 1) {
                    %>
                    <td colspan="2" bgcolor="#ff8c00"><%=balanceGame.getAnswer1()%>
                    </td>
                    <td colspan="2"><%=balanceGame.getAnswer2()%>
                    </td>
                    <%
                    } else {
                    %>
                    <td colspan="2"><%=balanceGame.getAnswer1()%>
                    </td>
                    <td colspan="2" bgcolor="#ff8c00"><%=balanceGame.getAnswer2()%>
                    </td>
                    <%
                        }
                    } else {
                    %>
                    <td colspan="4">
                        <form action="/chooseAnswerServlet" method="post" accept-charset="UTF-8">
                            <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                            <ul style="list-style: none;">
                                <li class="nav-item">
                                    <input type="radio" name="answer" value="1"><%=balanceGame.getAnswer1()%>
                                </li>
                                <li class="nav-item">
                                    <input type="radio" name="answer" value="2"><%=balanceGame.getAnswer2()%>
                                </li>
                            </ul>
                            <button type="submit">선택 완료</button>
                        </form>
                    </td>
                    <%
                        }
                    %>
                </tr>

                <%--            게임 결과 출력--%>
                <%
                    if (balanceGameVote != null) {
                        BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
                %>
                <tr height="100px">
                    <td colspan="2"><%=balanceGameResult.getAnswer1percent()%>%</td>
                    <td colspan="2"><%=balanceGameResult.getAnswer2percent()%>%</td>
                </tr>
                <tr height="50px">
                    <td colspan="2"><%=balanceGameResult.getAnswer1voteCount()%>
                    </td>
                    <td colspan="2"><%=balanceGameResult.getAnswer2voteCount()%>
                    </td>
                </tr>

                <%--            난이도 투표--%>
                <%--            TODO 이미 투표한 경우 투표한 위치에 표시--%>
                <tr>
                    <td colspan="4"><p>난이도 선택 : </p>
                        <form action="/voteBalanceGameDifficultyServlet" method="post" accept-charset="UTF-8">
                            <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                            <ul style="list-style: none;">
                                <li class="nav-item">
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
                                </li>
                                <li class="nav-item">
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
                                </li>
                                <li class="nav-item">
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
                                </li>
                            </ul>
                            <button type="submit">난이도 투표</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
                <%--                이전/다음 질문. 좋아요/싫어요 투표 --%>
                <tr>
                    <td>
                        <a href="show_balance_game.jsp?balanceGameId=<%=balanceGameDAO.getOtherRandomBalanceGameId(balanceGameId)%>">
                            이전 게임</a>
                    </td>
                    <%
                        if (balanceGameVote != null) {
                    %>
                    <%--                    TODO 이미 투표한 경우 투표한 위치에 표시--%>
                    <td colspan="2"><p>좋아요/싫어요 투표</p>
                        <form action="/voteBalanceGamePreferenceServlet" method="post" accept-charset="UTF-8">
                            <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                            <ul style="list-style: none;">
                                <li class="nav-item">
                                    <%
                                        if (balanceGameVote.getPreference() != null && balanceGameVote.getPreference().equals(Preference.LIKE)) {
                                    %>
                                    <input type="radio" name="preference" value="<%=Preference.LIKE.name()%>"
                                           checked="checked">좋아요
                                    <%
                                    } else {
                                    %>
                                    <input type="radio" name="preference" value="<%=Preference.LIKE.name()%>">좋아요
                                    <%
                                        }
                                    %>
                                </li>
                                <li class="nav-item">
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
                                </li>
                            </ul>
                            <button type="submit">좋아요/싫어요 투표</button>
                        </form>
                    </td>
                    <%
                    } else {
                    %>
                    <td colspan="2"></td>
                    <%
                        }
                    %>
                    <td>
                        <a href="show_balance_game.jsp?balanceGameId=<%=balanceGameDAO.getOtherRandomBalanceGameId(balanceGameId)%>">다음
                            게임</a>
                    </td>
                </tr>

                <%--            댓글 작성    --%>
                <%
                    if (balanceGameVote != null) {
                        BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGameId);
                %>
                <tr height="100px">
                    <td colspan="4">
                        <form action="/addBalanceGameCommentServlet" method="post" accept-charset="UTF-8">
                            <input type="hidden" name="balanceGameId" value="<%=balanceGameId%>"/>
                            <label>
                                <input class="data" type="text" name="content" placeholder="댓글을 입력하세요."/>
                            </label>
                            <button type="submit">댓글 작성</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>

                <%--            댓글 출력    --%>
                <%
                    List<BalanceGameComment> balanceGameComments = balanceGameCommentDAO.findAllByBalanceGameId(balanceGameId);
                %>
                <tr height="50px">
                    <td colspan="4">댓글 : <%=balanceGameComments.size()%>개</td>
                </tr>
                <%
                    for (BalanceGameComment balanceGameComment : balanceGameComments) {
                %>
                <tr height="50px">
                    <td colspan="4"><%=balanceGameComment.getContent()%>
                    </td>
                </tr>
                <%
                    }
                %>
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
