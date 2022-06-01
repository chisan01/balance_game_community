package com.example.balance_game_community;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataSource extends DataSource {
    public TestDataSource() {
        try (InputStream input = TestDataSource.class.getClassLoader().getResourceAsStream("dev.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);

            System.out.println("properties 파일 사용");

            this.dbURL = prop.getProperty("test_db.url");
            this.dbUser = prop.getProperty("test_db.user");
            this.dbPassword = prop.getProperty("test_db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
