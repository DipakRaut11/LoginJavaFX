package org.example.loginpage.timeUpdater;

import org.example.loginpage.dbConnection.DbCOnnection;

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

    // Method to update login_at when user logs in
    public static void updateLoginTime(String username) {
        String updateQuery = "UPDATE users SET login_at = ? WHERE username = ?";

        try (Connection connection = DbCOnnection.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            // Set the current time for login_at when user logs in
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Updating login_at for user: " + username + " to " + now);

            updateStatement.setTimestamp(1, java.sql.Timestamp.valueOf(now)); // login_at
            updateStatement.setString(2, username);

            // Update the user record
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
        String query = "UPDATE users SET last_logout_at = NOW() WHERE username = ?";

        try (Connection connection = DbCOnnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Logout time updated successfully for user: " + username);
            }

        } catch (SQLException e) {
            System.err.println("Error updating logout time: " + e.getMessage());
        }
    }
}
