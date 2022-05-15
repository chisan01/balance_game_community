package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.balanceGameVote.Preference;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BalanceGameDAOTest {

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
    void addBalanceGame() {
        Member member = new Member();
        member.setId(10L);
        member.setEmail("test@gmail.com");
        member.setPassword("1234");
        member.setNickname("test");
        memberDAO.signIn(member);

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setId(10L);
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);

        assertEquals(balanceGame.getId(), balanceGameDAO.findById(balanceGame.getId()).getId());
    }

    @Test
    @DisplayName("마지막 밸런스 게임 id 반환")
    public void getLastBalanceGameId() {
        // given
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");
        member.setPassword("1234");
        member.setNickname("test");
        memberDAO.signIn(member);

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);

        BalanceGame balanceGame2 = new BalanceGame();
        balanceGame2.setQuestion("둘 중 하나만 골라야 한다면? 2");
        balanceGame2.setAnswer1("A");
        balanceGame2.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame2);

        BalanceGame balanceGame3 = new BalanceGame();
        balanceGame3.setQuestion("둘 중 하나만 골라야 한다면? 3");
        balanceGame3.setAnswer1("A");
        balanceGame3.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame3);

        assertEquals(3, balanceGameDAO.getLastBalanceGameId());
    }

    @Test
    @DisplayName("현재 게임을 제외한 랜덤한 밸런스 게임 id 반환")
    public void getOtherRandomBalanceGameId() {
        // given
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");
        member.setPassword("1234");
        member.setNickname("test");
        memberDAO.signIn(member);

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setId(1L);
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);

        BalanceGame balanceGame2 = new BalanceGame();
        balanceGame2.setId(2L);
        balanceGame2.setQuestion("둘 중 하나만 골라야 한다면? 2");
        balanceGame2.setAnswer1("A");
        balanceGame2.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame2);

        BalanceGame balanceGame3 = new BalanceGame();
        balanceGame3.setId(3L);
        balanceGame3.setQuestion("둘 중 하나만 골라야 한다면? 3");
        balanceGame3.setAnswer1("A");
        balanceGame3.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame3);

        for (int i = 0; i < 10; i++) {
            assertNotEquals(balanceGame.getId(), balanceGameDAO.getOtherRandomBalanceGameId(balanceGame.getId()));
        }
    }

    @Test
    @DisplayName("특정 밸런스 게임 반환")
    public void findById() {
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

        balanceGameVoteDAO.votePreference(member.getId(), balanceGame.getId(), Preference.DISLIKE);
        balanceGameVoteDAO.votePreference(member2.getId(), balanceGame.getId(), Preference.DISLIKE);
        balanceGameVoteDAO.votePreference(member3.getId(), balanceGame.getId(), Preference.LIKE);
        balanceGameVoteDAO.votePreference(member4.getId(), balanceGame.getId(), Preference.DISLIKE);

        // then
        BalanceGame findBalanceGame = balanceGameDAO.findById(balanceGame.getId());
        assertEquals(findBalanceGame.getLikeNumber(), 1);
        assertEquals(findBalanceGame.getDislikeNumber(), 3);
    }
}