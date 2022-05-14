package com.example.balance_game_community.balanceGameVote;

import com.example.balance_game_community.TestDataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameDAO;
import com.example.balance_game_community.member.Member;
import com.example.balance_game_community.member.MemberDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceGameVoteDAOTest {

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
    }

//    @Test
//    void chooseAnswer() {
//
//    }

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
        assertTrue(balanceGameVoteDAO.findByMemberIdAndBalanceGameId(member2.getId(), balanceGame.getId()));
        assertFalse(balanceGameVoteDAO.findByMemberIdAndBalanceGameId(member.getId(), balanceGame.getId()));
    }
}