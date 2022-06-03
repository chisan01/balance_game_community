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
    private String answer1PictureUrl;
    private String answer2PictureUrl;
    private Timestamp enrollmentTime;

    private Difficulty difficulty;
    private Long likeCount;
    private Long dislikeCount;

    private Difficulty totalDifficulty; // 투표받은 것까지 계산된 난이도

    public BalanceGame() {}

    public BalanceGame(ResultSet rs) throws SQLException {
        this.id = rs.getLong(1);
        this.memberId = rs.getLong(2);
        this.question = rs.getString(3);
        this.answer1 = rs.getString(4);
        this.answer2 = rs.getString(5);
        this.answer1PictureUrl = rs.getString(6);
        this.answer2PictureUrl = rs.getString(7);
        this.enrollmentTime = rs.getTimestamp(8);

        this.difficulty = Difficulty.values()[rs.getInt(9)];
        this.likeCount = rs.getLong(10);
        this.dislikeCount = rs.getLong(11);

        this.totalDifficulty = Difficulty.EASY;
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

    public String getAnswer1PictureUrl() {
        return answer1PictureUrl;
    }

    public void setAnswer1PictureUrl(String answer1PictureUrl) {
        this.answer1PictureUrl = answer1PictureUrl;
    }

    public String getAnswer2PictureUrl() {
        return answer2PictureUrl;
    }

    public void setAnswer2PictureUrl(String answer2PictureUrl) {
        this.answer2PictureUrl = answer2PictureUrl;
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

    public Difficulty getTotalDifficulty() { return totalDifficulty; }

    public void setTotalDifficulty(Difficulty difficulty) { this.totalDifficulty = difficulty; }
}
