package com.example.balance_game_community.servlet;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.balanceGameVote.Difficulty;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/voteBalanceGameDifficultyServlet")
public class voteBalanceGameDifficultyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        AppConfig testAppConfig = new AppConfig(new DataSource());
        BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();

        Long memberId = (Long) request.getSession().getAttribute("memberId");
        Long balanceGameId = Long.parseLong(request.getParameter("balanceGameId"));
        Difficulty difficulty = Difficulty.valueOf(request.getParameter("difficulty"));
        balanceGameVoteDAO.voteDifficulty(memberId, balanceGameId, difficulty);

        System.out.println("difficulty = " + difficulty.name());
        System.out.println("memberId = " + memberId);
        System.out.println("balanceGameId = " + balanceGameId);

        response.sendRedirect(request.getHeader("referer"));
    }
}
