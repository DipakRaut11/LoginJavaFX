package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.example.loginpage.dbConnection.DbConnection;
import org.example.loginpage.timeUpdater.LoginTimeUpdater;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ResultSet in Java is used to hold the data retrieved from a database after executing a query.
 * It's an object that contains the results of a query execution, which you can iterate through to access
 * each row of the data returned.
 *
 *
 */

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;


    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        if (validateUser(username, password)) {

            LoginTimeUpdater.updateLoginTime(username);

            showAlert("Success", "Login successful!");
            openDashboard();
            closeLoginWindow();
        } else {
            showAlert("Error", "Invalid username or password");
        }
    }


    private boolean validateUser(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }

        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
        }

        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void switchToSignup() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene signupScene = new Scene(FXMLLoader.load(getClass().getResource("/org/example/loginpage/signup.fxml")));
            stage.setScene(signupScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/loginpage/dashboard.fxml"));
            Parent root = loader.load();


            DashboardController dashboardController = loader.getController();
            dashboardController.setLoggedInUsername(usernameField.getText());

            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(root));
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void closeLoginWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }


    public void handleLogout() {
        String username = usernameField.getText();
        if (!username.isEmpty()) {
           // LoginTimeUpdater.updateLogoutTime(username);
            closeDashboardWindow();
        }
    }

    private void closeDashboardWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
