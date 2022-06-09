package com.example.balance_game_community.balanceGame;

public class BalanceGameResult {
    private Long answer1voteCount;
    private Long answer2voteCount;
    private Double answer1percent;
    private Double answer2percent;

    public BalanceGameResult(Long answer1voteCount, Long answer2voteCount) {
        this.answer1voteCount = answer1voteCount;
        this.answer2voteCount = answer2voteCount;
        updatePercent();
    }

    public void updatePercent() {
        answer1percent = Math.round(((double) answer1voteCount * 1000 / (answer1voteCount + answer2voteCount))) / 10.0;
        answer2percent = 100.0 - answer1percent;
    }

    public Long getAnswer1voteCount() {
        return answer1voteCount;
    }

    public void setAnswer1voteCount(Long answer1voteCount) {
        this.answer1voteCount = answer1voteCount;
    }

    public Long getAnswer2voteCount() {
        return answer2voteCount;
    }

    public void setAnswer2voteCount(Long answer2voteCount) {
        this.answer2voteCount = answer2voteCount;
    }

    public Double getAnswer1percent() {
        return answer1percent;
    }

    public void setAnswer1percent(Double answer1percent) {
        this.answer1percent = answer1percent;
    }

    public Double getAnswer2percent() {
        return answer2percent;
    }

    public void setAnswer2percent(Double answer2percent) {
        this.answer2percent = answer2percent;
    }
}
