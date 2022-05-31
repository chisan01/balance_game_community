package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.AppConfig;
import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.balanceGameVote.Difficulty;
import com.example.balance_game_community.balanceGameVote.Preference;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;
import org.junit.jupiter.api.*;

import java.util.List;

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
    void addBalanceGame() throws Exception {
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
    public void getLastBalanceGameId() throws Exception {
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
    public void getOtherRandomBalanceGameId() throws Exception {
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
    @DisplayName("findById() 좋아요/싫어요 개수 확인")
    public void findById() throws Exception {
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
        assertEquals(1, findBalanceGame.getLikeCount());
        assertEquals(3, findBalanceGame.getDislikeCount());
    }

    @Test
    public void findAllByDifficulty() throws Exception {
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

        BalanceGame balanceGame2 = new BalanceGame();
        balanceGame2.setId(11L);
        balanceGame2.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame2.setAnswer1("A");
        balanceGame2.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame2);

        BalanceGame balanceGame3 = new BalanceGame();
        balanceGame3.setId(12L);
        balanceGame3.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame3.setAnswer1("A");
        balanceGame3.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame3);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame.getId(), Difficulty.EASY);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame.getId(), Difficulty.EASY);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame2.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame2.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame2.getId(), Difficulty.NORMAL);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame2.getId(), Difficulty.NORMAL);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame3.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame3.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame3.getId(), Difficulty.HARD);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame3.getId(), Difficulty.HARD);

        // when
        List<BalanceGame> easyBalanceGameList = balanceGameDAO.findAllByDifficulty(Difficulty.EASY);
        List<BalanceGame> normalBalanceGameList = balanceGameDAO.findAllByDifficulty(Difficulty.NORMAL);
        List<BalanceGame> hardBalanceGameList = balanceGameDAO.findAllByDifficulty(Difficulty.HARD);

        // then
        assertEquals(balanceGame.getId(), easyBalanceGameList.get(0).getId());
        assertEquals(balanceGame2.getId(), normalBalanceGameList.get(0).getId());
        assertEquals(balanceGame3.getId(), hardBalanceGameList.get(0).getId());
    }

    @Test
    @DisplayName("난이도 반올림 계산 확인")
    public void findAllByDifficulty2() throws Exception {
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
        member3.setId(6L);
        member3.setEmail("test3@gmail.com");
        member3.setPassword("1234");
        member3.setNickname("test3");
        memberDAO.signIn(member3);

        BalanceGame balanceGame = new BalanceGame();
        balanceGame.setId(10L);
        balanceGame.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame.setAnswer1("A");
        balanceGame.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame.getId(), 1);
        balanceGameVoteDAO.chooseAnswer(member3.getId(), balanceGame.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame.getId(), Difficulty.EASY);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame.getId(), Difficulty.HARD);
        balanceGameVoteDAO.voteDifficulty(member3.getId(), balanceGame.getId(), Difficulty.HARD);

        // when
        List<BalanceGame> normalBalanceGameList = balanceGameDAO.findAllByDifficulty(Difficulty.NORMAL);

        // then
        assertEquals(balanceGame.getId(), normalBalanceGameList.get(0).getId());
    }

    @Test
    public void findAll() throws Exception {
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

        BalanceGame balanceGame2 = new BalanceGame();
        balanceGame2.setId(11L);
        balanceGame2.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame2.setAnswer1("A");
        balanceGame2.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame2);

        BalanceGame balanceGame3 = new BalanceGame();
        balanceGame3.setId(12L);
        balanceGame3.setQuestion("둘 중 하나만 골라야 한다면?");
        balanceGame3.setAnswer1("A");
        balanceGame3.setAnswer2("B");
        balanceGameDAO.addBalanceGame(member.getId(), balanceGame3);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame.getId(), Difficulty.HARD);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame.getId(), Difficulty.HARD);
        balanceGameVoteDAO.votePreference(member2.getId(), balanceGame.getId(), Preference.LIKE);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame2.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame2.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame2.getId(), Difficulty.NORMAL);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame2.getId(), Difficulty.NORMAL);
        balanceGameVoteDAO.votePreference(member.getId(), balanceGame2.getId(), Preference.DISLIKE);
        balanceGameVoteDAO.votePreference(member2.getId(), balanceGame2.getId(), Preference.DISLIKE);

        balanceGameVoteDAO.chooseAnswer(member.getId(), balanceGame3.getId(), 2);
        balanceGameVoteDAO.chooseAnswer(member2.getId(), balanceGame3.getId(), 1);
        balanceGameVoteDAO.voteDifficulty(member.getId(), balanceGame3.getId(), Difficulty.EASY);
        balanceGameVoteDAO.voteDifficulty(member2.getId(), balanceGame3.getId(), Difficulty.EASY);
        balanceGameVoteDAO.votePreference(member.getId(), balanceGame3.getId(), Preference.LIKE);
        balanceGameVoteDAO.votePreference(member2.getId(), balanceGame3.getId(), Preference.LIKE);

        // when
        List<BalanceGame> balanceGameListSortedById = balanceGameDAO.findAllSortedBy("id asc");
        List<BalanceGame> balanceGameListSortedByDifficulty = balanceGameDAO.findAllSortedBy("difficulty asc");
        List<BalanceGame> balanceGameListSortedByLikeCount = balanceGameDAO.findAllSortedBy("likeCount asc");

        // then
        assertEquals(balanceGame.getId(), balanceGameListSortedById.get(0).getId());
        assertEquals(balanceGame2.getId(), balanceGameListSortedByLikeCount.get(0).getId());
        assertEquals(balanceGame3.getId(), balanceGameListSortedByDifficulty.get(0).getId());
    }
}