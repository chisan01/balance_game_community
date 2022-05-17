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

@WebServlet(name = "FileUploadServlet", value = "/FileUploadServlet")
@MultipartConfig(maxFileSize = 16177216) // 파일 크기 최대 16MB
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AppConfig testAppConfig = new AppConfig(new TestDataSource());
        MemberDAO memberDAO = testAppConfig.getMemberDAO();
        BalanceGameVoteDAO balanceGameVoteDAO = testAppConfig.getBalanceGameVoteDAO();
        BalanceGameDAO balanceGameDAO = testAppConfig.getBalanceGameDAO();
        BalanceGameCommentDAO balanceGameCommentDAO = testAppConfig.getBalanceGameCommentDAO();
        testAppConfig.resetAll();

        Member member = new Member();
        member.setId(1L);
        member.setPassword("1234");
        member.setEmail("test@gmail.com");
        member.setNickname("test");
        memberDAO.signIn(member);

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setId(1L);
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGame.setPicture1(request.getParameter("picture1"));
        balanceGame.setPicture2(request.getParameter("picture2"));

        // 이미지 저장용 폴더가 존재하지 않는 경우, 해당 폴더 생성
        File imageFolder = new File(AppConfig.IMAGE_FOLDER_PATH);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }

        // 첫번째 사진 저장
        Part filePart = request.getPart("picture1");
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            String fileName = member.getId() + LocalDateTime.now().toString().trim().replaceAll("[:.]", "-") + "1.png";
            File file = new File(AppConfig.IMAGE_FOLDER_PATH + "//" + fileName);
            copyInputStreamToFile(inputStream, file);
            balanceGame.setPicture1(fileName);
        }

        // 두번째 사진 저장
        Part filePart2 = request.getPart("picture2");
        if (filePart2 != null) {
            InputStream inputStream2 = filePart2.getInputStream();
            String fileName2 = member.getId() + LocalDateTime.now().toString().trim().replaceAll("[:.]", "-") + "2.png";
            File file2 = new File(AppConfig.IMAGE_FOLDER_PATH + "//" + fileName2);
            copyInputStreamToFile(inputStream2, file2);
            balanceGame.setPicture2(fileName2);
        }

        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);
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
