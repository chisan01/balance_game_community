package com.example.balance_game_community.fileIO;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameDAO;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@WebServlet("/addBalanceGameServlet")
@MultipartConfig(maxFileSize = 16177216) // 파일 크기 최대 16MB
public class addBalanceGameServlet extends HttpServlet {
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
//        testAppConfig.resetAll();

        Long memberId = (Long) request.getSession().getAttribute("memberId");

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setQuestion(request.getParameter("question"));
        balanceGame.setAnswer1(request.getParameter("answer1"));
        balanceGame.setAnswer2(request.getParameter("answer2"));

        // 이미지 저장용 폴더가 존재하지 않는 경우, 해당 폴더 생성
        File imageFolder = new File(AppConfig.IMAGE_FOLDER_PATH);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }

        // 첫번째 사진 저장
        Part filePart = request.getPart("picture1");
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            String fileName = memberId + LocalDateTime.now().toString().trim().replaceAll("[:.]", "-") + "1.png";
            File file = new File(AppConfig.IMAGE_FOLDER_PATH + "//" + fileName);
            copyInputStreamToFile(inputStream, file);
            balanceGame.setAnswer1PictureUrl(fileName);
        }

        // 두번째 사진 저장
        Part filePart2 = request.getPart("picture2");
        if (filePart2 != null) {
            InputStream inputStream2 = filePart2.getInputStream();
            String fileName2 = memberId + LocalDateTime.now().toString().trim().replaceAll("[:.]", "-") + "2.png";
            File file2 = new File(AppConfig.IMAGE_FOLDER_PATH + "//" + fileName2);
            copyInputStreamToFile(inputStream2, file2);
            balanceGame.setAnswer2PictureUrl(fileName2);
        }

        balanceGameDAO.addBalanceGame(memberId, balanceGame);

        RequestDispatcher rd = request.getRequestDispatcher("/show_balance_game.jsp?balanceGameId=" + balanceGameDAO.getLastBalanceGameId());
        rd.forward(request, response);
    }

    // file system에 저장
    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[8192];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
