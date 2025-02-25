package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.example.loginpage.dbConnection.DbCOnnection;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 */

public class SignupController {


    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    public void handleSignup() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }
        if (!isValidEmail(email)) {
            showAlert("Error", "Invalid email format");
            return;
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            showAlert("Error", "Invalid phone number format (must be 10 digits)");
            return;
        }



        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match");
            return;
        }


        System.out.println("Attempting to save user to database...");

        // **Call the saveUserToDatabase() method here**
        if (saveUserToDatabase(fullName, email, phoneNumber, username, password)) {
            showAlert("Success", "Account created successfully!");
            switchToLogin();
        } else {
            showAlert("Error", "Failed to save user to database.");
        }

    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void switchToLogin() {
        try {
            System.out.println("Loading login.fxml...");
            URL loginFXML = getClass().getResource("/org/example/loginpage/login.fxml");
            if (loginFXML == null) {
                throw new Exception("Login FXML file not found!");
            }
            Stage stage = (Stage) newUsernameField.getScene().getWindow();
            Scene loginScene = new Scene(FXMLLoader.load(loginFXML));
            stage.setScene(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("98\\d{8}");
    }

    private boolean saveUserToDatabase(String fullName, String email, String phone, String username, String password) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
        String insertQuery = "INSERT INTO users (full_name, email, phone, username, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbCOnnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            // Check if username or email already exists
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            var resultSet = checkStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                showAlert("Error", "Username or Email already exists!");
                return false;
            }

            // Hash the password before storing
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Insert new user
            insertStmt.setString(1, fullName);
            insertStmt.setString(2, email);
            insertStmt.setString(3, phone);
            insertStmt.setString(4, username);
            insertStmt.setString(5, hashedPassword);

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}