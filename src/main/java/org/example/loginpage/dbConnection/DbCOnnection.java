package org.example.loginpage.dbConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbCOnnection {
    private static final String URL = "jdbc:mysql://localhost:3306/avocado_login";
    private static final String USER = "mysql";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
