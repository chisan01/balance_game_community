package com.example.balance_game_community.balanceGameVote;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceGameVote {
    private Long id;
    private Long memberId;
    private Long balanceGameId;
    private Integer answerNumber;
    private Difficulty difficulty;
    private Preference preference;

    public BalanceGameVote(ResultSet rs) throws SQLException {
        this.id = rs.getLong(1);
        this.memberId = rs.getLong(2);
        this.balanceGameId = rs.getLong(3);
        this.answerNumber = rs.getInt(4);

        if(rs.getObject(5) == null) this.difficulty = null;
        else this.difficulty = Difficulty.values()[rs.getInt(5)];

        if(rs.getString(6) == null) this.preference = null;
        else this.preference = Preference.valueOf(rs.getString(6));
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

    public Long getBalanceGameId() {
        return balanceGameId;
    }

    public void setBalanceGameId(Long balanceGameId) {
        this.balanceGameId = balanceGameId;
    }

    public Integer getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(Integer answerNumber) {
        this.answerNumber = answerNumber;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }
}
