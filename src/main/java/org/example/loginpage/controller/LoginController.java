package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.example.loginpage.dbConnection.DbCOnnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    //Make handleLogin() public so it can be accessed from FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        if (validateUser(username, password)) {
            showAlert("Success", "Login successful!");
            openDashboard();
            closeLoginWindow();
        } else {
            showAlert("Error", "Invalid username or password");
        }
    }

    //Fix validateUser() by adding proper logging & closing resources
    private boolean validateUser(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = DbCOnnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery(); //Use ResultSet properly

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                return BCrypt.checkpw(password, hashedPassword); //Check hashed password
            }

        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage()); //Better logging
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
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");

            // Dynamically set scene size based on FXML
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeLoginWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
