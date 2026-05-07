package com.ghs.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ghs_db";

    public static Connection getConnection() {
        Properties auth = new Properties();
        auth.put("user", "root");
        auth.put("password", "Your_Password");
        
        try {
            return DriverManager.getConnection(URL, auth);
        } catch (SQLException e) {
            System.err.println("DB Connection failure: " + e.getErrorCode());
            return null;
        }
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            if (con != null) System.out.println("Successfully connected to ghs_db!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}