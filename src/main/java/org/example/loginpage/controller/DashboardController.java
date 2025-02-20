package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class DashboardController {





    @FXML
    private Button logoutButton;
    @FXML
    private ListView<String> stockList;
    @FXML
    private TextField stockInput;
    @FXML private Label stockPriceLabel;

    private final Map<String, Double> stockPrices = new HashMap<>();




        public void initialize() {

        stockPrices.put("AAPL", 175.50);
        stockPrices.put("GOOGL", 2800.75);
        stockPrices.put("AMZN", 3400.60);
        stockPrices.put("TSLA", 950.30);


        stockList.getItems().addAll(stockPrices.keySet());
        }

    @FXML
    private void checkStockPrice() {
        String stockSymbol = stockInput.getText().toUpperCase();
        if (stockPrices.containsKey(stockSymbol)) {
            stockPriceLabel.setText("Price: $" + stockPrices.get(stockSymbol));
        } else {
            showAlert("Stock Not Found", "Please enter a valid stock symbol.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





    @FXML
        private void handleLogout() {
            // Close dashboard window
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
        }
}
