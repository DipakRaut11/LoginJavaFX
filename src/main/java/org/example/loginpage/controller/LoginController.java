package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Stage = windows
 * Scene = content of the window
 * Parent = root node of the scene graph (the root node of the FXML file)
 *
 */

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

//    public void handleLogin() {
//        String username = usernameField.getText();
//        String password = passwordField.getText();
//
//        if (username.isEmpty() || password.isEmpty()) {
//            showAlert("Error", "Please fill in all fields");
//            return;
//        }
//
//        if (SignupController.users.containsKey(username) &&
//                SignupController.users.get(username).equals(password)) {
//            showAlert("Success", "Login successful!");
//        } else {
//            showAlert("Error", "Invalid username or password");
//        }
//    }

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        if (SignupController.users.containsKey(username) &&
                SignupController.users.get(username).equals(password)) {
            showAlert("Success", "Login successful!");
            openDashboard();
            closeLoginWindow();
        } else {
            showAlert("Error", "Invalid username or password");
        }
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
            dashboardStage.setScene(new Scene(root, 600, 400));
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
