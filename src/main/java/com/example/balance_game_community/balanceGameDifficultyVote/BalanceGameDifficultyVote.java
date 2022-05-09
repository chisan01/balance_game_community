package com.example.balance_game_community.balanceGameDifficultyVote;

public class BalanceGameDifficultyVote {
    private Long id;
    private Long memberId;
    private Long balanceGameId;
    private Difficulty difficulty;

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
