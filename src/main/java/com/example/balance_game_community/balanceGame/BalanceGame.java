package com.example.balance_game_community.balanceGame;

import java.sql.Timestamp;

public class BalanceGame {
    private Long id;
    private Long memberId;
    private String question;
    private String answer1;
    private String answer2;
    private String picture1;
    private String picture2;
    private Timestamp enrollmentTime;

    private Long likeNumber;
    private Long dislikeNumber;

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

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public Timestamp getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(Timestamp enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public Long getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Long likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Long getDislikeNumber() {
        return dislikeNumber;
    }

    public void setDislikeNumber(Long dislikeNumber) {
        this.dislikeNumber = dislikeNumber;
    }
}
