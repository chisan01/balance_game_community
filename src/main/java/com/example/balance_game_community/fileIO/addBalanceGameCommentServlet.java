package com.example.balance_game_community.fileIO;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameDAO;
import com.example.balance_game_community.balanceGameComment.BalanceGameComment;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@WebServlet("/addBalanceGameCommentServlet")
public class addBalanceGameCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // TODO 로그인 기능 구현 후 : TestDataSource -> DataSource
        AppConfig testAppConfig = new AppConfig(new TestDataSource());
        MemberDAO memberDAO = testAppConfig.getMemberDAO();
        BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();
        BalanceGameDAO balanceGameDAO = testAppConfig.getBalanceGameDAO();
        BalanceGameCommentDAO balanceGameCommentDAO = testAppConfig.getBalanceGameCommentDAO();

        Long memberId = (Long) request.getSession().getAttribute("memberId");
        Long balanceGameId = Long.parseLong(request.getParameter("balanceGameId"));
        String content = request.getParameter("content");
        balanceGameCommentDAO.addComment(memberId, balanceGameId, content);

        System.out.println("content = " + content);
        System.out.println("memberId = " + memberId);
        System.out.println("balanceGameId = " + balanceGameId);

        response.sendRedirect(request.getHeader("referer"));
    }
}
