package org.example.loginpage.exception;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class GlobalException {

    // Global method to show error dialogs
    public static void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        });
    }

    // Global uncaught exception handler
    public static void setGlobalExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            if (throwable instanceof javafx.fxml.LoadException) {
                showError("FXML Load Error", "There was an issue while loading the FXML file.");
            } else if (throwable instanceof Exception) {
                showError("Unexpected Error", "An unexpected error occurred: " + throwable.getMessage());
            }
        });
    }
}
