module org.example.loginpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens org.example.loginpage to javafx.fxml;
    opens org.example.loginpage.controller to javafx.fxml;

    exports org.example.loginpage;
}