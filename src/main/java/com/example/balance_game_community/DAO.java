package com.example.balance_game_community;

import java.sql.*;

public class DAO {

    private final DataSource dataSource;

    public DAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() {
        return dataSource.getConnection();
    }

    protected void releaseConnection(Connection conn) throws SQLException {
        dataSource.releaseConnection(conn);
    }

    protected void close(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
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

    protected void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        close(conn, pstmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
