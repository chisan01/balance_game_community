package com.example.balance_game_community;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDataSource extends DataSource {
    public TestDataSource() {
        this.dbURL = "jdbc:mysql://localhost:3306/balance_game_community_test?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        this.dbUser = "balance_game_community_test";
        this.dbPassword = "1234";
    }
}
