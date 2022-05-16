package com.example.balance_game_community.balanceGameComment;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameDAO;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.balanceGameVote.Preference;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BalanceGameCommentDAOTest {

    private static AppConfig appTestConfig;
    private static MemberDAO memberDAO;
    private static BalanceGameDAO balanceGameDAO;
    private static BalanceGameVoteDAO balanceGameVoteDAO;
    private static BalanceGameCommentDAO balanceGameCommentDAO;

    @BeforeAll
    static void setUp() {
        appTestConfig = new AppConfig(new TestDataSource());
        memberDAO = appTestConfig.getMemberDAO();
        balanceGameVoteDAO = appTestConfig.getBalanceGameVoteDAO();
        balanceGameDAO = appTestConfig.getBalanceGameDAO();
        balanceGameCommentDAO = appTestConfig.getBalanceGameCommentDAO();
    }

    @AfterEach
    void tearDown() {
        appTestConfig.resetAll();
    }

    @Test
    void findAllByBalanceGameId() throws Exception {
        // given
        Member member = new Member();
        member.setId(2L);
        member.setEmail("test@gmail.com");
        member.setPassword("1234");
        member.setNickname("test");
        memberDAO.signIn(member);

        Member member2 = new Member();
        member2.setId(4L);
        member2.setEmail("test2@gmail.com");
        member2.setPassword("1234");
        member2.setNickname("test2");
        memberDAO.signIn(member2);

        Member member3 = new Member();
        member3.setId(5L);
        member3.setEmail("test3@gmail.com");
        member3.setPassword("1234");
        member3.setNickname("test3");
        memberDAO.signIn(member3);

        Member member4 = new Member();
        member4.setId(6L);
        member4.setEmail("test4@gmail.com");
        member4.setPassword("1234");
        member4.setNickname("test4");
        memberDAO.signIn(member4);

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setId(10L);
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);

        // when
        balanceGameCommentDAO.addComment(member.getId(), balanceGame.getId(), "1번째 댓글");
        balanceGameCommentDAO.addComment(member2.getId(), balanceGame.getId(), "2번째 댓글");
        balanceGameCommentDAO.addComment(member3.getId(), balanceGame.getId(), "3번째 댓글");
        balanceGameCommentDAO.addComment(member4.getId(), balanceGame.getId(), "4번째 댓글");

        // then
        List<BalanceGameComment> balanceGameCommentList = balanceGameCommentDAO.findAllByBalanceGameId(balanceGame.getId());
        assertEquals("4번째 댓글", balanceGameCommentList.get(0).getContent());
        assertEquals("3번째 댓글", balanceGameCommentList.get(1).getContent());
        assertEquals("2번째 댓글", balanceGameCommentList.get(2).getContent());
        assertEquals("1번째 댓글", balanceGameCommentList.get(3).getContent());
    }
}