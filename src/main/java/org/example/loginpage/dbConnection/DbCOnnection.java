package org.example.loginpage.dbConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbCOnnection {
//    private static  String URL = "jdbc:mysql://localhost:3306/avocado_login";
//    private static  String USER = "mysql";
//    private static  String PASSWORD = "root";
//
//
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static void init(String url, String user, String password) {
        URL = url;
        USER = user;
        PASSWORD = password;
    }
    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Database connection details are not initialized.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

}
