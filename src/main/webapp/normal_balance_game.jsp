<%@ page import="com.example.balance_game_community.AppConfig" %>
<%@ page import="com.example.balance_game_community.member.MemberDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGameDAO" %>
<%@ page import="com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO" %>
<%@ page import="com.example.balance_game_community.DataSource" %>
<%@ page import="com.example.balance_game_community.balanceGame.BalanceGame" %>
<%@ page import="com.example.balance_game_community.balanceGameVote.Difficulty" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    AppConfig appConfig = new AppConfig(new DataSource());
    MemberDAO memberDAO = appConfig.getMemberDAO();
    BalanceGameVoteDAO balanceGameVoteDAO = appConfig.getBalanceGameVoteDAO();
    BalanceGameDAO balanceGameDAO = appConfig.getBalanceGameDAO();
    BalanceGameCommentDAO balanceGameCommentDAO = appConfig.getBalanceGameCommentDAO();
    long maxIndex = balanceGameDAO.getLastBalanceGameId();
    List<BalanceGame> normalBalanceGames;
    normalBalanceGames = balanceGameDAO.findAllByDifficulty(Difficulty.NORMAL);
%>

<span class="svg-border-rounded">
            <!--최신순 정렬-->
        <h2 class="balancegameTitle">좀 더 어려운 밸런스 게임은 없을까?</h2>
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
            int index = 4;
            int flag = 2;
            int ang = 0;
            for (int i = 1; i <= normalBalanceGames.size(); i++) {
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

        <a class="towel-page" href="show_balance_game.jsp?balanceGameId=<%=i%>" style="transform: rotate(<%=ang%>deg)">
            <div class="towel">
                <%
                    Random rand = new Random();
                    int randnum = 0;
                    String[] clothes = {"clothes", "clothes1", "clothes2", "clothes3", "clothes4", "clothes5", "clothes6", "clothes7", "clothes8", "clothes9", "towel"};
                    randnum = rand.nextInt(11);
                    String selectclothes = "./img/" + clothes[randnum] + ".png";
                    int laundryClass = (int)((Math.random()*10)%3)+1;
                    if(laundryClass == 1) {%>
                <image class="laundry_1" src="<%=selectclothes%>" width="450" height="470"></image> <%}

                if(laundryClass == 2) {%>
                <image class="laundry_2" src="<%=selectclothes%>" width="450" height="470"></image> <%}
                if(laundryClass == 3) {%>
                <image class="laundry_3" src="<%=selectclothes%>" width="450" height="470"></image> <%}%>
                <div class="balancegame">
                    <p style="font-size: 22px;"><%=normalBalanceGames.get(normalBalanceGames.size() - i).getQuestion()%>
                    </p>
                    <h4><br/></h4>
                    <p><%=normalBalanceGames.get(normalBalanceGames.size() - i).getAnswer1()%>
                    </p>
                    <p style="color: saddlebrown">vs</p>
                    <p><%=normalBalanceGames.get(normalBalanceGames.size() - i).getAnswer2()%>
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
