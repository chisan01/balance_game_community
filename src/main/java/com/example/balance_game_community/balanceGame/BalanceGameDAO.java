package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.DAO;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGameComment.BalanceGameComment;
import com.example.balance_game_community.balanceGameVote.BalanceGameVoteDAO;
import com.example.balance_game_community.balanceGameVote.Difficulty;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BalanceGameDAO extends DAO {

    private final BalanceGameVoteDAO balanceGameVoteDAO;

    public BalanceGameDAO(DataSource dataSource, BalanceGameVoteDAO balanceGameVoteDAO) {
        super(dataSource);
        this.balanceGameVoteDAO = balanceGameVoteDAO;
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
                BalanceGame balanceGame = new BalanceGame(rs);
                return balanceGameVoteDAO.updatePreferenceCount(balanceGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

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

    public List<BalanceGame> findAllByDifficulty(Difficulty difficulty) {
        String SQL = "SELECT * \n" +
                "FROM (\n" +
                "\tSELECT balancegame.*, ROUND(AVG(balancegamevote.difficulty), 0) AS difficulty\n" +
                "\tFROM balancegame JOIN balancegamevote\n" +
                "\t\tON balancegame.id = balancegamevote.balanceGameId\n" +
                "\tGROUP BY balancegame.id\n" +
                "\t) AS balancegame\n" +
                "WHERE difficulty = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, difficulty.ordinal());

            rs = pstmt.executeQuery();

            List<BalanceGame> balanceGameList = new ArrayList<>();
            while (rs.next()) {
                BalanceGame balanceGame = new BalanceGame(rs);
                balanceGameList.add(balanceGameVoteDAO.updatePreferenceCount(balanceGame));
            }
            return balanceGameList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }
}
