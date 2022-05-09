package com.example.balance_game_community.balanceGame;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BalanceGame {
    private Long id;
    private Long memberId;
    private String question;
    private String answer1;
    private String answer2;
    private Timestamp enrollmentTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public Timestamp getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(Timestamp enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }
}
