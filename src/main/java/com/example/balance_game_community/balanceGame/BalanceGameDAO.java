package com.example.balance_game_community.balanceGame;

import com.example.balance_game_community.DAO;
import com.example.balance_game_community.DataSource;
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
            pstmt.setString(6, balanceGame.getAnswer1PictureUrl());
            pstmt.setString(7, balanceGame.getAnswer2PictureUrl());
            pstmt.setTimestamp(8, Timestamp.from(Instant.now()));

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public BalanceGame findById(Long balanceGameId) {

        String SQL = "SELECT\n" +
                "\tid, memberId, question, answer1, answer2, picture1, picture2, enrollmentTime, difficulty,\n" +
                "\tMAX(case when preference='LIKE' then preferenceCount ELSE 0 END) AS likeCount,\n" +
                "\tMAX(case when preference='DISLIKE' then preferenceCount ELSE 0 END) AS dislikeCount\n" +
                "FROM (\n" +
                "\tSELECT\n" +
                "\t\tbalancegame.*,\n" +
                "\t\tROUND(AVG(balancegamevote.difficulty), 0) AS difficulty,\n" +
                "\t\tCOUNT(*) AS preferenceCount,\n" +
                "\t\tbalancegamevote.preference\n" +
                "\tFROM balancegame LEFT JOIN balancegamevote\n" +
                "\t\tON balancegame.id = balancegamevote.balanceGameId\n" +
                "\tGROUP BY balancegame.id, balancegamevote.preference\n" +
                ") AS b\n" +
                "WHERE id = ?\n" +
                "GROUP BY b.id;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, balanceGameId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new BalanceGame(rs);
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

        // 게임이 1개만 있는 경우
        if(lastBalanceGameId == 1) return 1L;

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

        String SQL = "SELECT\n" +
                "\tid, memberId, question, answer1, answer2, picture1, picture2, enrollmentTime, difficulty,\n" +
                "\tMAX(case when preference='LIKE' then preferenceCount ELSE 0 END) AS likeCount,\n" +
                "\tMAX(case when preference='DISLIKE' then preferenceCount ELSE 0 END) AS dislikeCount\n" +
                "FROM (\n" +
                "\tSELECT\n" +
                "\t\tbalancegame.*,\n" +
                "\t\tROUND(AVG(balancegamevote.difficulty), 0) AS difficulty,\n" +
                "\t\tCOUNT(*) AS preferenceCount,\n" +
                "\t\tbalancegamevote.preference\n" +
                "\tFROM balancegame LEFT JOIN balancegamevote\n" +
                "\t\tON balancegame.id = balancegamevote.balanceGameId\n" +
                "\tGROUP BY balancegame.id, balancegamevote.preference\n" +
                ") AS b\n" +
                "WHERE difficulty = ?\n" +
                "GROUP BY b.id;";

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
                balanceGameList.add(balanceGame);
            }
            return balanceGameList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }

    public List<BalanceGame> findAllSortedBy(String sort) {
        String SQL = "SELECT\n" +
                "\tid, memberId, question, answer1, answer2, picture1, picture2, enrollmentTime, difficulty,\n" +
                "\tMAX(case when preference='LIKE' then preferenceCount ELSE 0 END) AS likeCount,\n" +
                "\tMAX(case when preference='DISLIKE' then preferenceCount ELSE 0 END) AS dislikeCount\n" +
                "FROM (\n" +
                "\tSELECT\n" +
                "\t\tbalancegame.*,\n" +
                "\t\tROUND(AVG(balancegamevote.difficulty), 0) AS difficulty,\n" +
                "\t\tCOUNT(*) AS preferenceCount,\n" +
                "\t\tbalancegamevote.preference\n" +
                "\tFROM balancegame LEFT JOIN balancegamevote\n" +
                "\t\tON balancegame.id = balancegamevote.balanceGameId\n" +
                "\tGROUP BY balancegame.id, balancegamevote.preference\n" +
                ") AS b\n" +
                "GROUP BY b.id\n" +
                "ORDER BY " + sort;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            rs = pstmt.executeQuery();

            List<BalanceGame> balanceGameList = new ArrayList<>();
            while (rs.next()) {
                BalanceGame balanceGame = new BalanceGame(rs);
                balanceGameList.add(balanceGame);
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
