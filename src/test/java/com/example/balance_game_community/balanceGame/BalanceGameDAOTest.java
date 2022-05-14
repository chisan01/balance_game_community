package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGameVote.BalanceGameVote;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BalanceGameDAOTest {

    private static MemberDAO memberDAO;
    private static BalanceGameDAO balanceGameDAO;
    private static BalanceGameVoteDAO balanceGameVoteDAO;

    @BeforeAll
    static void init() {
        memberDAO = new MemberDAO(new TestDataSource());
        balanceGameDAO = new BalanceGameDAO(new TestDataSource());
        balanceGameVoteDAO = new BalanceGameVoteDAO(new TestDataSource());
    }

    @AfterEach
    void tearDown() {
        balanceGameDAO.reset();
        balanceGameVoteDAO.reset();
        memberDAO.reset();
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
        BalanceGameResult balanceGameResult = balanceGameDAO.getBalanceGameResult(balanceGame.getId());
        assertEquals(balanceGameResult.getAnswer1voteCount(), 1);
        assertEquals(balanceGameResult.getAnswer2voteCount(), 3);
        assertEquals(balanceGameResult.getAnswer1percent(), 25.0);
        assertEquals(balanceGameResult.getAnswer2percent(), 75.0);
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
}