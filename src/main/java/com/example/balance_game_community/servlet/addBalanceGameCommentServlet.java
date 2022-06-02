package com.example.balance_game_community.servlet;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addBalanceGameCommentServlet")
public class addBalanceGameCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        AppConfig testAppConfig = new AppConfig(new DataSource());
        BalanceGameCommentDAO balanceGameCommentDAO = testAppConfig.getBalanceGameCommentDAO();

        Long memberId = (Long) request.getSession().getAttribute("memberId");

        // 로그인이 안되어있는 경우
        if(memberId == null) response.sendRedirect("/");

        Long balanceGameId = Long.parseLong(request.getParameter("balanceGameId"));
        String content = request.getParameter("content");
        balanceGameCommentDAO.addComment(memberId, balanceGameId, content);

        response.sendRedirect(request.getHeader("referer"));
    }
}
