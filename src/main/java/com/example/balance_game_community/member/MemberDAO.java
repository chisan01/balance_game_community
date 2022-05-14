package com.example.balance_game_community.member;

import com.example.balance_game_community.DAO;
import com.example.balance_game_community.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MemberDAO extends DAO {

    public MemberDAO(DataSource dataSource) {
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
            stmt.execute("TRUNCATE member");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt);
        }
    }

    // 회원가입
    public void signIn(Member member) {
        String SQL = "INSERT INTO member VALUES (?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if(emailDuplicateCheck(member.getEmail())) {
                throw new Exception("already exist email");
            }

            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);

            pstmt.setObject(1, null); // id가 auto_increment filed 라서 column 명을 생략하고 insert 문을 사용하는 경우, null 값을 넣어주면 된다,
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getNickname());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 이메일 중복 체크
    public boolean emailDuplicateCheck(String email) {
        String SQL = "SELECT * FROM member WHERE email = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return false;
    }

    // 로그인 (user_id 반환)
    public Long logIn(String email, String password) {
        String SQL = "SELECT id, password FROM member WHERE email = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString(2).equals(password)) {
                    return rs.getLong(1);
                } else {
                    throw new Exception("wrong password");
                }
            }
            throw new Exception("이메일 존재하지않음");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return null;
    }
}
