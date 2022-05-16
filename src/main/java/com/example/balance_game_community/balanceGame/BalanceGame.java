package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.balanceGameVote.Difficulty;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private Difficulty difficulty;
    private Long likeCount;
    private Long dislikeCount;

    public BalanceGame() {}

    public BalanceGame(ResultSet rs) throws SQLException {
        this.id = rs.getLong(1);
        this.memberId = rs.getLong(2);
        this.question = rs.getString(3);
        this.answer1 = rs.getString(4);
        this.answer2 = rs.getString(5);
        this.picture1 = rs.getString(6);
        this.picture2 = rs.getString(7);
        this.enrollmentTime = rs.getTimestamp(8);

        this.difficulty = Difficulty.values()[rs.getInt(9)];
        this.likeCount = rs.getLong(10);
        this.dislikeCount = rs.getLong(11);
    }

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
