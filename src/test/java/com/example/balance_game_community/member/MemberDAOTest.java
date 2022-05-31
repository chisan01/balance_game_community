package com.example.balance_game_community.member;

import com.example.balance_game_community.TestDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberDAOTest {

    private static MemberDAO memberDAO;

    @BeforeAll
    static void init() {
        memberDAO = new MemberDAO(new TestDataSource());
    }

    @AfterEach
    void tearDown() {
        memberDAO.reset();
    }

    @Test
    void signIn() {
    }

    @Test
    @DisplayName("이메일 중복 여부 체크 기능")
    void emailDuplicateCheck() throws Exception {
        // given
        Member member = new Member();
        member.setEmail("test@gmail.com");
        member.setPassword("1234");
        member.setNickname("test");
        memberDAO.signIn(member);

        // then
        assertTrue(memberDAO.emailDuplicateCheck("test@gmail.com"));
        assertFalse(memberDAO.emailDuplicateCheck("test2@gmail.com"));
    }

    @Test
    @DisplayName("로그인 정상 동작 확인")
    void logIn() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "1234";

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setNickname("test");
        memberDAO.signIn(member);

        // then
        assertNotNull(memberDAO.logIn(email, password));
    }

    @Test
    @DisplayName("로그인 - 잘못된 비밀번호 입력시")
    void logIn2() throws Exception {
        // given
        String email = "test@gmail.com";
        String password = "1234";
        String wrongPassword = "1234a";

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setNickname("test");
        memberDAO.signIn(member);

        // then
        assertNull(memberDAO.logIn(email, wrongPassword));
    }

    @Test
    @DisplayName("로그인 - 존재하지 않는 이메일 사용시")
    void logIn3() throws Exception {
        // given
        String email = "test@gmail.com";
        String email2 = "test2@gmail.com";
        String password = "1234";

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setNickname("test");
        memberDAO.signIn(member);

        // then
        assertNull(memberDAO.logIn(email2, password));
    }
}