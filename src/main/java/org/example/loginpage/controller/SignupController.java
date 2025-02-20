package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.URL;
import java.util.HashMap;

/**
 *
 */

public class SignupController {
    public static HashMap<String, String> users = new HashMap<>();

    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;

    public void handleSignup() {
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match");
            return;
        }

        if (users.containsKey(username)) {
            showAlert("Error", "Username already exists");
            return;
        }

        users.put(username, password);
        showAlert("Success", "Account created successfully!");
        switchToLogin();
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


}