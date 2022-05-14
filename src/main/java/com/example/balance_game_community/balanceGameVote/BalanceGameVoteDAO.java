package com.example.balance_game_community.balanceGameVote;

import com.example.balance_game_community.DAO;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameResult;

import java.sql.*;
import java.time.Instant;

public class BalanceGameVoteDAO extends DAO {

    public BalanceGameVoteDAO(DataSource dataSource) {
        super(dataSource);
    }

    // 테스트 용 DB table reset
    public void reset() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE balancegamevote");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }

    // 밸런스 게임 답변 고르기 (회원은 게임당 한번씩만 답변을 고를 수 있음)
    public void chooseAnswer(Long memberId, Long balanceGameId, int answerNumber) {
        String SQL = "INSERT INTO balancegamevote VALUES (?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, null); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setLong(2, memberId);
            pstmt.setLong(3, balanceGameId);
            pstmt.setInt(4, answerNumber);
            pstmt.setObject(5, null);
            pstmt.setObject(6, null);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 특정 밸런스게임에 대해 특정 회원이 이미 답변을 했는지 확인
    public Boolean findByMemberIdAndBalanceGameId(Long memberId, Long balanceGameId) {
        String SQL = "SELECT * FROM balancegamevote WHERE memberId = ? AND balanceGameId = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, memberId);
            pstmt.setLong(2, balanceGameId);

            rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    // 답변 후 난이도 투표

    // 답변 후 해당 게임에 대한 선호도(좋아요/싫어요) 투표

}
