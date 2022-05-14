package com.example.balance_game_community.balanceGameComment;

import java.sql.Timestamp;

public class BalanceGameComment {
    private Long id;
    private Long memberId;
    private Long balanceGameId;
    private String content;
    private Timestamp writeTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Timestamp writeTime) {
        this.writeTime = writeTime;
    }
}
