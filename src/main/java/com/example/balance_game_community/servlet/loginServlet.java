package com.example.balance_game_community.servlet;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.member.MemberDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        AppConfig testAppConfig = new AppConfig(new DataSource());
        MemberDAO memberDAO = testAppConfig.getMemberDAO();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        PrintWriter script = response.getWriter();
        try {
            Long memberId = memberDAO.logIn(email, password);
            if(memberId == null) return;

            // 임시 계정에 직접적으로 로그인하려고 한 경우
            if(memberDAO.isTempMember(memberId)) {
                script.println("<script>");
                script.println("alert('" + "not allowed access" + "')");
                script.println("history.back()");
                script.println("</script>");
                return;
            }

            request.getSession().setAttribute("memberId", memberId);
            System.out.println("email = " + email);
            System.out.println("password = " + password);
            System.out.println("memberId = " + memberId);

            response.sendRedirect("/index.jsp");
        } catch (Exception e) {
            script.println("<script>");
            script.println("alert('" + e.getMessage() + "')");
            script.println("history.back()");
            script.println("</script>");
        }
    }
}
