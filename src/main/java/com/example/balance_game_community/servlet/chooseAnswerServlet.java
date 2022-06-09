package com.example.balance_game_community.servlet;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chooseAnswerServlet")
public class chooseAnswerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        AppConfig testAppConfig = new AppConfig(new DataSource());
        BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();

        Long memberId = (Long) request.getSession().getAttribute("memberId");

        // 로그인이 안되어있는 경우
        if(memberId == null) {
            response.sendRedirect("/");
            return;
        }

        Long balanceGameId = Long.parseLong(request.getParameter("balanceGameId"));
        int answer = Integer.parseInt(request.getParameter("answer"));
        balanceGameVoteDAO.chooseAnswer(memberId, balanceGameId, answer);

        response.sendRedirect(request.getHeader("referer"));
    }
}
