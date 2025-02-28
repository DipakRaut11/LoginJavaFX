package org.example.loginpage.timeUpdater;

import org.example.loginpage.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * A PreparedStatement in Java is a type of Statement that is used to execute SQL queries with parameters.
 * It is called "prepared" because it allows you to define an SQL query with placeholders (often represented by ?),
 * which will later be substituted with actual values at runtime.
 *
 *
 */

public class LoginTimeUpdater {


    public static void updateLoginTime(String username) {
        String updateQuery = "UPDATE users SET login_at = ? WHERE username = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {


            LocalDateTime now = LocalDateTime.now();
            System.out.println("Updating login_at for user: " + username + " to " + now);


            updateStatement.setTimestamp(1, java.sql.Timestamp.valueOf(now));
            updateStatement.setString(2, username);


            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Successfully updated login_at for user: " + username);
            } else {
                System.out.println("No rows updated for user: " + username);
            }

        } catch (SQLException e) {
            System.err.println("Error updating login time for user: " + username + ". Error: " + e.getMessage());
        }
    }


    public static void updateLogoutTime(String username) {
        String query = "UPDATE users SET last_logout_at = ? WHERE username = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {


            LocalDateTime now = LocalDateTime.now();
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(now));
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Logout time updated successfully for user: " + username);
            }

        } catch (SQLException e) {
            System.err.println("Error updating logout time: " + e.getMessage());
        }
    }

}
