package com.example.balance_game_community.balanceGameVote;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameDAO;
import com.example.balance_game_community.balanceGame.BalanceGameResult;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BalanceGameVoteDAOTest {

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
    void findByMemberIdAndBalanceGameId() {
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

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setId(10L);
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);

        // when
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame.getId(), 2);

        // then
        assertNotNull(balanceGameVoteDAO.findByMemberIdAndBalanceGameId(member2.getId(), balanceGame.getId()));
        assertNull(balanceGameVoteDAO.findByMemberIdAndBalanceGameId(member.getId(), balanceGame.getId()));
    }

    @Test
    @DisplayName("특정 밸런스 게임의 사람들 선택 결과 반환 & 결과 투표")
    void getBalanceGameResult() {
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
        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame.getId(), 1);
        balanceGameVoteDAO.chooseAnswer(member3.getId(), balanceGame.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member4.getId(), balanceGame.getId(), 2);

        // then
        BalanceGameResult balanceGameResult = balanceGameVoteDAO.getBalanceGameResult(balanceGame.getId());
        assertEquals(balanceGameResult.getAnswer1voteCount(), 1);
        assertEquals(balanceGameResult.getAnswer2voteCount(), 3);
        assertEquals(balanceGameResult.getAnswer1percent(), 25.0);
        assertEquals(balanceGameResult.getAnswer2percent(), 75.0);
    }
}