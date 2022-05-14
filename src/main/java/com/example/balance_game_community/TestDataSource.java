package com.example.balance_game_community;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDataSource {
    String dbURL, dbUser, dbPassword;

    public TestDataSource() {
        this.dbURL = "jdbc:mysql://localhost:3307/balance_game_community_test?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
        this.dbUser = "balance_game_community_test";
        this.dbPassword = "1234";
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
