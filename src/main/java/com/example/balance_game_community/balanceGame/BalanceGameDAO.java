package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.DAO;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;

import java.sql.*;
import java.time.Instant;

public class BalanceGameDAO extends DAO {

    private final BalanceGameVoteDAO balanceGameVoteDAO;

    public BalanceGameDAO(DataSource dataSource) {
        super(dataSource);
        balanceGameVoteDAO = new BalanceGameVoteDAO(dataSource);
    }

    // 테스트 용 DB table reset
    public void reset() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE balancegame");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }

    // 밸런스 게임 생성
    public void addBalanceGame(Long memberId, BalanceGame balanceGame) {
        String SQL = "INSERT INTO balancegame VALUES (?,?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, balanceGame.getId()); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setLong(2, memberId);
            pstmt.setString(3, balanceGame.getQuestion());
            pstmt.setString(4, balanceGame.getAnswer1());
            pstmt.setString(5, balanceGame.getAnswer2());
            pstmt.setString(6, balanceGame.getPicture1());
            pstmt.setString(7, balanceGame.getPicture2());
            pstmt.setTimestamp(8, Timestamp.from(Instant.now()));

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public BalanceGame findById(Long balanceGameId) {
        String SQL = "SELECT * FROM balancegame WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, balanceGameId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                BalanceGame balanceGame = new BalanceGame();
                balanceGame.setId(rs.getLong(1));
                balanceGame.setMemberId(rs.getLong(2));
                balanceGame.setQuestion(rs.getString(3));
                balanceGame.setAnswer1(rs.getString(4));
                balanceGame.setAnswer2(rs.getString(5));
                balanceGame.setPicture1(rs.getString(6));
                balanceGame.setPicture2(rs.getString(7));
                balanceGame.setEnrollmentTime(rs.getTimestamp(8));
                return balanceGameVoteDAO.updatePreferenceCount(balanceGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    // 다음 게임 id 랜덤하게 반환 (현재 게임 제외)
    public Long getOtherRandomBalanceGameId(Long curBalanceGameId) {
        Long otherRandomBalanceGameId;
        Long lastBalanceGameId = getLastBalanceGameId();
        while (true) {
            otherRandomBalanceGameId = (long) (Math.random() * lastBalanceGameId) + 1;
            if(!otherRandomBalanceGameId.equals(curBalanceGameId)) break;
        }
        return otherRandomBalanceGameId;
    }

    public Long getLastBalanceGameId() {
        String SQL = "SELECT MAX(id) FROM balancegame";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }
}
