package org.example.loginpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.loginpage.dbConnection.DbConnection;
import org.example.loginpage.exception.GlobalException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login System");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        GlobalException.setGlobalExceptionHandler();

        if (args.length < 3) {
            System.err.println("Usage:  java -jar YourApp.jar <db_url> <db_user> <db_password>");
            System.exit(1);
        }

        // Initialize database connection before launching the UI
        DbConnection.init(args[0], args[1], args[2]);


        launch(args);
    }
}