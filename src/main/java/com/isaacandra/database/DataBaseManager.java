package com.isaacandra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    private static final String url = System.getenv("DB_URL");
    private static final String user = System.getenv("DB_USER");
    private static final String password = System.getenv("SB_PASSWORD");

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
