package com.example.balance_game_community.balanceGameComment;

import com.example.balance_game_community.DAO;
import com.example.balance_game_community.DataSource;
import com.example.balance_game_community.balanceGame.BalanceGame;
import com.example.balance_game_community.balanceGame.BalanceGameResult;
import com.example.balance_game_community.balanceGameVote.Preference;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BalanceGameCommentDAO extends DAO {

    public BalanceGameCommentDAO(DataSource dataSource) {
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
            stmt.execute("TRUNCATE balancegamecomment");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }

    // 밸런스 게임 댓글 작성
    public void addComment(Long memberId, Long balanceGameId, String content) {
        String SQL = "INSERT INTO balancegamecomment VALUES (?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, null); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setLong(2, memberId);
            pstmt.setLong(3, balanceGameId);
            pstmt.setString(4, content);
            pstmt.setTimestamp(5, Timestamp.from(Instant.now()));

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 특정 밸런스게임에 대한 댓글 목록 (시간순 내림차순 정렬)
    public List<BalanceGameComment> findAllByBalanceGameId(Long balanceGameId) {
        String SQL = "SELECT *\n" +
                "FROM balancegamecomment\n" +
                "WHERE balanceGameId = ?\n" +
                "ORDER BY writeTime DESC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, balanceGameId);

            rs = pstmt.executeQuery();

            List<BalanceGameComment> balanceGameCommentList = new ArrayList<>();
            while (rs.next()) {
                BalanceGameComment balanceGameComment = new BalanceGameComment();
                balanceGameComment.setId(rs.getLong(1));
                balanceGameComment.setMemberId(rs.getLong(2));
                balanceGameComment.setBalanceGameId(rs.getLong(3));
                balanceGameComment.setContent(rs.getString(4));
                balanceGameComment.setWriteTime(rs.getTimestamp(5));
                balanceGameCommentList.add(balanceGameComment);
            }
            return balanceGameCommentList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }
}
