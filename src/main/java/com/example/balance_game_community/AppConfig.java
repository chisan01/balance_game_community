package com.example.balance_game_community;

import com.example.balance_game_community.balanceGame.BalanceGameDAO;
import com.example.balance_game_community.balanceGameComment.BalanceGameCommentDAO;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.member.MemberDAO;

public class AppConfig {
    private static MemberDAO memberDAO;
    private static BalanceGameDAO balanceGameDAO;
    private static BalanceGameVoteDAO balanceGameVoteDAO;
    private static BalanceGameCommentDAO balanceGameCommentDAO;

    public static final String IMAGE_FOLDER_PATH = "/balance_game_images";

    public AppConfig(DataSource dataSource) {
        memberDAO = new MemberDAO(dataSource);
        balanceGameVoteDAO = new BalanceGameVoteDAO(dataSource);
        balanceGameDAO = new BalanceGameDAO(dataSource, balanceGameVoteDAO);
        balanceGameCommentDAO = new BalanceGameCommentDAO(dataSource);
    }

    public void resetAll() {
        balanceGameCommentDAO.reset();
        balanceGameVoteDAO.reset();
        balanceGameDAO.reset();
        memberDAO.reset();
    }

    public MemberDAO getMemberDAO() {
        return memberDAO;
    }

    public BalanceGameDAO getBalanceGameDAO() {
        return balanceGameDAO;
    }

    public BalanceGameVoteDAO getBalanceGameVoteDAO() {
        return balanceGameVoteDAO;
    }

    public BalanceGameCommentDAO getBalanceGameCommentDAO() {
        return balanceGameCommentDAO;
    }
}
