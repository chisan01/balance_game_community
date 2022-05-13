package com.example.balance_game_community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    private final DataSource dataSource;

    public DAO() {
        this.dataSource = new DataSource();
    }

    protected Connection getConnection() {
        return dataSource.getConnection();
    }

    protected void releaseConnection(Connection conn) throws SQLException {
        dataSource.releaseConnection(conn);
    }

    protected void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                releaseConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
